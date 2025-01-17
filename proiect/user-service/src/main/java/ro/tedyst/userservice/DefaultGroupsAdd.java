package ro.tedyst.userservice;

import ro.tedyst.userservice.config.MapMultiTenantConnectionProvider;
import ro.tedyst.userservice.config.TenantIdentifierResolver;
import ro.tedyst.userservice.model.User;
import ro.tedyst.userservice.model.UserGroup;
import ro.tedyst.userservice.service.UserGroupService;
import ro.tedyst.userservice.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class DefaultGroupsAdd {
    @Inject
    private UserService userService;

    @Inject
    private UserGroupService userGroupService;

    @Transactional
    private void onStartup(@Observes @Initialized(ApplicationScoped.class) final Object event) {
        for (String tenant : MapMultiTenantConnectionProvider.getTenantIdentifiers()) {
            TenantIdentifierResolver.setTenant(tenant);
            List<UserGroup> groups = userGroupService.find();
            if (groups.isEmpty()) {
                UserGroup adminGroup = new UserGroup();
                adminGroup.setName("admin");
                adminGroup = userGroupService.save(adminGroup);
                UserGroup teacherGroup = new UserGroup();
                teacherGroup.setName("teacher");
                userGroupService.save(teacherGroup);
                UserGroup studentGroup = new UserGroup();
                studentGroup.setName("student");
                userGroupService.save(studentGroup);

                User admin = new User();
                admin.setUsername("admin");
                admin.setPasswordHashed("admin");
                admin.getGroups().add(adminGroup);
                userService.save(admin);
            }
        }
    }
}
