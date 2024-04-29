package org.asodev.securityvalidate.payload.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.asodev.securityvalidate.models.Role;

import java.util.Set;

public record SignupRequest(
        @NotBlank
        @Size(min = 3, max = 20)
        String firstName,
        @NotBlank
        @Size(min = 3, max = 50)
        String lastName,
        @NotBlank
        @Size(min=3,max=40)
        String username,
        @NotBlank
        @Size(min = 6,max = 40)
        String password,
        @NotBlank
        String email,

        Set<Role> role

) {


}
