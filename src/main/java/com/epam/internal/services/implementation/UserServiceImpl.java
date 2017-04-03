package com.epam.internal.services.implementation;

import com.epam.internal.daos.UserDao;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Override
    public User findByEmail(String email) {
        return dao.getUserByEmail(email);
    }

    @Override
    public void createUser(User user) {
        dao.create(user);
    }

    @Override
    public void deleteUserByEmail(String email) {
        dao.delete(dao.getUserByEmail(email));
    }

    @Override
    public void updateUser(User user) {
        dao.update(user);
    }

    @Override
    public void updateUserInfo(User user, UserInfo info) {
        user.setInfo(info);
        dao.update(user);
    }


}