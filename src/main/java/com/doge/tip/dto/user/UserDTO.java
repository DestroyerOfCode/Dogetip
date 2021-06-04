package com.doge.tip.dto.user;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private UUID userId;
    private String userName;
    private String password;
    private Set<RoleDTO> userRoles;
}
