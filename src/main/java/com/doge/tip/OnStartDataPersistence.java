package com.doge.tip;

import com.doge.tip.businesslogic.user.UserLogic;
import com.doge.tip.model.user.Role;
import com.doge.tip.model.user.RoleRepository;
import com.doge.tip.model.user.User;
import com.doge.tip.model.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class OnStartDataPersistence implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(OnStartDataPersistence.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserLogic userLogic;


    private final Role adminRole = Role.builder().roleName("admin").roleDescription("Admin Role").build();
    private final Role userRole = Role.builder().roleName("user").roleDescription("User Role").build();

    @Autowired
    public OnStartDataPersistence(RoleRepository roleRepository, UserRepository userRepository, UserLogic userLogic) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userLogic = userLogic;
    }

    @Override
    public void run(String...args) throws Exception {

        persistRoles();
        persistUsers();

        LOG.info("Finished persisting initial data");
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
