package ro.tedyst.user.service.filters;

import ro.tedyst.user.service.config.MapMultiTenantConnectionProvider;
import ro.tedyst.user.service.config.TenantIdentifierResolver;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Provider
@Priority(Priorities.AUTHENTICATION)
public class TenantFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String tenantId = requestContext.getHeaderString("X-Tenant-ID");

        if (tenantId == null || tenantId.trim().isEmpty()) {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity("Missing or invalid X-Tenant-ID header")
                    .build());
            return;
        }

        try {
            if (!validateTenant(tenantId)) {
                requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid tenant ID")
                        .build());
                return;
            }

            TenantIdentifierResolver.setTenant(tenantId);
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error processing tenant ID")
                    .build());
        }
    }

    private boolean validateTenant(String tenantId) {
        return MapMultiTenantConnectionProvider.getTenantIdentifiers().contains(tenantId);
    }
}

