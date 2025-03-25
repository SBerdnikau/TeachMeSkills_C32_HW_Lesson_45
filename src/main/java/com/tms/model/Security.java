package com.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Security {
    private Long id;
    private String login;
    private String password;
    private Role role;
    private Timestamp created;
    private Timestamp updated;
    private Long userId;
}
