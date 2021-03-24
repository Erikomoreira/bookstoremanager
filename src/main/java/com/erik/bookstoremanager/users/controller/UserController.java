package com.erik.bookstoremanager.users.controller;

import com.erik.bookstoremanager.users.dto.MessageDTO;
import com.erik.bookstoremanager.users.dto.UserDTO;
import com.erik.bookstoremanager.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserControllerDocs{

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO create(@RequestBody @Valid UserDTO userCreateDTO) {
        return userService.create(userCreateDTO);
    }
}
