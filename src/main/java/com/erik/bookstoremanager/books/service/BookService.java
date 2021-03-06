package com.erik.bookstoremanager.books.service;

import com.erik.bookstoremanager.author.service.AuthorService;
import com.erik.bookstoremanager.books.dto.BookRequestDTO;
import com.erik.bookstoremanager.books.dto.BookResponseDTO;
import com.erik.bookstoremanager.books.entity.Book;
import com.erik.bookstoremanager.books.exception.BookAlreadyExistsException;
import com.erik.bookstoremanager.books.exception.BookNotFoundException;
import com.erik.bookstoremanager.books.mapper.BookMapper;
import com.erik.bookstoremanager.books.repository.BookRespository;
import com.erik.bookstoremanager.publishers.service.PublisherService;
import com.erik.bookstoremanager.users.dto.AuthenticatedUser;
import com.erik.bookstoremanager.users.entity.User;
import com.erik.bookstoremanager.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        var foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        var foundAuthor = authorService.verifyAndGetIfExists(bookRequestDTO.getAuthorId());
        var foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

        verifyIfBookIsAlreadyRegistered(foundAuthenticatedUser, bookRequestDTO);

        var bookToSave = bookMapper.toModel(bookRequestDTO);
        bookToSave.setUser(foundAuthenticatedUser);
        bookToSave.setAuthor(foundAuthor);
        bookToSave.setPublisher(foundPublisher);
        var savedBook = bookRespository.save(bookToSave);

        return bookMapper.toDTO(savedBook);
    }

    public BookResponseDTO findByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId) {
        var foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRespository.findByIdAndUser(bookId, foundAuthenticatedUser)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    public List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser) {
        var foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRespository.findAllByUser(foundAuthenticatedUser)
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId) {
        var foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        var foundBookToDelete = verifyAndGetIfExists(bookId, foundAuthenticatedUser);

        bookRespository.deleteByIdAndUser(foundBookToDelete.getId(), foundAuthenticatedUser);
    }

    public BookResponseDTO updateByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId, BookRequestDTO bookRequestDTO){
        var foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        var foundBook = verifyAndGetIfExists(bookId, foundAuthenticatedUser);
        var foundAuthor = authorService.verifyAndGetIfExists(bookRequestDTO.getAuthorId());
        var foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());


        var bookToUpdate = bookMapper.toModel(bookRequestDTO);
        bookToUpdate.setId(foundBook.getId());
        bookToUpdate.setUser(foundAuthenticatedUser);
        bookToUpdate.setAuthor(foundAuthor);
        bookToUpdate.setPublisher(foundPublisher);
        bookToUpdate.setCreatedDate(foundBook.getCreatedDate());
        var updatedBook = bookRespository.save(bookToUpdate);
        return bookMapper.toDTO(updatedBook);
    }

    private Book verifyAndGetIfExists(Long bookId, User foundAuthenticatedUser) {
        return bookRespository.findByIdAndUser(bookId, foundAuthenticatedUser)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    private void verifyIfBookIsAlreadyRegistered(User foundUser, BookRequestDTO bookRequestDTO) {
        bookRespository.findByNameAndIsbnAndUser(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser)
                .ifPresent(duplicatedBook -> {
                    throw new BookAlreadyExistsException(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser.getUsername());
                });
    }
}
