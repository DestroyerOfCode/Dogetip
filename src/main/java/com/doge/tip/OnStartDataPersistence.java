package com.doge.tip;

import com.doge.tip.businesslogic.user.UserLogic;
import com.doge.tip.model.domain.user.*;
import com.doge.tip.model.repository.user.AuthorityRepository;
import com.doge.tip.model.repository.user.RoleRepository;
import com.doge.tip.model.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class OnStartDataPersistence implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(OnStartDataPersistence.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserLogic userLogic;

    private final Authority authoritySendDoge = Authority.builder().authorityName("sendDogeAuthority")
            .authorityDescription("authority to Send Doge").build();
    private final Authority authoritySendAdminDoge = Authority.builder().authorityName("sendAdminDogeAuthority")
            .authorityDescription("Authority to send Admin Doge").build();

    private final Role adminRole = Role.builder().roleName("admin").roleDescription("Admin Role")
            .authorities(new HashSet<>(Stream.of(authoritySendDoge, authoritySendAdminDoge)
                .collect(Collectors.toSet())))
            .build();
    private final Role userRole = Role.builder().roleName("user").roleDescription("User Role")
            .authorities(new HashSet<>(Stream.of(authoritySendDoge).collect(Collectors.toSet())))
            .build();

    @Autowired
    public OnStartDataPersistence(RoleRepository roleRepository, UserRepository userRepository,
                                  AuthorityRepository authorityRepository, UserLogic userLogic) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.userLogic = userLogic;
    }

    @Override
    public void run(String...args) throws Exception {

        persistAuhorities();
        persistRoles();
        persistUsers();

        LOG.info("Finished persisting initial data");
    }

    private void persistAuhorities() {
        authorityRepository.save(authoritySendDoge);
        authorityRepository.save(authoritySendAdminDoge);
    }

    private void persistRoles() {
        roleRepository.save(adminRole);
        roleRepository.save(userRole);
    }

    private void persistUsers() {
        userRepository.save(User.builder().userName("Jozef").userRoles(Stream.of(adminRole, userRole)
                .collect(Collectors.toSet())).password(userLogic.encryptUserPassword("1234")).build());
        userRepository.save(User.builder().userName("Pavol").userRoles(Stream.of(userRole)
                .collect(Collectors.toSet())).password(userLogic.encryptUserPassword("1234")).build());
    }
}
