package com.kokokozhina.diploma.service;

import com.kokokozhina.diploma.model.Secret;

import java.util.Set;

public interface SecretService {
    void createOrUpdateSecret(Secret secret);
    void deleteSecret(String secretName);
    Secret getSecretValue(String secretName) throws Exception;
    Set<String> getSecretNamesList();
}
