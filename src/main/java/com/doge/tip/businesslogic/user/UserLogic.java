package com.doge.tip.businesslogic.user;

import com.doge.tip.OnStartDataPersistence;
import com.doge.tip.converter.user.AuthorityConverter;
import com.doge.tip.converter.user.RoleConverter;
import com.doge.tip.dto.user.AuthorityDTO;
import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.dto.user.UserDTO;
import com.doge.tip.exception.user.MissingElementException;
import com.doge.tip.model.domain.user.Authority;
import com.doge.tip.model.repository.user.AuthorityRepository;
import com.doge.tip.model.domain.user.Role;
import com.doge.tip.model.repository.user.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserLogic {

    private static final Logger LOG = LoggerFactory.getLogger(UserLogic.class);

    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final AuthorityConverter authorityConverter;
    private final RoleConverter roleConverter;

    @Autowired
    public UserLogic(PasswordEncoder passwordEncoder, RoleRepository roleRepository, RoleConverter roleConverter,
                     AuthorityConverter authorityConverter, AuthorityRepository authorityRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
        this.authorityConverter = authorityConverter;
        this.roleConverter = roleConverter;
    }

    public String encryptUserPassword(String plainTextPassword) {
        try {
            return passwordEncoder.encode(plainTextPassword);
        } catch (RuntimeException e) {
            LOG.error("Could not encode password", e);
        }
        throw new RuntimeException("error encrypting password");
    }

    public Set<RoleDTO> assignExistingRoles(Set<RoleDTO> userDTOUserRoles) {
        Set<Role> userRoles = new HashSet<>();
            userDTOUserRoles.forEach(dto -> {
                try {
                    userRoles.add(roleRepository.getRoleByName(dto.getName()).orElseThrow(NoSuchElementException::new));
                } catch(NoSuchElementException e){
                        LOG.error("Error finding an element!", e);
                        throw new MissingElementException(createMissingElementExceptionMessage(dto));
                    }
                });

        return userRoles.stream().map(roleConverter::toDTO).collect(Collectors.toSet());
    }

    public Set<AuthorityDTO> assignExistingAuthorities(Set<AuthorityDTO> authorityDTOS) {
        Set<Authority> authorities = new HashSet<>();
            authorityDTOS.forEach(dto -> {
                try {
                    authorities.add(authorityRepository.getAuthorityByName(dto.getName())
                            .orElseThrow(NoSuchElementException::new)
                    );
                } catch(NoSuchElementException e) {
                    LOG.error("Error finding an element!", e);
                    throw new MissingElementException(createMissingElementExceptionMessage(dto));
                }
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
