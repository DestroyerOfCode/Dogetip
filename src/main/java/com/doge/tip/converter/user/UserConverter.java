package com.doge.tip.converter.user;

import com.doge.tip.dto.user.UserDTO;
import com.doge.tip.model.domain.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toEntity(UserDTO dto) {
        User user = modelMapper.map(dto, User.class);
        return user;
    }

    public UserDTO toDTO(User role) {
        UserDTO dto = modelMapper.map(role, UserDTO.class);
        return dto;
    }
}
