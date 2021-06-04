package com.doge.tip.model.user;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity(name = "Role")
@Table(name = "role", schema = "public")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "pg-uuid"
    )
    @GenericGenerator(
            name = "pg-uuid",
            strategy = "uuid2"
    )
    @NotNull(message = "The value userId must be NOT null and hence must be set!")
    @Column(name = "role_id", nullable = false, unique = true, updatable = false, insertable = false)
    private UUID roleId;

    @Size(max = 100)
    @NotBlank(message = "The role name must NOT be left without any characters!")
    @NotNull(message = "The role name must NOT be without VALUE!")
    @Column(name = "role_name", nullable = false, length = 100)
    private String roleName;

    @Size(max = 100)
    @NotBlank(message = "The role description must NOT be left without any characters!")
    @NotNull(message = "The role description must NOT be without VALUE!")
    @Column(name = "role_description", length = 100)
    private String roleDescription;

    @NotEmpty(message = "Each role must have at least a single authority! Insert an authority to the role")
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Authority.class)
    @JoinTable(
            name = "roles_authorities",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities;

}
