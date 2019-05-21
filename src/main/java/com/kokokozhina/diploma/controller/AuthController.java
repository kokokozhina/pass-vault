package com.kokokozhina.diploma.controller;

import com.kokokozhina.diploma.dto.ResponseWrapperRegistrationValidator;
import com.kokokozhina.diploma.dto.ResponseWrapperUserAuth;
import com.kokokozhina.diploma.dto.StringMessage;
import com.kokokozhina.diploma.model.User;
import com.kokokozhina.diploma.service.SecurityService;
import com.kokokozhina.diploma.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(value = "Login System", description = "Login via login and password")
@RequestMapping("/")
public class AuthController {

    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTER_ENDPOINT = "/register";

    private static final String INCORRECT_LOGIN_OR_PASSWORD = "Неверный логин или пароль";

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = LOGIN_ENDPOINT, method = RequestMethod.POST, consumes = {"application/json"})
    @ApiOperation(value = "login", response = Object.class)
    public ResponseEntity<Object> login(@RequestBody User user) {
        String login = user.getLogin();
        securityService.login(login, user.getPassword());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getName().equals(login)) {
            User userFromDB = userService.findUserByLogin(login);

            return new ResponseEntity<>(
                    new ResponseWrapperUserAuth(
                            userFromDB.getName(),
                            userFromDB.getRole()), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new StringMessage(INCORRECT_LOGIN_OR_PASSWORD), HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @RequestMapping(value = REGISTER_ENDPOINT, method = RequestMethod.POST, consumes = {"application/json"})
    @ApiOperation(value = "register", response = Object.class)
    public ResponseEntity<Object> register(@RequestBody User user) {
        User copyOfUser = SerializationUtils.clone(user);
        ResponseWrapperRegistrationValidator json = userService.register(copyOfUser);
        if (json.isOk()) {
            return login(user);
        } else {
            return new ResponseEntity<>(json, HttpStatus.CONFLICT);
        }
    }

}