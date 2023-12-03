package com.example.test.test.repositories;

import com.example.test.test.entity.OrdersTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTableRepo extends JpaRepository<OrdersTable, Integer> {
}
