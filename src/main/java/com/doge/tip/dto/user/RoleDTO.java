package com.doge.tip.dto.user;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private UUID roleId;
    private String roleName;
    private String roleDescription;
}
