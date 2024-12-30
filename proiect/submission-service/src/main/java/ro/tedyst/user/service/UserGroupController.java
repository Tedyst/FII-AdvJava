package ro.tedyst.user.service;

import ro.tedyst.user.service.model.User;
import ro.tedyst.user.service.model.UserGroup;
import ro.tedyst.user.service.service.UserGroupService;
import ro.tedyst.user.service.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/group")
@Singleton
@RolesAllowed("admin")
public class UserGroupController {
    @Inject
    private UserGroupService userGroupService;

    @Inject
    private UserService userService;

    @GET
    @Transactional
    public Response getGroups() {
        List<UserGroup> groups = userGroupService.find();
        return Response.ok(groups).build();
    }

    @GET
    @Path("/{id}")
    @Transactional
    public Response getGroup(Long id) {
        UserGroup group = userGroupService.find(id);
        if (group == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(group).build();
    }

    @POST
    @Transactional
    public Response createGroup(UserGroup group) {
        UserGroup newGroup = userGroupService.save(group);
        return Response.ok(newGroup).build();
    }

    @POST
    @Path("/{id}/add-user/{userId}")
    @Transactional
    public Response addUserToGroup(@PathParam("id") Long id, @PathParam("userId") Long userId) {
        UserGroup group = userGroupService.find(id);
        if (group == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        User user = userService.find(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        user.getGroups().add(group);
        userService.update(user);
        return Response.ok(user).build();
    }

}
