package com.doge.tip.common.user;

import com.doge.tip.dto.user.AuthorityDTO;
import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.dto.user.UserDTO;
import com.doge.tip.model.domain.user.Authority;
import com.doge.tip.model.domain.user.Role;
import com.doge.tip.model.domain.user.User;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public abstract class UserCommonTest {

    protected static AuthorityDTO authorityDTO;
    protected static Authority authority;
    protected static UserDTO userDTO;
    protected static User user;
    protected static RoleDTO roleDTO;
    protected static Role role;

    @BeforeAll
    static void setUpClass() {
        authorityDTO = AuthorityDTO.builder().name("sendAdminDogeAuthority").description("sendAdminDogeAuthority role")
                .build();
        authority = Authority.builder().name("sendAdminDogeAuthority").description("sendAdminDogeAuthority role")
                .build();
        roleDTO = RoleDTO.builder().name("admin").description("admin role")
                .authorities(new HashSet<>(Stream.of(authorityDTO).collect(Collectors.toSet())))
                .build();
        role = Role.builder().name("admin").description("admin role")
                .authorities(new HashSet<>(Stream.of(authority).collect(Collectors.toSet())))
                .build();
        userDTO = UserDTO.builder().name("Jozef").password("password")
                .userRoles(new HashSet<>(Stream.of(roleDTO).collect(Collectors.toSet())))
                .build();
        user = User.builder().name("Jozef").password("password")
                .userRoles(new HashSet<>(Stream.of(role).collect(Collectors.toSet())))
                .build();

    }

    @BeforeEach
    void setUp() {
        int i =3;
    }
    @Test
    void test() {}
}
