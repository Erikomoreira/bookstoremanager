package com.erik.bookstoremanager.books.service;

import com.erik.bookstoremanager.author.service.AuthorService;
import com.erik.bookstoremanager.books.mapper.BookMapper;
import com.erik.bookstoremanager.books.repository.BookRespository;
import com.erik.bookstoremanager.publishers.service.PublisherService;
import com.erik.bookstoremanager.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookRespository bookRespository;

    private UserService userService;

    private AuthorService authorService;

    private PublisherService publisherService;


}
