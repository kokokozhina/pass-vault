package com.kokokozhina.diploma.service.implementation;

import com.kokokozhina.diploma.model.User;
import com.kokokozhina.diploma.model.UsersSecrets;
import com.kokokozhina.diploma.repository.UserRepository;
import com.kokokozhina.diploma.repository.UsersSecretsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@Qualifier("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersSecretsRepository usersSecretsRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        Optional<UsersSecrets> usersSecrets = usersSecretsRepository.findById(login);
        String password = "";
        if (usersSecrets.isPresent()) {
            password = usersSecrets.get().getPassword();
        }

        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), password, grantedAuthorities);
    }

}