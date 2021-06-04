package com.doge.tip.service.user;

import com.doge.tip.businesslogic.user.UserLogic;
import com.doge.tip.converter.user.AuthorityConverter;
import com.doge.tip.converter.user.RoleConverter;
import com.doge.tip.converter.user.UserConverter;
import com.doge.tip.dto.user.AuthorityDTO;
import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.dto.user.UserDTO;
import com.doge.tip.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleConverter roleConverter;
    private final UserConverter userConverter;
    private final AuthorityConverter authorityConverter;
    private final UserLogic userLogic;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository, RoleConverter roleConverter,
                       UserConverter userConverter, AuthorityRepository authorityRepository,
                       AuthorityConverter authorityConverter, UserLogic userLogic) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.roleConverter = roleConverter;
        this.userConverter = userConverter;
        this.authorityConverter = authorityConverter;
        this.userLogic = userLogic;
    }

    public Optional<RoleDTO> createUserRole(RoleDTO dto) {
        try {
            dto.setAuthorities(userLogic.assignExistingAuthorities(dto.getAuthorities()));

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

    public Optional<AuthorityDTO> createAuthority(AuthorityDTO dto) {
        try {
            Authority authority = authorityConverter.toEntity(dto);

            authorityRepository.save(authority);
        } catch (Exception e) {
            System.out.println("oopsie");
        }
        return Optional.of(dto);
    }
}
