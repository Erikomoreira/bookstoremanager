package com.erik.bookstoremanager.books.controller;

import com.erik.bookstoremanager.books.builder.BookRequestDTOBuilder;
import com.erik.bookstoremanager.books.builder.BookResponseDTOBuilder;
import com.erik.bookstoremanager.books.dto.BookRequestDTO;
import com.erik.bookstoremanager.books.dto.BookResponseDTO;
import com.erik.bookstoremanager.books.service.BookService;
import com.erik.bookstoremanager.users.dto.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.erik.bookstoremanager.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    private static final String BOOKS_API_URL_PATH = "/api/v1/books";

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    private BookRequestDTOBuilder bookRequestDTOBuilder;

    private BookResponseDTOBuilder bookResponseDTOBuilder;

    @BeforeEach
    void setUp() {
        bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
        bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .addFilters()
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeReturned() throws Exception {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildResponseBookDTO();

        when(bookService.create(any(AuthenticatedUser.class), eq(expectedBookToCreateDTO))).thenReturn(expectedCreatedBookDTO);

        mockMvc.perform(post(BOOKS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedBookToCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedCreatedBookDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedCreatedBookDTO.getName())))
                .andExpect(jsonPath("$.isbn", is(expectedCreatedBookDTO.getIsbn())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldsThenBadRequestShouldBeReturned() throws Exception {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        expectedBookToCreateDTO.setIsbn(null);

        mockMvc.perform(post(BOOKS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedBookToCreateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidBookThenStatusOkShouldBeInformed() throws Exception {
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildResponseBookDTO();

        when(bookService.findByIdAndUser(any(AuthenticatedUser.class), eq(expectedBookToFindDTO.getId()))).thenReturn(expectedFoundBookDTO);

        mockMvc.perform(get(BOOKS_API_URL_PATH + "/" + expectedBookToFindDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedFoundBookDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedFoundBookDTO.getName())))
                .andExpect(jsonPath("$.isbn", is(expectedFoundBookDTO.getIsbn())));
    }

    @Test
    void whenGETListIsCalledThenStatusOkShouldBeInformed() throws Exception {
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildResponseBookDTO();

        when(bookService.findAllByUser(any(AuthenticatedUser.class))).thenReturn(Collections.singletonList(expectedFoundBookDTO));

        mockMvc.perform(get(BOOKS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedFoundBookDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(expectedFoundBookDTO.getName())))
                .andExpect(jsonPath("$[0].isbn", is(expectedFoundBookDTO.getIsbn())));
    }
}
