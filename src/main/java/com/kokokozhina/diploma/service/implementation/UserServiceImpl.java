package com.kokokozhina.diploma.service.implementation;

import com.kokokozhina.diploma.model.User;
import com.kokokozhina.diploma.model.enums.Role;
import com.kokokozhina.diploma.repository.UserRepository;
import com.kokokozhina.diploma.service.SecretService;
import com.kokokozhina.diploma.service.UserService;
import com.kokokozhina.diploma.service.validation.UserServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserServiceValidator userServiceValidator;

    @Autowired
    private SecretService secretService;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }


    @Override
//    @Cacheable("user")
    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }


    @Override
    public Map<String, String> register(User user) {
        Map<String, String> errors = userServiceValidator.validate(user);
        if (errors.isEmpty()) {
            user.setRole(Role.USER);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            secretService.initUserSecrets(user);
            save(user);
        }

        return errors;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}