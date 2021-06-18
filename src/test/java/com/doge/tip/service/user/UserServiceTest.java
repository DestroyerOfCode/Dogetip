package com.doge.tip.service.user;

import com.doge.tip.businesslogic.user.UserLogic;
import com.doge.tip.common.user.UserCommonTest;
import com.doge.tip.converter.user.AuthorityConverter;
import com.doge.tip.converter.user.RoleConverter;
import com.doge.tip.converter.user.UserConverter;
import com.doge.tip.dto.user.AuthorityDTO;
import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.dto.user.UserDTO;
import com.doge.tip.exception.common.APIRequestException;
import com.doge.tip.model.domain.user.Authority;
import com.doge.tip.model.domain.user.Role;
import com.doge.tip.model.repository.user.AuthorityRepository;
import com.doge.tip.model.repository.user.RoleRepository;
import com.doge.tip.model.repository.user.UserRepository;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.*;

public class UserServiceTest extends UserCommonTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private final UserConverter userConverter = new UserConverter(modelMapper);

    static RoleRepository roleRepository;
    static UserRepository userRepository;
    static AuthorityRepository authorityRepository;
    static RoleConverter roleConverter;
//    static UserConverter userConverter;
    static AuthorityConverter authorityConverter;
    static UserLogic userLogic;

    private final UserService userService = new UserService(roleRepository,
            userRepository,
            roleConverter,
            userConverter,
            authorityRepository,
            authorityConverter,
            userLogic);

    @BeforeAll
    static void setUpMocks() {
        roleRepository = mock(RoleRepository.class);
        userRepository = mock(UserRepository.class);
        authorityRepository = mock(AuthorityRepository.class);
        roleConverter = mock(RoleConverter.class);
        authorityConverter = mock(AuthorityConverter.class);
        userLogic = mock(UserLogic.class);
    }


    @Test
    @DisplayName(value = "create user role")
    void createUserRole() {
        when(userLogic.assignExistingAuthorities(roleDTO.getAuthorities())).thenReturn(roleDTO.getAuthorities());
        when(roleConverter.toEntity(roleDTO)).thenReturn(role);
        when(roleConverter.toDTO(role)).thenReturn(roleDTO);
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        RoleDTO dto = userService.createUserRole(roleDTO);
        Assertions.assertEquals(roleDTO, dto);
    }


    @Test
    @DisplayName(value = "Creates authority and nothing else")
    void createUserAuthority() {

        when(authorityConverter.toEntity(authorityDTO)).thenReturn(authority);
        when(authorityConverter.toDTO(authority)).thenReturn(authorityDTO);
        when(authorityRepository.save(any(Authority.class))).thenReturn(authority);

        AuthorityDTO dto = userService.createAuthority(authorityDTO);
        Assertions.assertEquals(dto, authorityDTO);
    }

    @Test
    @DisplayName(value = "Throws exception when creating an authority")
    void createUserAuthorityThrowsException() {

        when(authorityConverter.toEntity(authorityDTO)).thenReturn(authority);
        when(authorityRepository.save(authority)).thenThrow(new RuntimeException("exception thrown"));

        APIRequestException exception = Assertions.assertThrows(APIRequestException.class,
                () -> userService.createAuthority(authorityDTO), () ->
                "wrong Exception thrown");
        Assertions.assertEquals("error trying to persist entity com.doge.tip.dto.user.AuthorityDTO with name" +
                        " sendAdminDogeAuthority! Error message: exception thrown",
                exception.getMessage()
        );
    }

    @Test
    @DisplayName(value = "Try creating user role but throw exception")
    void createUserRoleThrowException() {
        when(userLogic.assignExistingAuthorities(roleDTO.getAuthorities())).thenReturn(roleDTO.getAuthorities());
        when(roleConverter.toEntity(roleDTO)).thenReturn(role);
        when(roleRepository.save(any(Role.class))).thenThrow(new RuntimeException("Exception thrown"));

        APIRequestException apiRequestException = Assertions.assertThrows(APIRequestException.class, () ->
                userService.createUserRole(roleDTO),
                () -> "incorrect exception thrown");
        Assertions.assertEquals(apiRequestException.getMessage(), "error trying to persist entity" +
                " com.doge.tip.dto.user.RoleDTO with name admin! Error message: Exception thrown");
    }

    @Test
    @DisplayName(value = "Try creating user but throw exception")
    void createUserThrowsException() {
        when(userRepository.save(any())).thenThrow(new RuntimeException("Exception thrown"));

        APIRequestException apiRequestException = Assertions.assertThrows(APIRequestException.class, () ->
                userService.createUser(userDTO),
                () -> "incorrect exception thrown");
        Assertions.assertEquals(apiRequestException.getMessage(), "error trying to persist entity" +
                " com.doge.tip.dto.user.UserDTO with name Jozef! Error message: Exception thrown");
    }
}
