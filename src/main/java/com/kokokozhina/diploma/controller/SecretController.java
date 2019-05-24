package com.kokokozhina.diploma.controller;

import com.kokokozhina.diploma.model.Secret;
import com.kokokozhina.diploma.service.SecretService;
import com.kokokozhina.diploma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SecretController {

    private static final String CREATE_SECRET_ENDPOINT = "/createSecret";
    private static final String GET_SECRET_ENDPOINT = "/getSecret";
    private static final String DELETE_SECRET_ENDPOINT = "/deleteSecret";
    private static final String MAIN_ENDPOINT = "/main";

    @Autowired
    private SecretService secretService;

    @Autowired
    private UserService userService;

    @PostMapping(value = CREATE_SECRET_ENDPOINT)
    public String createSecret(@ModelAttribute("secretName") String secretName,
                               @ModelAttribute("secretValue") String secretValue, Model model) {
        if (secretName.isEmpty()) {
            model.addAttribute("creatingSecretMessage", "Название секрета не должно быть пустым");
            return main(model);
        }

        secretService.createOrUpdateSecret(new Secret(secretName, secretValue));
        return "redirect:/main";
    }

    @PostMapping(value = GET_SECRET_ENDPOINT)
    public String getSecretByName(@ModelAttribute("whichSecretToGet") String secretName, Model model) {
        try {
            Secret secret = secretService.getSecretValue(secretName);
            List<Secret> secrets = getSecretsList();
            model.addAttribute("secrets", secrets.stream()
                    .map(x -> x.getKey().equals(secretName) ? secret : x)
                    .collect(Collectors.toList()));
            model.addAttribute("name", userService.findUserByLogin(SecurityContextHolder
                    .getContext().getAuthentication().getName()).getName());
            return "main";
        } catch (Exception e) {
            model.addAttribute("secretsListError", "Произошла ошибка");
            return main(model);
        }
    }

    @PostMapping(value = DELETE_SECRET_ENDPOINT)
    public String deleteSecretByName(@ModelAttribute("whichSecretToDelete") String secretName, Model model) {
        try {
            secretService.deleteSecret(secretName);
            return "redirect:/main";
        } catch (Exception e) {
            model.addAttribute("secretsListError", "Произошла ошибка");
            return main(model);
        }
    }

    @GetMapping(value = MAIN_ENDPOINT)
    public String main(Model model) {
        model.addAttribute("name", userService.findUserByLogin(SecurityContextHolder
                .getContext().getAuthentication().getName()).getName());
        model.addAttribute("secrets", getSecretsList());
        return "main";
    }

    private List<Secret> getSecretsList() {
        List<Secret> secrets = new ArrayList<>();
        for (String secretName : secretService.getSecretNamesList())  {
            secrets.add(new Secret(secretName, "******"));
        }
        return secrets;
    }
}
