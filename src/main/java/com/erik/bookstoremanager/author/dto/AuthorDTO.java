package com.erik.bookstoremanager.author.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {


    private Long id;

    @NotBlank
    @Size(max = 25)
    private String name;

    @NotNull
    @Max(120)
    private int age;
}
