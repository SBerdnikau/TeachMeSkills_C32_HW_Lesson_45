package com.tms.model;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class User {
    private Long id;
    @NotBlank(message = "Name can't be empty")
    private String firstname;
    @NotBlank(message = "Lastname can't be empty")
    private String secondName;
    private Integer age;
    private String email;
    private String sex;
    private String telephoneNumber;
    private Timestamp created;
    private Timestamp updated;
    private Boolean isDeleted;
    private Security securityInfo;
}
