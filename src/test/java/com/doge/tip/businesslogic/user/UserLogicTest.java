package com.doge.tip.businesslogic.user;

import com.doge.tip.common.user.UserCommonTest;
import com.doge.tip.converter.user.AuthorityConverter;
import com.doge.tip.converter.user.RoleConverter;
import com.doge.tip.dto.user.AuthorityDTO;
import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.exception.user.MissingElementException;
import com.doge.tip.model.domain.user.User;
import com.doge.tip.model.repository.user.AuthorityRepository;
import com.doge.tip.model.repository.user.RoleRepository;
import com.doge.tip.model.repository.user.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserLogicTest extends UserCommonTest {

    static PasswordEncoder passwordEncoder;
    static RoleRepository roleRepository;
    static RoleConverter roleConverter;
    static AuthorityConverter authorityConverter;
    static AuthorityRepository authorityRepository;
    static UserRepository userRepository;

    private UserLogic userLogic;

    @BeforeAll
    static void setUpMocks() {
        passwordEncoder = mock(PasswordEncoder.class);
        roleRepository = mock(RoleRepository.class);
        roleConverter = mock(RoleConverter.class);
        authorityConverter = mock(AuthorityConverter.class);
        authorityRepository = mock(AuthorityRepository.class);
        userRepository = mock(UserRepository.class);
    }

    @BeforeEach
    void setUp() {
        userLogic  = new UserLogic(passwordEncoder, roleRepository, roleConverter, authorityConverter,
                authorityRepository, userRepository);
    }

    @AfterEach
    void cleanUpObjects() {
        userLogic = null;
    }

    @ParameterizedTest
    @MethodSource(value = "providePasswordsForEncryption")
    @DisplayName(value = "encrypt string using Bcrypt algorithm")
    void encryptPasswordWithBcrypt(String plainTextPassword, String result, TestInfo testInfo, TestReporter testReporter) {
        when(passwordEncoder.encode(plainTextPassword)).thenReturn(result);
        assertEquals(assertDoesNotThrow( () -> userLogic.encryptUserPassword(plainTextPassword),
                () -> "the method encryptUserPassword has thrown an unexpected exception"),
                result);
    }

    private static Stream<Arguments> providePasswordsForEncryption() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return Stream.of(
                Arguments.of("password", passwordEncoder.encode("password")),
                Arguments.of("", passwordEncoder.encode("")),
                Arguments.of("    ", passwordEncoder.encode("    "))
        );
    }

    @Test
    @DisplayName(value = "encrypt string using Bcrypt algorithm but expect thrown expection")
    void encryptPasswordWithBcryptExpectException(TestInfo testInfo, TestReporter testReporter) {
        when(passwordEncoder.encode(null)).thenThrow(IllegalArgumentException.class);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userLogic.encryptUserPassword(null),
                () -> "expected exception");
        Assertions.assertEquals(exception.getMessage(), "error encrypting password");

    }

    @Test
    @DisplayName(value = "Check whether roles exist and if yes, assign them to user entity")
    void assignExistingRoles() {
        when(roleRepository.getRoleByName("admin")).thenReturn(Optional.of(role));
        when(roleConverter.toDTO(role)).thenReturn(roleDTO);

        Set<RoleDTO> userRoles = userLogic.assignExistingRoles(Stream.of(roleDTO).collect(Collectors.toSet()));
        assertTrue(userRoles.stream().allMatch(userRole -> userRole.getName().equals("admin")));
    }

    @Test
    @DisplayName(value = "Check whether roles exist and expect to fail because assigning a nonexisting role")
    void assignNonExistingRole(TestInfo testInfo, TestReporter testReporter) {
        when(roleRepository.getRoleByName("admin")).thenReturn(Optional.empty());

        assertThrows(MissingElementException.class,
                () -> userLogic.assignExistingRoles(Stream.of(roleDTO).collect(Collectors.toSet())),
                () -> "expected exception to be thrown!");

    }

    @Test
    @DisplayName(value = "Check whether authority exist and if yes, assign them to role entity")
    void assignExistingAuthoritiesToUserEntity() {

        when(authorityRepository.getAuthorityByName("sendAdminDogeAuthority")).thenReturn(Optional.of(authority));
        when(authorityConverter.toDTO(authority)).thenReturn(authorityDTO);

        Set<AuthorityDTO> authoritiesDTO = userLogic.assignExistingAuthorities(Stream.of(authorityDTO)
                .collect(Collectors.toSet()));
        assertTrue(authoritiesDTO.stream().allMatch(authorityDTO -> authorityDTO.getName()
                .equals("sendAdminDogeAuthority"))
        );
    }

    @Test
    @DisplayName(value = "Check whether authority exist and expect to fail because assigning a nonexisting authority")
    void assignNonExistingAuthoritiesToUserEntity(TestInfo testInfo, TestReporter testReporter) {

        when(authorityRepository.getAuthorityByName("sendAdminDogeAuthority")).thenReturn(Optional.empty());

        MissingElementException exception = assertThrows(MissingElementException.class,
                () -> userLogic.assignExistingAuthorities(Stream.of(authorityDTO).collect(Collectors.toSet())),
                () -> "expected exception to be thrown!");

        assertEquals(exception.getMessage(), "Cannot find entity com.doge.tip.dto.user.AuthorityDTO with name " +
                "sendAdminDogeAuthority. Perhaps try inserting the before assigning to a parent entity.");
    }

    @Test
    @DisplayName(value = "Get a user By Id and return user")
    void getExistingUserById() {

        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        Optional<User> newUser = userLogic.getUserById(UUID.randomUUID());
        newUser.ifPresent((User u) -> {
            assertEquals(u.getName(), user.getName());
            assertEquals(u.getPassword(), user.getPassword());
            assertArrayEquals(u.getUserRoles().toArray(), user.getUserRoles().toArray());
        });
    }

    @Test
    @DisplayName(value = "Get all users from user repository and return users")
    void getAllUsers() {

        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);

        userLogic.getAllUsers().forEach((User u) -> {
            assertEquals(u.getName(), user.getName());
            assertEquals(u.getPassword(), user.getPassword());
            assertArrayEquals(u.getUserRoles().toArray(), user.getUserRoles().toArray());
        });
    }
}
