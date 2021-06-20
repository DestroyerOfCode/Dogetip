package com.doge.tip.service;

import com.doge.tip.model.domain.user.Role;
import com.doge.tip.model.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<com.doge.tip.model.domain.user.User> user = userRepository.getUserByName(username);

        user.orElseThrow(() -> {
            throw new UsernameNotFoundException(String.format("Could not find user with name %s!", username));
        });

        UserDetails loginUser = User
                    .withUsername(user.get().getName())
                    .password(user.get().getPassword())
                    .roles(user.get().getUserRoles().stream().map(Role::getName).toArray(String[]::new))
                    .build();

        return loginUser;
    }
}