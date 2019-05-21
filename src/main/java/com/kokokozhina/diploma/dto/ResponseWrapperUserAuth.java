package com.kokokozhina.diploma.dto;

import com.kokokozhina.diploma.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class ResponseWrapperUserAuth implements Serializable {
    private String name;
    private Role role;

    public ResponseWrapperUserAuth(String name, Role role) {
        this.name = name;
        this.role = role;
    }
}
