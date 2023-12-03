package com.example.test.test.service;

import com.example.test.test.DTOs.GroceryDtos.GroceryEntryDto;
import com.example.test.test.DTOs.GroceryDtos.GroceryUpdateDto;
import com.example.test.test.DTOs.ResponseDto;
import org.springframework.stereotype.Component;

@Component
public interface AdminService {
    ResponseDto addItem(String userEmail, GroceryEntryDto groceryEntryDto);

//    ResponseDto viewGroceryItems(String userEmail);

    ResponseDto removeGroceryItem(String userEmail, Integer groceryItemId);

    ResponseDto udpateItem(String userEmail, GroceryUpdateDto groceryUpdateDto);
}
