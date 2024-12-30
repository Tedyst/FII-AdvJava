package ro.tedyst.user.service.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import javax.inject.Named;

@Named
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = currentTenant.get();
        if (tenantId == null) {
            return "default";
        }
        return tenantId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    public static void setTenant(String tenantId) {
        currentTenant.set(tenantId);
    }

    public static void clearTenant() {
        currentTenant.remove();
    }
}

