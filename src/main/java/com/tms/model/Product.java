package com.tms.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Product {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Double price;
    private Timestamp created;
    private Timestamp updated;
}
