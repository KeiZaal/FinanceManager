package com.epam.internal.dao.test;

import com.epam.internal.data.entities.User;

public class UserDao extends GenericDao<User> {

    public UserDao() {
        super(User.class);
    }

    public User getUserByEmail(String email) {
        openCurrentSession();
        User user = (User) getCurrentSession()
                .createQuery("from User u where u.email=:email")
                .setParameter("email", email)
                .uniqueResult();
        closeCurrentSession();
        return user;
    }

}