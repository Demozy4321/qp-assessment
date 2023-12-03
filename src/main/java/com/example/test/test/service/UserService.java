package com.example.test.test.service;

import com.example.test.test.DTOs.OrderDtos.PlaceOrderDto;
import com.example.test.test.DTOs.ResponseDto;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    ResponseDto palceOrder(PlaceOrderDto placeOrderDto);

    ResponseDto showOrders(String userEmail);
}
