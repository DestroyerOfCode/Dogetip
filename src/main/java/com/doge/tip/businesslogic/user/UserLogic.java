package com.doge.tip.businesslogic.user;

import com.doge.tip.converter.user.AuthorityConverter;
import com.doge.tip.converter.user.RoleConverter;
import com.doge.tip.dto.user.AuthorityDTO;
import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.exception.user.MissingElementException;
import com.doge.tip.model.domain.user.Authority;
import com.doge.tip.model.repository.user.AuthorityRepository;
import com.doge.tip.model.domain.user.Role;
import com.doge.tip.model.repository.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserLogic {

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
            System.out.println("oops");
        }
        throw new RuntimeException("error encrypting password");
    }

    public Set<RoleDTO> assignExistingRoles(Set<RoleDTO> userDTOUserRoles) {
        Set<Role> userRoles = new HashSet<>();
        userDTOUserRoles.forEach(x -> userRoles.add(roleRepository.getRoleByRoleName(x.getRoleName())
                .orElseThrow(() -> new MissingElementException(String.format("Cannot find Role %s." +
                        "Perhaps try inserting the role before assigning to a user.", x.getRoleName())))
                )
        );
        return userRoles.stream().map(roleConverter::toDTO).collect(Collectors.toSet());
    }

    public Set<AuthorityDTO> assignExistingAuthorities(Set<AuthorityDTO> authorityDTOS) {
        Set<Authority> authorities = new HashSet<>();
        authorityDTOS.forEach(x -> authorities.add(authorityRepository.getAuthorityByAuthorityName(x.getAuthorityName())
                .orElseThrow(() -> new MissingElementException(String.format("Cannot find authority %s." +
                        "Perhaps try inserting the authority before assigning to a role.", x.getAuthorityName())))
                )
        );
        return authorities.stream().map(authorityConverter::toDTO).collect(Collectors.toSet());
    }
}
