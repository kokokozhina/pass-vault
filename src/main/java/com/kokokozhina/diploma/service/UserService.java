package com.kokokozhina.diploma.service;

import com.kokokozhina.diploma.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    void save(User user);
    void delete(User user);

    User findUserByLogin(String login);
    Map<String, String> register(User user);
    List<User> getAllUsers();

}