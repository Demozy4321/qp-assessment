package com.example.test.test.controllers;

import com.example.test.test.entity.UserTable;
import com.example.test.test.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
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

}
