package com.kokokozhina.diploma.model;

import org.springframework.data.annotation.Id;
import org.springframework.vault.repository.mapping.Secret;

import java.util.Map;

@Secret
public class UsersSecrets {
    @Id private String id;
    private String password;
    private Map<String, String> secrets;

    public UsersSecrets(String id, String password, Map<String, String> secrets) {
        this.id = id;
        this.password = password;
        this.secrets = secrets;
    }

    public UsersSecrets() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getSecrets() {
        return secrets;
    }

    public void setSecrets(Map<String, String> secrets) {
        this.secrets = secrets;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
