package ro.tedyst.lab7.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

import java.util.UUID;

@Logged
@ApplicationScoped
public class RegistrationNumberBean {
    @Produces
    @Named
    @MyQualifier
     String getRegistrationNumber() {
        return UUID.randomUUID().toString();
    }
}
