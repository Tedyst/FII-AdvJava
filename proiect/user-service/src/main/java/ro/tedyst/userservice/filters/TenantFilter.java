package ro.tedyst.userservice.filters;

import ro.tedyst.userservice.config.MapMultiTenantConnectionProvider;
import ro.tedyst.userservice.config.TenantIdentifierResolver;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Provider
@Priority(Priorities.AUTHORIZATION)
public class TenantFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
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

        SecurityContext containerRequestContext = requestContext.getSecurityContext();

        System.out.println("Tenant ID: " + tenantId);
    }

    private boolean validateTenant(String tenantId) {
        return MapMultiTenantConnectionProvider.getTenantIdentifiers().contains(tenantId);
    }
}

