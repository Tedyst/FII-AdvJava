package ro.tedyst.hibernate;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;
import ro.tedyst.user.service.model.User;
import ro.tedyst.user.service.model.UserGroup;

import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Named
public class MapMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {
    private static final Map<String, ConnectionProvider> connectionProviderMap = new HashMap<>();

    public MapMultiTenantConnectionProvider() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties prop = new Properties();
        try (InputStream is = classLoader.getResourceAsStream("META-INF/tenants-info.properties")) {
            prop.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String key : prop.stringPropertyNames()) {
            initConnectionProviderForTenant(key, prop.getProperty(key));
        }
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return connectionProviderMap.values()
                .iterator()
                .next();
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
        return connectionProviderMap.get(tenantIdentifier);
    }

    private void initConnectionProviderForTenant(String tenantName, String propertyFile) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.print("Initializing database connection for tenant " + propertyFile);
        Properties properties = new Properties();
        try (InputStream is = classLoader.getResourceAsStream("META-INF/tenants/" + propertyFile)) {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> configProperties = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            configProperties.put(key, value);
        }
        DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();
        connectionProvider.configure(configProperties);
        this.connectionProviderMap.put(tenantName, connectionProvider);

        generateSchema(tenantName, configProperties);
    }

    private void generateSchema(String tenantName, Map<String, Object> configProperties) {
        try {
            StandardServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder().applySettings(configProperties).build();

            MetadataSources metadata =
                    new MetadataSources(serviceRegistry);
            metadata.addAnnotatedClass(User.class);
            metadata.addAnnotatedClass(UserGroup.class);

            SchemaExport schemaExport = new SchemaExport();
            schemaExport.setHaltOnError(false);
            schemaExport.setFormat(true);
            schemaExport.setDelimiter(";");
            schemaExport.execute(EnumSet.of(TargetType.DATABASE), SchemaExport.Action.CREATE, metadata.buildMetadata());

            System.out.println("Schema created for tenant: " + tenantName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate schema for tenant: " + tenantName, e);
        }
    }

    public static Set<String> getTenantIdentifiers() {
        return connectionProviderMap.keySet();
    }
}