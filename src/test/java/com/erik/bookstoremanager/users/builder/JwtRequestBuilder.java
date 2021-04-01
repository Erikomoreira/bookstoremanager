package com.erik.bookstoremanager.users.builder;

import com.erik.bookstoremanager.users.dto.JwtRequest;
import lombok.Builder;

@Builder
public class JwtRequestBuilder {

    @Builder.Default
    private final String username = "eomoreira";

    @Builder.Default
    private final String password = "123456";

    public JwtRequest buildJwtRequest() {
        return new JwtRequest(username, password);
    }

}
