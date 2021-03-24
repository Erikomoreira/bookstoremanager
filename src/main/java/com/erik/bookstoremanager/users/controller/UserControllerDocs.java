package com.erik.bookstoremanager.users.controller;

import com.erik.bookstoremanager.users.dto.MessageDTO;
import com.erik.bookstoremanager.users.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("System users management")
public interface UserControllerDocs {

    @ApiOperation(value = "User creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Sucess user creation"),
            @ApiResponse(code = 400, message = "Missing required field, or error on validation field rules")
    })
    MessageDTO create(UserDTO userCreateDTO);
}
