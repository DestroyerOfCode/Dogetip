package com.doge.tip.controller.implementation.user;

import com.doge.tip.dto.user.AuthorityDTO;
import com.doge.tip.dto.user.RoleDTO;
import com.doge.tip.dto.user.UserDTO;
import com.doge.tip.exception.common.APIRequestException;
import com.doge.tip.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

@RestController(value = "user")
@RequestMapping(value = "user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "role/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDTO> createUserRole(@RequestBody RoleDTO dto) {
            return new ResponseEntity<>(userService.createUserRole(dto), HttpStatus.CREATED);
    }

    @PostMapping(value = "user/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
            return new ResponseEntity<>(userService.createUser(dto), HttpStatus.CREATED);
    }

    @PostMapping(value = "authority/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorityDTO> createAuthority(@RequestBody AuthorityDTO dto) {
            return new ResponseEntity<>(userService.createAuthority(dto), HttpStatus.CREATED);
    }
}
