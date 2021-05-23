package com.doge.tip.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user")
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "user_id", nullable = false, updatable = false)
    UUID userId;

    @Max(value = 100, message = "The length in characters in user name must be less than 100!")
    @Column(name = "user_name", nullable = false)
    String userName;

    @Column(name = "user_password", nullable = false)
    String password;

    @Column(name = "user_roles", nullable = false)
    Set<Roles> userRoles;
}
