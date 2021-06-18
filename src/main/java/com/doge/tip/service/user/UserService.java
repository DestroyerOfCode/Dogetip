package com.doge.tip.service.user;

import com.doge.tip.businesslogic.user.UserLogic;
import com.doge.tip.converter.user.AuthorityConverter;
import com.doge.tip.converter.user.RoleConverter;
import com.doge.tip.converter.user.UserConverter;
import com.doge.tip.dto.user.AuthorityDTO;
import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.dto.user.UserDTO;
import com.doge.tip.exception.common.APIRequestException;
import com.doge.tip.model.domain.user.*;
import com.doge.tip.model.repository.user.AuthorityRepository;
import com.doge.tip.model.repository.user.RoleRepository;
import com.doge.tip.model.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleConverter roleConverter;
    private final UserConverter userConverter;
    private final AuthorityConverter authorityConverter;
    private final UserLogic userLogic;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository, RoleConverter roleConverter,
                       UserConverter userConverter, AuthorityRepository authorityRepository,
                       AuthorityConverter authorityConverter, UserLogic userLogic) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.roleConverter = roleConverter;
        this.userConverter = userConverter;
        this.authorityConverter = authorityConverter;
        this.userLogic = userLogic;
    }

    public RoleDTO createUserRole(RoleDTO dto) {
        Role role;

        try {
            dto.setAuthorities(userLogic.assignExistingAuthorities(dto.getAuthorities()));

            role = roleConverter.toEntity(dto);;
            role = roleRepository.save(role);

        } catch (RuntimeException e) {
            LOG.error("Error persisting entity", e);
            throw new APIRequestException(createAPIRequestExceptionMessage(dto, e));
        }

        return roleConverter.toDTO(role);
    }

    public UserDTO createUser(UserDTO dto) {
        User user;

        try {
            dto.setPassword(userLogic.encryptUserPassword(dto.getPassword()));
            dto.setUserRoles(userLogic.assignExistingRoles(dto.getUserRoles()));

            user = userConverter.toEntity(dto);
            user = userRepository.save(user);
        } catch (RuntimeException e) {
            LOG.error("Error persisting entity", e);
            throw new APIRequestException(createAPIRequestExceptionMessage(dto, e));
        }

        return userConverter.toDTO(user);
    }

    public AuthorityDTO createAuthority(AuthorityDTO dto) {
        Authority authority;

        try {
            authority = authorityConverter.toEntity(dto);

            authority = authorityRepository.save(authority);
        } catch (RuntimeException e) {
            LOG.error("Error persisting entity", e);
            throw new APIRequestException(createAPIRequestExceptionMessage(dto, e));
        }
        return authorityConverter.toDTO(authority);
    }

    private String createAPIRequestExceptionMessage(Object dto, RuntimeException e) {
        String exceptionMessage = "error trying to persist entity %s with name %s! Error message: %s";
        try {
            Method method = dto.getClass().getMethod("getName");
            exceptionMessage = String.format(exceptionMessage, dto.getClass().getTypeName(), method.invoke(dto), e.getMessage());
        } catch( NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            LOG.error("Reflection error", ex);
        }
        return exceptionMessage;
    }
}
