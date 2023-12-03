package com.example.test.test.DTOs.OrderDtos;

import com.example.test.test.entity.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {

    private Integer orderId;
    private List<PlacedOrderDetailsDto> orderDtos;

}
