package com.example.test.test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Order_Details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailsId;

    @ManyToOne
    @JoinColumn(name = "Order_Id")
    private OrdersTable orderId;

    @ManyToOne
    @JoinColumn(name = "Item_Id")
    private GroceryItem groceryItem;

    @Column(name = "Grocery_Item_Quantity")
    private Integer groceryItemQuantity;
}
