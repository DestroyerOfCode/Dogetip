package com.doge.tip.converter.user;

import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.model.user.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {

    private final ModelMapper modelMapper;
    private final UserConverter userConverter;

    @Autowired
    public RoleConverter(ModelMapper modelMapper, UserConverter userConverter) {
        this.modelMapper = modelMapper;
        this.userConverter = userConverter;
    }

    public Role toEntity(RoleDTO dto) {
        Role role = modelMapper.map(dto, Role.class);
        return role;
    }

    public RoleDTO toDTO(Role role) {
        RoleDTO dto = modelMapper.map(role, RoleDTO.class);
        return dto;
    }
}
