package com.example.test.test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Grocery_Items")
public class GroceryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    @Column(name = "Item_Name")
    private String itemName;

    @Column(name = "Item_Price")
    private Double itemPrice;

    @Column(name = "Item_Quantity")
    private Integer itemQuantity;

}
