package com.doge.tip.dto.user;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO {

    private UUID id;
    private String name;
    private String description;
    private Set<AuthorityDTO> authorities;
}
