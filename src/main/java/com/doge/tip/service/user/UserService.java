package com.doge.tip.service.user;

import com.doge.tip.businesslogic.user.UserLogic;
import com.doge.tip.converter.user.RoleConverter;
import com.doge.tip.converter.user.UserConverter;
import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.dto.user.UserDTO;
import com.doge.tip.model.user.Role;
import com.doge.tip.model.user.RoleRepository;
import com.doge.tip.model.user.User;
import com.doge.tip.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleConverter roleConverter;
    private final UserConverter userConverter;
    private final UserLogic userLogic;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository,
                       RoleConverter roleConverter, UserConverter userConverter,
                       UserLogic userLogic) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.roleConverter = roleConverter;
        this.userConverter = userConverter;
        this.userLogic = userLogic;
    }

    public Optional<RoleDTO> createUserRole(RoleDTO dto) {
        try {
            Role role = roleConverter.toEntity(dto);;
            roleRepository.save(role);
        } catch (Exception e) {
            System.out.println("oopsie");
        }
        return Optional.of(dto);
    }

    public Optional<UserDTO> createUser(UserDTO dto) {
        try {
            dto.setPassword(userLogic.encryptUserPassword(dto.getPassword()));
            dto.setUserRoles(userLogic.assignExistingRoles(dto.getUserRoles()));
            User user = userConverter.toEntity(dto);

            userRepository.save(user);
        } catch (Exception e) {
            System.out.println("oopsie");
        }
        return Optional.of(dto);
    }
}
