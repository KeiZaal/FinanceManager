package com.epam.internal.services;

import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;

public interface UserService {

    User findByEmail(String email);
    void createUser(User user);
    void deleteUserByEmail(String email);
    void updateUser(User user);
    void updateUserInfo(User user, UserInfo info);

}