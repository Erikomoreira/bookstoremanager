package com.erik.bookstoremanager.author.service;

import com.erik.bookstoremanager.author.dto.AuthorDTO;
import com.erik.bookstoremanager.author.entity.Author;
import com.erik.bookstoremanager.author.exception.AuthorAlreadyExistsException;
import com.erik.bookstoremanager.author.exception.AuthorNotFoundException;
import com.erik.bookstoremanager.author.mapper.AuthorMapper;
import com.erik.bookstoremanager.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private static final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDTO create(AuthorDTO authorDTO) {
        verifyIfExists(authorDTO.getName());

        var authorToCreate = authorMapper.toModel(authorDTO);
        var createdAuthor = authorRepository.save(authorToCreate);
        return authorMapper.toDTO(createdAuthor);
    }

    public AuthorDTO findById(Long id) {
        var foundAuthor = verifyAndGetIfExists(id);
        return authorMapper.toDTO(foundAuthor);
    }

    public List<AuthorDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO).collect(Collectors.toList());
    }

    public void delete(Long id){
        verifyAndGetIfExists(id);
        authorRepository.deleteById(id);
    }

    private void verifyIfExists(String authorName) {
        authorRepository.findByName(authorName)
                .ifPresent(author -> {
                    throw new AuthorAlreadyExistsException(authorName);
                });
    }

    public Author verifyAndGetIfExists(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }

}
