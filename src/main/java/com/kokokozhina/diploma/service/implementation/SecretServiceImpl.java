package com.kokokozhina.diploma.service.implementation;

import com.kokokozhina.diploma.model.Secret;
import com.kokokozhina.diploma.model.UsersSecrets;
import com.kokokozhina.diploma.repository.UsersSecretsRepository;
import com.kokokozhina.diploma.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SecretServiceImpl implements SecretService {

    @Autowired
    UsersSecretsRepository repo;

    private Optional<UsersSecrets> getUserSecrets() {
        return repo.findById(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public void createOrUpdateSecret(Secret secret) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UsersSecrets> userSecrets = getUserSecrets();
        if (userSecrets.isPresent()) {
            userSecrets.get().getSecrets().put(secret.getKey(), secret.getValue());
            repo.save(userSecrets.get());
        } else {
            Map<String, String> attrs = new HashMap<>();
            attrs.put(secret.getKey(), secret.getValue());
            repo.save(new UsersSecrets(login, attrs));
        }
    }

    @Override
    public void deleteSecret(String secretName) {
        Optional<UsersSecrets> userSecrets = getUserSecrets();
        if (userSecrets.isPresent()) {
            userSecrets.get().getSecrets().remove(secretName);
            repo.save(userSecrets.get());
        }
    }

    @Override
    public Secret getSecretValue(String secretName) throws Exception {
        Optional<UsersSecrets> userSecrets = getUserSecrets();
        if (userSecrets.isPresent() && userSecrets.get().getSecrets().get(secretName) != null) {
            return new Secret(secretName, userSecrets.get().getSecrets().get(secretName));
        } else {
            throw new Exception("No secret with " + secretName + " found");
        }
    }

    @Override
    public Set<String> getSecretNamesList() {
        Optional<UsersSecrets> userSecrets = getUserSecrets();
        Set<String> secretsNames = new HashSet<>();
        if (userSecrets.isPresent()) {
            return userSecrets.get().getSecrets().keySet();
        }

        return secretsNames;
    }


}
