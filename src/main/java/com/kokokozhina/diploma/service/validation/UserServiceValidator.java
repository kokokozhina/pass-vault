package com.kokokozhina.diploma.service.validation;

import com.kokokozhina.diploma.dto.ResponseWrapperRegistrationValidator;
import com.kokokozhina.diploma.model.User;
import com.kokokozhina.diploma.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.kokokozhina.diploma.service.validation.ValidationMessages.*;

@Service
public class UserServiceValidator {

    @Autowired
    private UserRepository userRepository;

    private String checkLogin(String login, MutableBoolean isOk) {
        if (login == null || login.length() < 3) {
            isOk.setFalse();
            return LOGIN_LENGTH;
        }

        User user = userRepository.findUserByLogin(login);
        if(user != null) {
            isOk.setFalse();
            return LOGIN_NOT_UNIQUE;
        }
        return OK;
    }

    private String checkPassword(String password, MutableBoolean isOk) {
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            isOk.setFalse();
            return PASSWORD_POLICY;
        }

        return OK;
    }

    private String checkNotEmpty(String field, MutableBoolean isOk) {
        if (StringUtils.isBlank(field)) {
            isOk.setFalse();
            return FIELD_IS_EMPTY;
        }

        return OK;
    }


    private String checkPhone(String phone, MutableBoolean isOk) {
        if (phone == null || !phone.matches(PHONE_REGEX)) {
            isOk.setFalse();
            return PHONE_INCORRECT;
        }

        User user = userRepository.findUserByPhone(phone);
        if(user != null) {
            isOk.setFalse();
            return PHONE_NOT_UNIQUE;
        }

        return OK;
    }

    private String checkEmail(String email, MutableBoolean isOk) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            isOk.setFalse();
            return EMAIL_INCORRECT;
        }

        User user = userRepository.findUserByEmail(email);
        if(user != null) {
            isOk.setFalse();
            return EMAIL_NOT_UNIQUE;
        }

        return OK;
    }

    public ResponseWrapperRegistrationValidator validate(User user) {
        MutableBoolean userDataIsOk = new MutableBoolean(true);
        return new ResponseWrapperRegistrationValidator(
                checkLogin(user.getLogin(), userDataIsOk),
                checkPassword(user.getPassword(), userDataIsOk),
                checkNotEmpty(user.getName(), userDataIsOk),
                checkPhone(user.getPhone(), userDataIsOk),
                checkEmail(user.getEmail(), userDataIsOk),
                userDataIsOk.getValue());
    }

}
