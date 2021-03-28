package com.erik.bookstoremanager.users.builder;

import com.erik.bookstoremanager.users.dto.UserDTO;
import com.erik.bookstoremanager.users.enums.Gender;
import com.erik.bookstoremanager.users.enums.Role;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class UserDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Erik Moreira";

    @Builder.Default
    private int age = 25;

    @Builder.Default
    private Gender gender = Gender.MALE;

    @Builder.Default
    private String email = "e@test.com.br";

    @Builder.Default
    private String username = "eomoreira";

    @Builder.Default
    private String password = "123456";

    @Builder.Default
    private LocalDate birthDate = LocalDate.of(1996, 1, 1);

    @Builder.Default
    private Role role = Role.USER;

    public UserDTO buildUserDTO(){
        return new UserDTO(id, name, age, gender, email, username, password, birthDate, role);
    }

}
