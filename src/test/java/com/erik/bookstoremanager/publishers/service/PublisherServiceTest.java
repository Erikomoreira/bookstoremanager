package com.erik.bookstoremanager.publishers.service;

import com.erik.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.erik.bookstoremanager.publishers.dto.PublisherDTO;
import com.erik.bookstoremanager.publishers.entity.Publisher;
import com.erik.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.erik.bookstoremanager.publishers.exception.PublisherNotFoundException;
import com.erik.bookstoremanager.publishers.mappers.PublisherMapper;
import com.erik.bookstoremanager.publishers.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    private final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
    }

    @Test
    void whenNewPublisherIsInformedThenItShouldBeCreated() {
        PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expPublisherCreated = publisherMapper.toModel(expectedPublisherToCreateDTO);

        when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
                .thenReturn(Optional.empty());
        when(publisherRepository.save(expPublisherCreated)).thenReturn(expPublisherCreated);

        PublisherDTO createdPublisherDTO = publisherService.create(expectedPublisherToCreateDTO);

        assertThat(createdPublisherDTO, is(equalTo(expectedPublisherToCreateDTO)));
    }

    @Test
    void whenExistingPublisherIsInformedThenAnExceptionShouldBeThrown() {
        PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherDuplicated = publisherMapper.toModel(expectedPublisherToCreateDTO);

        when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
                .thenReturn(Optional.of(expectedPublisherDuplicated));

        assertThrows(PublisherAlreadyExistsException.class, () -> publisherService.create(expectedPublisherToCreateDTO));
    }

    @Test
    void whenValidIdIsGivenThenAPublisherShouldBeReturned() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherFound = publisherMapper.toModel(expectedPublisherFoundDTO);
        var expectedPublisherFoundId = expectedPublisherFoundDTO.getId();

        when(publisherRepository.findById(expectedPublisherFoundId)).thenReturn(Optional.of(expectedPublisherFound));

        PublisherDTO foundPublisherDTO = publisherService.findById(expectedPublisherFoundId);

        assertThat(foundPublisherDTO, is(equalTo(foundPublisherDTO)));
    }

    @Test
    void whenInvalidIdIsGivenThenAnExceptionThrown() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        var expectedPublisherFoundId = expectedPublisherFoundDTO.getId();

        when(publisherRepository.findById(expectedPublisherFoundId)).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> publisherService.findById(expectedPublisherFoundId));
    }

    @Test
    void whenListPublishersIsCalledThenItShouldBeReturned() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherFound = publisherMapper.toModel(expectedPublisherFoundDTO);

        when(publisherRepository.findAll()).thenReturn(Collections.singletonList(expectedPublisherFound));

        List<PublisherDTO> foundPublishersDTO = publisherService.findAll();

        assertThat(foundPublishersDTO.size(), is(1));
        assertThat(foundPublishersDTO.get(0), is(equalTo(expectedPublisherFoundDTO)));
    }

    @Test
    void whenListPublishersIsCalledAnEmptyListShouldBeReturned() {
        when(publisherRepository.findAll()).thenReturn(Collections.emptyList());

        List<PublisherDTO> foundPublishersDTO = publisherService.findAll();

        assertThat(foundPublishersDTO.size(), is(0));
    }
}
