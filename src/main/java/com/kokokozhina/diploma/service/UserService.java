package com.kokokozhina.diploma.service;

import com.kokokozhina.diploma.dto.ResponseWrapperRegistrationValidator;
import com.kokokozhina.diploma.model.User;

import java.util.List;

public interface UserService {

    void save(User user);
    void delete(User user);

    User findUserByLogin(String login);

    ResponseWrapperRegistrationValidator register(User user);

    List<User> getAllUsers();

}