package ro.tedyst.userservice.service;

import javax.transaction.Transactional;
import org.hibernate.service.Service;
import ro.tedyst.userservice.model.User;

import java.util.List;

@Transactional
public interface UserService extends Service {
    User find(String username);
    User find(Long id);
    List<User> find();

    User save(User user);
    User update(User user);
    void delete(Long id);
}
