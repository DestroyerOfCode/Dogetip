package com.doge.tip.controller.implementation.user;

import com.doge.tip.dto.user.AuthorityDTO;
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
    public ResponseEntity<RoleDTO> createUserRole(@RequestBody RoleDTO dto) {
        if (userService.createUserRole(dto).isPresent())
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "user/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
        if (userService.createUser(dto).isPresent())
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "authority/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorityDTO> createAuthority(@RequestBody AuthorityDTO dto) {
        if (userService.createAuthority(dto).isPresent())
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
