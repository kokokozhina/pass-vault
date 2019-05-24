package com.kokokozhina.diploma.service.validation;

import com.kokokozhina.diploma.model.User;
import com.kokokozhina.diploma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.kokokozhina.diploma.service.validation.ValidationMessages.*;

@Service
public class UserServiceValidator {
    private Map<String, String> errors;

    @Autowired
    private UserRepository userRepository;

    private void checkLogin(String login) {
        if (login == null || login.length() < 3) {
            errors.put("login", LOGIN_LENGTH);
            return;
        }

        User user = userRepository.findUserByLogin(login);
        if(user != null) {
            errors.put("login", LOGIN_NOT_UNIQUE);
            return;
        }
    }

    private void checkPassword(String password) {
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            errors.put("password", PASSWORD_POLICY);
        }
    }

    private void checkName(String name) {
        if (name.isEmpty()) {
            errors.put("name", FIELD_IS_EMPTY);
        }
    }

    private void checkEmail(String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            errors.put("email", EMAIL_INCORRECT);
            return;
        }

        User user = userRepository.findUserByEmail(email);
        if(user != null) {
            errors.put("email", EMAIL_NOT_UNIQUE);
            return;
        }
    }

    public Map<String, String> validate(User user) {
        errors = new HashMap<>();
        checkLogin(user.getLogin());
        checkEmail(user.getEmail());
        checkName(user.getName());
        checkPassword(user.getPassword());
        return errors;
    }

}
