package com.doge.tip.dto.user;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorityDTO {
    private UUID id;
    private String name;
    private String description;
}
