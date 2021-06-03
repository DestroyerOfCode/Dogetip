package com.doge.tip.model.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity(name = "Role")
@Table(name = "role", schema = "public")
@Getter
@Setter
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
    @Column(name = "role_id", nullable = false, unique = true)
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

//    @ManyToMany(mappedBy = "userRoles", targetEntity = User.class)
//    private Set<User> users;


//    @ManyToMany
//    @JoinTable(
//            name = "role_authorities",
//            joinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "user_id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "authority_id", referencedColumnName = "role_id"))
//    private List<Authorities> authorities;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "role_authorities", referencedColumnName = "role")
//    private Set<Authorities> roleAuthorities;
}
