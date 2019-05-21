package com.kokokozhina.diploma.service;

public interface SecurityService {

    String findLoggedInLogin();

    void login(String login, String password);
}