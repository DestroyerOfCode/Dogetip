package com.doge.tip.controller.user;

import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.dto.user.UserDTO;
import com.doge.tip.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "user")
@RequestMapping(value = "user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "role/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDTO> createUserRole(@RequestBody RoleDTO role) {
        if (userService.createUserRole(role).isPresent())
            return new ResponseEntity<>(role, HttpStatus.CREATED);
        throw new RuntimeException("error creating " + role.getRoleName());
    }

    @PostMapping(value = "user/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        if (userService.createUser(user).isPresent())
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        throw new RuntimeException("error creating " + user.getUserName());
    }
}
