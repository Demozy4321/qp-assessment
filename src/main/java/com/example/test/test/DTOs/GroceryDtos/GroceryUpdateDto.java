package com.example.test.test.DTOs.GroceryDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroceryUpdateDto {

    private Integer groceryItemId;
    private String name;
    private Integer quantity;
    private Double price;
}
