package com.example.test.test.DTOs.OrderDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlacedOrderDetailsDto {

    private Integer orderDetailsId;
    private Integer orderId;
    private Integer groceryItemId;
    private String groceryItemName;
    private Integer groceryItemQuantity;

}
