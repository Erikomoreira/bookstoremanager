package com.erik.bookstoremanager.books.builder;

import com.erik.bookstoremanager.books.dto.BookRequestDTO;
import com.erik.bookstoremanager.users.builder.UserDTOBuilder;
import com.erik.bookstoremanager.users.dto.UserDTO;
import lombok.Builder;

@Builder
public class BookRequestDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Spring Boot Pro";

    @Builder.Default
    private final String isbn = "978-3-16-148410-0";

    @Builder.Default
    private final Integer pages = 200;

    @Builder.Default
    private final Integer chapters = 10;

    @Builder.Default
    private final Long publisherId = 2L;

    @Builder.Default
    private final Long authorId = 3L;

    private final UserDTO userDTO = UserDTOBuilder.builder().build().buildUserDTO();

    public BookRequestDTO buildRequestBookDTO() {
        return new BookRequestDTO(id,
                name,
                isbn,
                pages,
                chapters,
                publisherId,
                authorId);
    }
}
