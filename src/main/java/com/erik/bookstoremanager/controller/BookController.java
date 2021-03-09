package com.erik.bookstoremanager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
@Api("Book Store Manager API")
public class BookController {

    @ApiOperation(value = "Return an example hello world")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return.")
    })
    @GetMapping
    public String helloWorld() {
        return "Hello Bookstore Manager...............";
    }

}
