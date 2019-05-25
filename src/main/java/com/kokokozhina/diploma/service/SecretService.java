package com.kokokozhina.diploma.service;

import com.kokokozhina.diploma.model.Secret;
import com.kokokozhina.diploma.model.User;

import java.util.Set;

public interface SecretService {
    void createOrUpdateSecret(Secret secret);
    void deleteSecret(String secretName);
    void initUserSecrets(User user);
    Secret getSecretValue(String secretName) throws Exception;
    Set<String> getSecretNamesList();
}
