package com.erik.bookstoremanager.books.service;

import com.erik.bookstoremanager.author.entity.Author;
import com.erik.bookstoremanager.author.service.AuthorService;
import com.erik.bookstoremanager.books.dto.BookRequestDTO;
import com.erik.bookstoremanager.books.dto.BookResponseDTO;
import com.erik.bookstoremanager.books.entity.Book;
import com.erik.bookstoremanager.books.exception.BookAlreadyExistsException;
import com.erik.bookstoremanager.books.exception.BookNotFoundException;
import com.erik.bookstoremanager.books.mapper.BookMapper;
import com.erik.bookstoremanager.books.repository.BookRespository;
import com.erik.bookstoremanager.publishers.entity.Publisher;
import com.erik.bookstoremanager.publishers.service.PublisherService;
import com.erik.bookstoremanager.users.dto.AuthenticatedUser;
import com.erik.bookstoremanager.users.entity.User;
import com.erik.bookstoremanager.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private static final BookMapper bookMapper = BookMapper.INSTANCE;

    private final BookRespository bookRespository;

    private final UserService userService;

    private final AuthorService authorService;

    private final PublisherService publisherService;

    public BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        Author foundAuthor = authorService.verifyAndGetIfExists(bookRequestDTO.getAuthorId());
        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

        verifyIfBookIsAlreadyRegistered(foundAuthenticatedUser, bookRequestDTO);

        Book bookToSave = bookMapper.toModel(bookRequestDTO);
        bookToSave.setUser(foundAuthenticatedUser);
        bookToSave.setAuthor(foundAuthor);
        bookToSave.setPublisher(foundPublisher);
        Book savedBook = bookRespository.save(bookToSave);

        return bookMapper.toDTO(savedBook);
    }

    public BookResponseDTO findByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRespository.findByIdAndUser(bookId, foundAuthenticatedUser)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    public List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRespository.findAllByUser(foundAuthenticatedUser)
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void verifyIfBookIsAlreadyRegistered(User foundUser, BookRequestDTO bookRequestDTO) {
        bookRespository.findByNameAndIsbnAndUser(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser)
                .ifPresent(duplicatedBook -> {
                    throw new BookAlreadyExistsException(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser.getUsername());
                });
    }
}
