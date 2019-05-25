package com.kokokozhina.diploma.controller;

import com.kokokozhina.diploma.model.User;
import com.kokokozhina.diploma.service.SecurityService;
import com.kokokozhina.diploma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;


@Controller
public class AuthController {

    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTER_ENDPOINT = "/register";

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @GetMapping({"/", LOGIN_ENDPOINT})
    public String login(Principal principal)
    {
        if(principal != null)
        {
            return "redirect:/main";
        }
        return "login";
    }

    @PostMapping(LOGIN_ENDPOINT)
    public String login(@ModelAttribute("login") String login,
                        @ModelAttribute("password") String password, Model model) {
        securityService.login(login, password);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getName().equals(login)) {
            return "redirect:/main";
        }

        model.addAttribute("error", "Неверный логин или пароль");
        return "login";
    }


    @GetMapping(REGISTER_ENDPOINT)
    public String register() {
        return "register";
    }

    @PostMapping(REGISTER_ENDPOINT)
    public String register(@ModelAttribute("login") String login,
                           @ModelAttribute("name") String name,
                           @ModelAttribute("email") String email,
                           @ModelAttribute("password") String password,
                           @ModelAttribute("passwordRepeat") String passwordRepeated,
                           Model model) {
        User user = new User(name, login, email, password);
        Map<String, String> errors = userService.register(user);
        if (errors.isEmpty() && password.equals(passwordRepeated)) {
            return "redirect:/login";
        } else {
            if (errors.containsKey("login")) {
                model.addAttribute("loginErrorMessage", errors.get("login"));
            }
            if (errors.containsKey("name")) {
                model.addAttribute("nameErrorMessage", errors.get("name"));
            }
            if (!password.equals(passwordRepeated)) {
                model.addAttribute("passwordErrorMessage", "Пароли не совпадают");
            }
            if (errors.containsKey("password")) {
                model.addAttribute("passwordErrorMessage", errors.get("password"));
            }
            if (errors.containsKey("email")) {
                model.addAttribute("emailErrorMessage", errors.get("email"));
            }

            return "register";
        }
    }
}