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

    private UUID roleId;
    private String roleName;
    private String roleDescription;
    private Set<AuthorityDTO> authorities;
}
