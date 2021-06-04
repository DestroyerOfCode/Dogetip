package com.doge.tip.businesslogic.user;

import com.doge.tip.converter.user.AuthorityConverter;
import com.doge.tip.converter.user.RoleConverter;
import com.doge.tip.dto.user.AuthorityDTO;
import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.model.user.Authority;
import com.doge.tip.model.user.AuthorityRepository;
import com.doge.tip.model.user.Role;
import com.doge.tip.model.user.RoleRepository;
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
        if (!userDTOUserRoles.stream().allMatch(x -> {
            Optional<Role> role =  roleRepository.getRoleByRoleName(x.getRoleName());
            if (role.isPresent()) {
                userRoles.add(role.get());
                return true;
            }
            return false;
        }))
            throw new RuntimeException("Unexisting roles");
        return userRoles.stream().map(roleConverter::toDTO).collect(Collectors.toSet());
    }

    public Set<AuthorityDTO> assignExistingAuthorities(Set<AuthorityDTO> authorityDTOS) {
        Set<Authority> authorities = new HashSet<>();
        if (!authorityDTOS.stream().allMatch(x -> {
            Optional<Authority> authority =  authorityRepository.getAuthorityByAuthorityName(x.getAuthorityName());
            if (authority.isPresent()) {
                authorities.add(authority.get());
                return true;
            }
            return false;
        }))
            throw new RuntimeException("Unexisting authorities");
        return authorities.stream().map(authorityConverter::toDTO).collect(Collectors.toSet());
    }
}
