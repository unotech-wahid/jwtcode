package com.crackit.SpringSecurityJWT.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;
    private String errorMessage; // Define errorMessage field

    // Add the errorMessage(String) method
    public static class AuthenticationResponseBuilder {
        private String accessToken;
        private String errorMessage;

        public AuthenticationResponseBuilder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }
    }
}

