package com.doge.tip.model.user;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Entity(name = "User")
@Table(name = "user", schema = "public")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "userId")
public class User {

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
    @Column(name = "user_id", nullable = false, updatable = false, unique = true)
    private UUID userId;

    @Size(max = 100)
    @NotBlank(message = "The user name must NOT be left without any characters!")
    @NotNull(message = "The value userName must be NOT null and hence must be set!")
    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Size(min = 1, max = 100)
    @NotBlank(message = "The password must NOT be left without any characters!")
    @NotNull
    @Column(name = "user_password", nullable = false, length = 100)
    private String password;

//    @Column(name = "user_roles", nullable = false)
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_roles", referencedColumnName = "user")
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"),
//            foreignKey = @ForeignKey(name = "user_role_fk")
//    )

    @NotEmpty(message = "You must assign at least a single role to user")
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            targetEntity = Role.class
    )
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
//    @JoinColumn(name = "user_id"
//              nullable = false
//              table = "Role",
//              foreignKey = @ForeignKey(name = "user_role_fk"),
//                referencedColumnName = "role_id"

//    )
//    @MapsId
    private Set<Role> userRoles;
}
