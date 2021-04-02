package com.erik.bookstoremanager.books.service;

import com.erik.bookstoremanager.author.service.AuthorService;
import com.erik.bookstoremanager.books.builder.BookRequestDTOBuilder;
import com.erik.bookstoremanager.books.builder.BookResponseDTOBuilder;
import com.erik.bookstoremanager.books.mapper.BookMapper;
import com.erik.bookstoremanager.books.repository.BookRespository;
import com.erik.bookstoremanager.publishers.service.PublisherService;
import com.erik.bookstoremanager.users.dto.AuthenticatedUser;
import com.erik.bookstoremanager.users.enums.Role;
import com.erik.bookstoremanager.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Mock
    private BookRespository bookRespository;

    @Mock
    private UserService userService;

    @Mock
    private AuthorService authorService;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private BookService bookService;

    private BookRequestDTOBuilder bookRequestDTOBuilder;

    private BookResponseDTOBuilder bookResponseDTOBuilder;

    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    void setUp() {
        bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
        bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();
        authenticatedUser = new AuthenticatedUser("eomoreira", "123456", Role.ADMIN);

    }
}
