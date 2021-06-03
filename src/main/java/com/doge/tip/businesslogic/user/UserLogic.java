package com.doge.tip.businesslogic.user;

import com.doge.tip.converter.user.RoleConverter;
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

    public void encryptUserPassword(UserDTO dto) {
        try {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        } catch (RuntimeException e) {
            System.out.println("oops");
        }
    }

    public void assignExistingRoles(UserDTO userDTO) {
        Set<Role> userRoles = new HashSet<>();
        if (!userDTO.getUserRoles().stream().allMatch(x -> {
            ArrayList<Role> selectedUserRoles = (ArrayList<Role>) roleRepository.getRolesByRoleName(x.getRoleName());
            if (selectedUserRoles != null && !selectedUserRoles.isEmpty()) {
                userRoles.add(selectedUserRoles.get(0));
                return true;
            }
            return false;
        }))
            throw new RuntimeException("Unexisting roles");
        userDTO.setUserRoles(userRoles.stream().map(roleConverter::toDTO).collect(Collectors.toSet()));
    }
}
