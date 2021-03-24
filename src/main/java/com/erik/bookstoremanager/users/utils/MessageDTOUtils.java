package com.erik.bookstoremanager.users.utils;

import com.erik.bookstoremanager.users.dto.MessageDTO;
import com.erik.bookstoremanager.users.entity.User;

public class MessageDTOUtils {

    public static MessageDTO creationMessage(User createdUser){
        return returnMessage(createdUser, "created");
    }

    public static MessageDTO updatedMessage(User updateduser){
        return returnMessage(updateduser, "updated");
    }

    private static MessageDTO returnMessage(User user, String action) {
        String updatedUserName = user.getUsername();
        Long updatedId = user.getId();
        String updatedUserMessage = String.format("User %s with ID %s successfully %s", updatedUserName, updatedId, action);
        return MessageDTO.builder()
                .message(updatedUserMessage)
                .build();
    }
}
