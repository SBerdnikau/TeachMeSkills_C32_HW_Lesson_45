package com.tms.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "RegistrationDTO")
@Getter
@Setter
public class RegistrationRequestDto {
    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    @Pattern(
            regexp = "^[a-zA-Z-]+$",
            message = "First name must contain only letters and hyphens"
    )
    private String firstname;

    @NotNull(message = "Second name cannot be null")
    @NotBlank(message = "Second name cannot be blank")
    @Size(min = 2, max = 20, message = "Second name must be between 2 and 20 characters")
    @Pattern(
            regexp = "^[a-zA-Z-]+$",
            message = "Second name must contain only letters and hyphens"
    )
    private String secondName;

    @NotNull(message = "Age cannot be null")
    @Min(value = 12, message = "Age must be at least 12")
    @Max(value = 120, message = "Age must be at most 120")
    private Integer age;

    @NotNull
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull
    @NotBlank(message = "Sex cannot be blank")
    private String sex;

    @NotNull
    @NotBlank
    @Pattern(
            regexp = "^\\+?[0-9]{7,}$",
            message = "Telephone number must be at least 7 digits and may start with a '+'"
    )
    private String telephoneNumber;

    @NotNull(message = "Login cannot be null")
    @NotBlank(message = "Login cannot be blank")
    @Pattern(
            regexp = "^[a-zA-Z0-9]+$",
            message = "Login must contain only letters and digits"
    )
    private String login;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;
}
