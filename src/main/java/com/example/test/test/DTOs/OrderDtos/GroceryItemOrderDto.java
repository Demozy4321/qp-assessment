package com.example.test.test.DTOs.OrderDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroceryItemOrderDto {

    private Integer groceryItemId;
    private Integer quantity;
}
