package com.kokokozhina.diploma.repository;

import com.kokokozhina.diploma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByLogin(String login);
    User findUserByEmail(String email);

    List<User> findAll();
}
