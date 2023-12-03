package com.example.test.test.DTOs.OrderDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderDto {

    private String userEmail;
    private List<GroceryItemOrderDto> orders;
}
