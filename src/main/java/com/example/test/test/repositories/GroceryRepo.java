package com.example.test.test.repositories;

import com.example.test.test.entity.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryRepo extends JpaRepository<GroceryItem, Integer> {


    @Query(value = "Select gt FROM GroceryItem gt where gt.itemName = :groceryItemName")
    GroceryItem findByGroceryName(@Param("groceryItemName") String name);
}
