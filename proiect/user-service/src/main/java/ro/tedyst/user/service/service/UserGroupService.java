package ro.tedyst.user.service.service;

import ro.tedyst.user.service.model.UserGroup;

import java.util.List;

public interface UserGroupService {
    UserGroup find(String name);
    UserGroup find(Long id);
    List<UserGroup> find();

    UserGroup save(UserGroup user);
    UserGroup update(UserGroup user);
    void delete(Long id);
}
