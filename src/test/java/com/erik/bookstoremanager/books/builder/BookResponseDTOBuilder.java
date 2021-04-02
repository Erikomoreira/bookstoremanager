package com.erik.bookstoremanager.books.builder;

import com.erik.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.erik.bookstoremanager.author.dto.AuthorDTO;
import com.erik.bookstoremanager.books.dto.BookResponseDTO;
import com.erik.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.erik.bookstoremanager.publishers.dto.PublisherDTO;
import com.erik.bookstoremanager.users.builder.UserDTOBuilder;
import com.erik.bookstoremanager.users.dto.UserDTO;
import lombok.Builder;

@Builder
public class BookResponseDTOBuilder {

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
    private final AuthorDTO author = AuthorDTOBuilder.builder().build().buildAuthorDTO();

    @Builder.Default
    private final PublisherDTO publisher = PublisherDTOBuilder.builder().build().buildPublisherDTO();

    private final UserDTO userDTO = UserDTOBuilder.builder().build().buildUserDTO();

    public BookResponseDTO buildResponseBookDTO() {
        return new BookResponseDTO(id,
                name,
                isbn,
                pages,
                chapters,
                author,
                publisher);
    }

}
