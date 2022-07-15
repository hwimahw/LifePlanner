package ru.nsd.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsd.models.User;

@Component
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User get(User user) {
        return userDao.get(user);
    }

    public void add(User user) {
        userDao.add(user);
    }
}
