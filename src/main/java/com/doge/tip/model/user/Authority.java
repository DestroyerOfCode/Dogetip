package com.doge.tip.model.user;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

//@Entity
//@Table(name = "authorities", schema = "public")
public class Authority {

//    @Column(name = "roles")
//    @OneToMany(mappedBy = "role_authorities", orphanRemoval = true)
//    private Roles roles;
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "pg-uuid"
    )
    @GenericGenerator(
            name = "pg-uuid",
            strategy = "uuid2"
    )
    private UUID authorityId;

    private String authorityName;

//    @ManyToMany(mappedBy = "authorities")
//    private List<Roles> roles;

}
