package com.example.test.test.repositories;

import com.example.test.test.entity.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserTable, Integer> {
}
