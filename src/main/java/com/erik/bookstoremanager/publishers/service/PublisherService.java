package com.erik.bookstoremanager.publishers.service;

import com.erik.bookstoremanager.publishers.dto.PublisherDTO;
import com.erik.bookstoremanager.publishers.entity.Publisher;
import com.erik.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.erik.bookstoremanager.publishers.exception.PublisherNotFoundException;
import com.erik.bookstoremanager.publishers.mappers.PublisherMapper;
import com.erik.bookstoremanager.publishers.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final static PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public PublisherDTO create(PublisherDTO publisherDTO) {
        verifyIfExists(publisherDTO.getName(), publisherDTO.getCode());
        Publisher publisherToCreate = publisherMapper.toModel(publisherDTO);
        Publisher createdPublisher = publisherRepository.save(publisherToCreate);
        return publisherMapper.toDTO(createdPublisher);
    }

    public PublisherDTO findById(Long id) {
        return publisherRepository.findById(id)
                .map(publisherMapper::toDTO)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    public void delete(Long id) {
        verifyIfExists(id);
        publisherRepository.deleteById(id);
    }


    public List<PublisherDTO> findAll() {
        return publisherRepository.findAll().stream().map(publisherMapper::toDTO).collect(Collectors.toList());
    }

    private void verifyIfExists(String name, String code) {
        Optional<Publisher> duplicatedPublisher = publisherRepository.findByNameOrCode(name, code);
        if (duplicatedPublisher.isPresent()) {
            throw new PublisherAlreadyExistsException(name, code);
        }
    }

    private void verifyIfExists(Long id) {
        publisherRepository.findById(id).orElseThrow(() -> new PublisherNotFoundException(id));
    }
}