package com.doge.tip.usinesslogic.user;

import com.doge.tip.businesslogic.user.UserLogic;
import com.doge.tip.converter.user.AuthorityConverter;
import com.doge.tip.converter.user.RoleConverter;
import com.doge.tip.model.domain.user.User;
import com.doge.tip.model.repository.user.AuthorityRepository;
import com.doge.tip.model.repository.user.RoleRepository;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserLogicTest {

    User user = new User();
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

    @BeforeAll
    static void setUpObjects() {
        passwordEncoder = mock(PasswordEncoder.class);
        roleRepository = mock(RoleRepository.class);
        roleConverter = mock(RoleConverter.class);
        authorityConverter = mock(AuthorityConverter.class);
        authorityRepository = mock(AuthorityRepository.class);
    }

    @Test
    void test(TestInfo testInfo, TestReporter testReporter) {
        var x = 3;
        assertEquals(x, 3, "penis");
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
        when(passwordEncoder.encode("")).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class,
                () -> userLogic.encryptUserPassword(""),
                () -> "expected exception");

    }
}
