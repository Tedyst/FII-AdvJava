package ro.tedyst.userservice.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import ro.tedyst.userservice.model.UserGroup;

import javax.inject.Named;
import java.util.List;

@Named
@Transactional
public class UserGroupServiceImpl implements UserGroupService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserGroup find(String name) {
        try {
            return entityManager.createQuery("SELECT g FROM UserGroup g WHERE g.name = :name", UserGroup.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Handle cases where no result is found
        }
    }

    @Override
    public UserGroup find(Long id) {
        return entityManager.find(UserGroup.class, id);
    }

    @Override
    public List<UserGroup> find() {
        return entityManager.createQuery("SELECT g FROM UserGroup g", UserGroup.class).getResultList();
    }

    @Override
    public UserGroup save(UserGroup group) {
        entityManager.persist(group);
        return group;
    }

    @Override
    public UserGroup update(UserGroup group) {
        return entityManager.merge(group);
    }

    @Override
    public void delete(Long id) {
        UserGroup group = find(id);
        if (group != null) {
            entityManager.remove(group);
        }
    }
}
