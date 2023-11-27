package com.example.test.test.service;

import com.example.test.test.entity.UserTable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MainService {
    void addUser();

    List<UserTable> findUser();
}
