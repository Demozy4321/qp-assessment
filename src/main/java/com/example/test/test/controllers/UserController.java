package com.example.test.test.controllers;

import com.example.test.test.DTOs.GroceryDtos.GroceryEntryDto;
import com.example.test.test.DTOs.OrderDtos.PlaceOrderDto;
import com.example.test.test.DTOs.ResponseDto;
import com.example.test.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/placeOrder")
    ResponseEntity<ResponseDto> palceOrder(@RequestBody @Validated PlaceOrderDto placeOrderDto)
    {
        ResponseDto responseDto = new ResponseDto();

        try {

            responseDto = userService.palceOrder(placeOrderDto);

        }catch (Exception e) {
            e.printStackTrace();
            responseDto.setStatus(false);
            responseDto.setMessage("Exception Occurred: " + e.getMessage());
            responseDto.setData(new ArrayList<>());
        }finally {
            return ResponseEntity.of(Optional.of(responseDto));
        }
    }

    @GetMapping("/showOrders")
    ResponseEntity<ResponseDto> showOrders(@RequestParam String userEmail)
    {
        ResponseDto responseDto = new ResponseDto();

        try {

            responseDto = userService.showOrders(userEmail);

        }catch (Exception e) {
            e.printStackTrace();
            responseDto.setStatus(false);
            responseDto.setMessage("Exception Occurred: " + e.getMessage());
            responseDto.setData(new ArrayList<>());
        }finally {
            return ResponseEntity.of(Optional.of(responseDto));
        }
    }

}
