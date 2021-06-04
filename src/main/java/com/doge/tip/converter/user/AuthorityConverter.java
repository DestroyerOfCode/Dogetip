package com.doge.tip.converter.user;

import com.doge.tip.dto.user.AuthorityDTO;
import com.doge.tip.model.user.Authority;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorityConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public AuthorityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Authority toEntity(AuthorityDTO dto) {
        Authority authority = modelMapper.map(dto, Authority.class);
        return authority;
    }

    public AuthorityDTO toDTO(Authority role) {
        AuthorityDTO dto = modelMapper.map(role, AuthorityDTO.class);
        return dto;
    }
}
