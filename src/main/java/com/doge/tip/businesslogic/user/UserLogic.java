package com.doge.tip.businesslogic.user;

import com.doge.tip.converter.user.RoleConverter;
import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.dto.user.UserDTO;
import com.doge.tip.model.user.Role;
import com.doge.tip.model.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserLogic {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    @Autowired
    public UserLogic(PasswordEncoder passwordEncoder, RoleRepository roleRepository, RoleConverter roleConverter) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
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
            ArrayList<Role> selectedUserRoles = (ArrayList<Role>) roleRepository.getRolesByRoleName(x.getRoleName());
            if (selectedUserRoles != null && !selectedUserRoles.isEmpty()) {
                userRoles.add(selectedUserRoles.get(0));
                return true;
            }
            return false;
        }))
            throw new RuntimeException("Unexisting roles");
        return userRoles.stream().map(roleConverter::toDTO).collect(Collectors.toSet());
//                userDTO.setUserRoles(userRoles.stream().map(roleConverter::toDTO).collect(Collectors.toSet()));
    }
}
