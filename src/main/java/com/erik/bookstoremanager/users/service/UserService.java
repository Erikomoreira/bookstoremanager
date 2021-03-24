package com.erik.bookstoremanager.users.service;

import com.erik.bookstoremanager.users.dto.MessageDTO;
import com.erik.bookstoremanager.users.dto.UserDTO;
import com.erik.bookstoremanager.users.entity.User;
import com.erik.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.erik.bookstoremanager.users.mapper.UserMapper;
import com.erik.bookstoremanager.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final static UserMapper userMapper = UserMapper.INSTANCE;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userCreateDTO) {
        verifyIfExists(userCreateDTO.getEmail(), userCreateDTO.getUsername());
        User userToCreate = userMapper.toModel(userCreateDTO);
        User createdUser = userRepository.save(userToCreate);
        return creationMessage(createdUser);
    }

    private MessageDTO creationMessage(User createdUser) {
        String createdUserName = createdUser.getUsername();
        Long createdId = createdUser.getId();
        String createdUserMessage = String.format("User %s with ID %s successfully created", createdUserName, createdId);
        return MessageDTO.builder()
                .message(createdUserMessage)
                .build();
    }

    private void verifyIfExists(String email, String username) {
        Optional<User> foundUser = userRepository.findByEmailOrUsername(email, username);
        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException(email, username);
        }
    }
}
