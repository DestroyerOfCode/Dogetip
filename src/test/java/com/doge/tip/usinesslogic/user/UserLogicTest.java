package com.doge.tip.usinesslogic.user;

import com.doge.tip.businesslogic.user.UserLogic;
import com.doge.tip.converter.user.AuthorityConverter;
import com.doge.tip.converter.user.RoleConverter;
import com.doge.tip.dto.user.AuthorityDTO;
import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.exception.user.MissingElementException;
import com.doge.tip.model.domain.user.Authority;
import com.doge.tip.model.domain.user.Role;
import com.doge.tip.model.repository.user.AuthorityRepository;
import com.doge.tip.model.repository.user.RoleRepository;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserLogicTest {

    static PasswordEncoder passwordEncoder;
    static RoleRepository roleRepository;
    static RoleConverter roleConverter;
    static AuthorityConverter authorityConverter;
    static AuthorityRepository authorityRepository;

    UserLogic userLogic = new UserLogic(passwordEncoder,
            roleRepository,
            roleConverter,
            authorityConverter,
            authorityRepository);

    AuthorityDTO authorityDTO;
    Authority authority;
    RoleDTO roleDTO;
    Role role;

    @BeforeAll
    static void setUpMocks() {
        passwordEncoder = mock(PasswordEncoder.class);
        roleRepository = mock(RoleRepository.class);
        roleConverter = mock(RoleConverter.class);
        authorityConverter = mock(AuthorityConverter.class);
        authorityRepository = mock(AuthorityRepository.class);
    }

    @BeforeEach
    void setUpObjects() {

        authorityDTO = AuthorityDTO.builder().name("sendAdminDogeAuthority").build();
        authority = Authority.builder().name("sendAdminDogeAuthority").build();
        roleDTO = RoleDTO.builder().name("admin").description("admin role")
                .authorities(new HashSet<>(Stream.of(authorityDTO).collect(Collectors.toSet())))
                .build();
        role = Role.builder().name("admin").description("admin role")
                .authorities(new HashSet<>(Stream.of(authority).collect(Collectors.toSet())))
                .build();
    }

    @AfterEach
    void cleanUpObjects() {
        authorityDTO = null;
        authority = null;
        roleDTO = null;
        role = null;
    }

    @Test
    @DisplayName(value = "encrypt string using Bcrypt algorithm")
    void encryptPasswordWithBcrypt(TestInfo testInfo, TestReporter testReporter) {
        String plainTextPassword = "";
        when(passwordEncoder.encode(plainTextPassword)).thenReturn("encryptedPassword");
        assertEquals(assertDoesNotThrow( () -> userLogic.encryptUserPassword(plainTextPassword),
                () -> "the method encryptUserPassword has thrown an unexpected exception"),
                "encryptedPassword");
    }

    @Test
    @DisplayName(value = "encrypt string using Bcrypt algorithm but expect thrown expection")
    void encryptPasswordWithBcryptExpectException(TestInfo testInfo, TestReporter testReporter) {
        testInfo.getDisplayName();
        when(passwordEncoder.encode("")).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class,
                () -> userLogic.encryptUserPassword(""),
                () -> "expected exception");

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
        assertTrue(authoritiesDTO.stream().allMatch(authorityDTO -> authorityDTO.getName().equals("sendAdminDogeAuthority")));
    }

    @Test
    @DisplayName(value = "Check whether authority exist and expect to fail because assigning a nonexisting authority")
    void assignNonExistingAuthoritiesToUserEntity(TestInfo testInfo, TestReporter testReporter) {

        when(authorityRepository.getAuthorityByName("sendAdminDogeAuthority")).thenReturn(Optional.empty());

        assertThrows(MissingElementException.class,
                () -> userLogic.assignExistingAuthorities(Stream.of(authorityDTO).collect(Collectors.toSet())),
                () -> "expected exception to be thrown!");

    }
}
