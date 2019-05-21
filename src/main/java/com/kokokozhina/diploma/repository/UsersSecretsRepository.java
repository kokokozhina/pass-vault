package com.kokokozhina.diploma.repository;

import com.kokokozhina.diploma.model.UsersSecrets;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersSecretsRepository extends CrudRepository<UsersSecrets, String> {
    UsersSecrets save(UsersSecrets secrets);

    @Override
    void deleteById(String s);

    @Override
    Optional<UsersSecrets> findById(String s);
}
