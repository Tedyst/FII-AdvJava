package ro.tedyst.user.service.service;

import javax.transaction.Transactional;
import org.hibernate.service.Service;
import org.hibernate.service.spi.InjectService;
import ro.tedyst.user.service.model.User;
import ro.tedyst.user.service.model.UserGroup;

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
