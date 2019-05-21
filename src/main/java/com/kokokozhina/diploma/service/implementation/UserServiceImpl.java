package com.kokokozhina.diploma.service.implementation;

import com.kokokozhina.diploma.dto.ResponseWrapperRegistrationValidator;
import com.kokokozhina.diploma.model.User;
import com.kokokozhina.diploma.model.enums.Role;
import com.kokokozhina.diploma.repository.UserRepository;
import com.kokokozhina.diploma.service.UserService;
import com.kokokozhina.diploma.service.validation.UserServiceValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserServiceValidator userServiceValidator;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }


    @Override
    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }


    @Override
    public ResponseWrapperRegistrationValidator register(User user) {
        ResponseWrapperRegistrationValidator json = userServiceValidator.validate(user);
        if (json.isOk()) {
            user.setRole(Role.USER);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            save(user);

        }

        return json;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}