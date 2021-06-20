package com.doge.tip.businesslogic.user;

import com.doge.tip.converter.user.AuthorityConverter;
import com.doge.tip.converter.user.RoleConverter;
import com.doge.tip.dto.user.AuthorityDTO;
import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.exception.user.MissingElementException;
import com.doge.tip.model.domain.user.Authority;
import com.doge.tip.model.domain.user.User;
import com.doge.tip.model.repository.user.AuthorityRepository;
import com.doge.tip.model.domain.user.Role;
import com.doge.tip.model.repository.user.RoleRepository;
import com.doge.tip.model.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserLogic {

    private static final Logger LOG = LoggerFactory.getLogger(UserLogic.class);

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final AuthorityConverter authorityConverter;
    private final RoleConverter roleConverter;

    @Autowired
    public UserLogic(PasswordEncoder passwordEncoder, RoleRepository roleRepository, RoleConverter roleConverter,
                     AuthorityConverter authorityConverter, AuthorityRepository authorityRepository,
                     UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
        this.authorityConverter = authorityConverter;
        this.roleConverter = roleConverter;
        this.userRepository = userRepository;
    }

    public String encryptUserPassword(String plainTextPassword) {
        String encodedPassword = "";
        try {
            encodedPassword = passwordEncoder.encode(plainTextPassword);
            return encodedPassword;
        } catch (IllegalArgumentException e) {
            LOG.error("Could not encode password", e);
        }
        throw new RuntimeException("error encrypting password");
    }

    public Set<RoleDTO> assignExistingRoles(Set<RoleDTO> userDTOUserRoles) {
        Set<Role> userRoles = new HashSet<>();
            userDTOUserRoles.forEach(dto -> {
                userRoles.add(roleRepository.getRoleByName(dto.getName()).orElseThrow( () ->
                        new MissingElementException(createMissingElementExceptionMessage(dto))
                ));
            });

        return userRoles.stream().map(roleConverter::toDTO).collect(Collectors.toSet());
    }

    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    public ArrayList<User> getAllUsers() {
        return  (ArrayList<User>) userRepository.findAll();
    }

    public Set<AuthorityDTO> assignExistingAuthorities(Set<AuthorityDTO> authorityDTOS) {
        Set<Authority> authorities = new HashSet<>();
            authorityDTOS.forEach(dto -> {
                authorities.add(authorityRepository.getAuthorityByName(dto.getName()).orElseThrow( () ->
                        new MissingElementException(createMissingElementExceptionMessage(dto)))
                );
            });

        return authorities.stream().map(authorityConverter::toDTO).collect(Collectors.toSet());
    }

    private String createMissingElementExceptionMessage(Object dto) {
        String exceptionMessage = "Cannot find entity %s with name %s. Perhaps try inserting the before" +
                " assigning to a parent entity.";
        try {
            Method method = dto.getClass().getMethod("getName");
            exceptionMessage = String.format(exceptionMessage, dto.getClass().getTypeName(), method.invoke(dto));
        } catch( NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error("Reflection error", e);
        }
        return exceptionMessage;
    }

}
