package ru.nsd.services.user;

import ru.nsd.models.User;

public class UserService {
    private final UserDao userDao = new UserDao();

    public User get(User user){
        return userDao.get(user);
    }

    public void add(User user){
        userDao.add(user);
    }
}
