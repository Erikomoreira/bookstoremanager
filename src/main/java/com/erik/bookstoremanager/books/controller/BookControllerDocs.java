package com.erik.bookstoremanager.books.controller;

import com.erik.bookstoremanager.books.dto.BookRequestDTO;
import com.erik.bookstoremanager.books.dto.BookResponseDTO;
import com.erik.bookstoremanager.users.dto.AuthenticatedUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

@Api("Books module management")
public interface BookControllerDocs {

    @ApiOperation(value = "Book creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success book creation"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or book already registered on system")
    })
    BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO);

    @ApiOperation(value = "Book find by id an user operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success book found"),
            @ApiResponse(code = 404, message = "Book not found error")
    })
    BookResponseDTO findByIdAndUser(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long bookId);
}
