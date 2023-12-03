package com.example.test.test.controllers;

import com.example.test.test.DTOs.GroceryDtos.GroceryEntryDto;
import com.example.test.test.DTOs.GroceryDtos.GroceryUpdateDto;
import com.example.test.test.DTOs.ResponseDto;
import com.example.test.test.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/addGroceryItem")
    ResponseEntity<ResponseDto> addGroceryItem(@RequestParam String userEmail,@RequestBody @Validated GroceryEntryDto groceryEntryDto)
    {
        ResponseDto responseDto = new ResponseDto();

        try {

            responseDto = adminService.addItem(userEmail,groceryEntryDto);

        }catch (Exception e) {
            e.printStackTrace();
            responseDto.setStatus(false);
            responseDto.setMessage("Exception Occurred: " + e.getMessage());
            responseDto.setData(new ArrayList<>());
        }finally {
            return ResponseEntity.of(Optional.of(responseDto));
        }
    }

//    @GetMapping("/viewGroceryItems")
//    ResponseEntity<ResponseDto> viewGroceryItems(@RequestParam String userEmail)
//    {
//        ResponseDto responseDto = new ResponseDto();
//
//        try {
//
//            responseDto = adminService.viewGroceryItems(userEmail);
//
//        }catch (Exception e) {
//            responseDto.setStatus(false);
//            responseDto.setMessage("Exception Occurred: " + e.getMessage());
//            responseDto.setData(new ArrayList<>());
//        }finally {
//            return ResponseEntity.of(Optional.of(responseDto));
//        }
//    }

    @PostMapping("/removeItem")
    ResponseEntity<ResponseDto> removeGroceryItem(@RequestParam String userEmail, @RequestParam Integer groceryItemId)
    {
        ResponseDto responseDto = new ResponseDto();

        try {

            responseDto = adminService.removeGroceryItem(userEmail, groceryItemId);

        }catch (Exception e) {
            responseDto.setStatus(false);
            responseDto.setMessage("Exception Occurred: " + e.getMessage());
            responseDto.setData(new ArrayList<>());
        }finally {
            return ResponseEntity.of(Optional.of(responseDto));
        }
    }

    @PostMapping("/updateItem")
    ResponseEntity<ResponseDto> udpateItem(@RequestParam String userEmail, @RequestBody @Validated GroceryUpdateDto groceryUpdateDto)
    {
        ResponseDto responseDto = new ResponseDto();

        try {

            responseDto = adminService.udpateItem(userEmail, groceryUpdateDto);

        }catch (Exception e) {
            responseDto.setStatus(false);
            responseDto.setMessage("Exception Occurred: " + e.getMessage());
            responseDto.setData(new ArrayList<>());
        }finally {
            return ResponseEntity.of(Optional.of(responseDto));
        }
    }

}
