package ro.tedyst.submissionservice;

import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import javax.transaction.Transactional;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import ro.tedyst.submissionservice.dto.CreateUser;
import ro.tedyst.submissionservice.model.User;
import ro.tedyst.submissionservice.secure.MPJWTToken;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
@Path("/user")
@Singleton
public class UserController {
    @Inject
    private UserGroupService userGroupService;

    @Inject
    private UserService userService;

    @GET
    @Transactional
    public Response getUsers() {
        List<User> users = userService.find();
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    @Transactional
    public Response getUser(@PathParam("id") Long id) {
        User user = userService.find(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Transactional
    public Response createUser(@RequestBody CreateUser user) {
        User newUser = new User();
        newUser.setPasswordHashed(user.password);
        newUser.setUsername(user.username);
        newUser.setGroups(Set.of(userGroupService.find("student")));
        User savedUser = userService.save(newUser);
        return Response.ok(savedUser).build();
    }

    public static class ModifyUser {
        String password;
    }

    @PATCH
    @Path("/{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, ModifyUser modifyUser) {
        User originalUser = userService.find(id);
        if (originalUser == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        originalUser.setPasswordHashed(modifyUser.password);
        userService.update(originalUser);
        return Response.ok(originalUser).build();
    }

    private String key;

    @PostConstruct
    public void init() {
        key = readPemFile();
    }

    private static String readPemFile() {
        StringBuilder sb = new StringBuilder(8192);
        try (BufferedReader is = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(UserController.class.getResourceAsStream("/privateKey.pem")), StandardCharsets.US_ASCII))) {
            String line;
            while ((line = is.readLine()) != null) {
                if (!line.startsWith("-")) {
                    sb.append(line);
                    sb.append('\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @POST
    @Path("/login")
    public Response login(@RequestBody CreateUser loginUser) {
        User user = userService.find(loginUser.username);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(!user.verifyPassword(loginUser.password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        JWTAuth provider = JWTAuth.create(null, new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm("RS256")
                        .setSecretKey(key)
                ));

        MPJWTToken token = new MPJWTToken();
        token.setAud("user-service");
        token.setIss("http://user-service");
        token.setJti("user:" + user.getId());
        token.setSub(user.getUsername());
        token.setUpn(user.getUsername());
        token.setPreferredUsername(user.getUsername());
        token.setIat(System.currentTimeMillis());
        token.setExp(System.currentTimeMillis() + 1000 * 60 * 30);
        token.setGroups(user.getGroups().stream().map(UserGroup::getName).collect(Collectors.toUnmodifiableList()));

        String generatedToken = provider.generateToken(new io.vertx.core.json.JsonObject().mergeIn(token.toJSONString()), new JWTOptions().setAlgorithm("RS256"));
        return Response.ok(generatedToken).build();
    }
}
