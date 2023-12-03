package com.example.test.test.DTOs.GroceryDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroceryEntryDto {

    private String name;
    private Integer quantity;
    private Double price;
}
