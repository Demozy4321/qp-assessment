package com.example.test.test.controllers;

import com.example.test.test.DTOs.ResponseDto;
import com.example.test.test.entity.UserTable;
import com.example.test.test.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/addUser")
    private void addUser() {
         mainService.addUser();
    }

    @GetMapping("/findUser")
    private List<UserTable> findUser() {
        return mainService.findUser();
    }

    @GetMapping("/viewGroceryItems")
    ResponseEntity<ResponseDto> viewGroceryItems()
    {
        ResponseDto responseDto = new ResponseDto();

        try {

            responseDto = mainService.viewGroceryItems();

        }catch (Exception e) {
            responseDto.setStatus(false);
            responseDto.setMessage("Exception Occurred: " + e.getMessage());
            responseDto.setData(new ArrayList<>());
        }finally {
            return ResponseEntity.of(Optional.of(responseDto));
        }
    }

}
