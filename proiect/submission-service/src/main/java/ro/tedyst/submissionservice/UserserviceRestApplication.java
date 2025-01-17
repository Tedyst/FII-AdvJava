package ro.tedyst.submissionservice;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@ApplicationScoped
@ApplicationPath("/api")

@LoginConfig(authMethod = "MP-JWT")
@DeclareRoles({"admin", "teacher", "student"})

public class UserserviceRestApplication extends Application {
}
