package com.doge.tip.service.user;

import com.doge.tip.businesslogic.user.UserLogic;
import com.doge.tip.common.user.UserCommonTest;
import com.doge.tip.converter.user.AuthorityConverter;
import com.doge.tip.converter.user.RoleConverter;
import com.doge.tip.converter.user.UserConverter;
import com.doge.tip.model.repository.user.AuthorityRepository;
import com.doge.tip.model.repository.user.RoleRepository;
import com.doge.tip.model.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class UserServiceTest extends UserCommonTest {

    static RoleRepository roleRepository;
    static UserRepository userRepository;
    static AuthorityRepository authorityRepository;
    static RoleConverter roleConverter;
    static UserConverter userConverter;
    static AuthorityConverter authorityConverter;
    static UserLogic userLogic;

    private UserService userService;

    @BeforeAll
    static void setUpMocks() {
        roleRepository = mock(RoleRepository.class);
        userRepository = mock(UserRepository.class);
        authorityRepository = mock(AuthorityRepository.class);
        roleConverter = mock(RoleConverter.class);
        userConverter = mock(UserConverter.class);
        authorityConverter = mock(AuthorityConverter.class);
        userLogic = mock(UserLogic.class);
    }

    @BeforeEach
    void setUpObjects() {
        userService = new UserService(roleRepository,
                userRepository,
                roleConverter,
                userConverter,
                authorityRepository,
                authorityConverter,
                userLogic);
    }

    @Test
    void createUserAuthority() {

    }
}
