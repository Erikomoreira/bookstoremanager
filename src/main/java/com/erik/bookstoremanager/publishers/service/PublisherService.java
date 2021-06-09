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

    private static final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public PublisherDTO create(PublisherDTO publisherDTO) {
        verifyAndGetIfExists(publisherDTO.getName(), publisherDTO.getCode());
        var publisherToCreate = publisherMapper.toModel(publisherDTO);
        var createdPublisher = publisherRepository.save(publisherToCreate);
        return publisherMapper.toDTO(createdPublisher);
    }

    public PublisherDTO findById(Long id) {
        return publisherRepository.findById(id)
                .map(publisherMapper::toDTO)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    public void delete(Long id) {
        verifyAndGetIfExists(id);
        publisherRepository.deleteById(id);
    }


    public List<PublisherDTO> findAll() {
        return publisherRepository.findAll().stream().map(publisherMapper::toDTO).collect(Collectors.toList());
    }

    private void verifyAndGetIfExists(String name, String code) {
        Optional<Publisher> duplicatedPublisher = publisherRepository.findByNameOrCode(name, code);
        if (duplicatedPublisher.isPresent()) {
            throw new PublisherAlreadyExistsException(name, code);
        }
    }

    public Publisher verifyAndGetIfExists(Long id) {
       return publisherRepository.findById(id).orElseThrow(() -> new PublisherNotFoundException(id));
    }
}
