package com.example.test.test.serviceImpl;

import com.example.test.test.DTOs.OrderDtos.GroceryItemOrderDto;
import com.example.test.test.DTOs.OrderDtos.OrderDetailsDto;
import com.example.test.test.DTOs.OrderDtos.PlaceOrderDto;
import com.example.test.test.DTOs.OrderDtos.PlacedOrderDetailsDto;
import com.example.test.test.DTOs.ResponseDto;
import com.example.test.test.entity.*;
import com.example.test.test.repositories.OrderTableRepo;
import com.example.test.test.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(rollbackOn = Exception.class)
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrderTableRepo orderTableRepo;

    @Override
    public ResponseDto palceOrder(PlaceOrderDto placeOrderDto) {
        ResponseDto responseDto = new ResponseDto();

        try {
            UserTable user = entityManager.find(UserTable.class, placeOrderDto.getUserEmail());
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
            if (!checkUser(placeOrderDto.getUserEmail()))
            {
                responseDto.setData(new ArrayList<>());
                responseDto.setStatus(false);
                responseDto.setMessage("User Does not have user role");
                return responseDto;
            }

            List<GroceryItemOrderDto> orderDtos = placeOrderDto.getOrders();
            OrdersTable ordersTable = new OrdersTable();
            if (!placeOrderDto.getOrders().isEmpty())
            {
                ordersTable.setUser(user);

                orderTableRepo.save(ordersTable);
            }else{
                responseDto.setData(new ArrayList<>());
                responseDto.setStatus(false);
                responseDto.setMessage("Order cannot be placed for empty cart");
                return responseDto;
            }

            orderDetailsDto.setOrderId(ordersTable.getOrderId());
            List<PlacedOrderDetailsDto> orders = new ArrayList<>();

            for (GroceryItemOrderDto groceryItemOrderDto : orderDtos)
            {
                GroceryItem groceryItem = entityManager.find(GroceryItem.class, groceryItemOrderDto.getGroceryItemId());

                if (groceryItem != null && groceryItem.getItemQuantity() >= groceryItemOrderDto.getQuantity())
                {
                    PlacedOrderDetailsDto placedOrderDetailsDto = new PlacedOrderDetailsDto();
                    OrderDetails orderDetails = new OrderDetails();
                    orderDetails.setOrderId(ordersTable);
                    orderDetails.setGroceryItem(groceryItem);
                    orderDetails.setGroceryItemQuantity(groceryItemOrderDto.getQuantity());

                    entityManager.persist(orderDetails);

                    groceryItem.setItemQuantity(groceryItem.getItemQuantity() - groceryItemOrderDto.getQuantity());

                    entityManager.merge(groceryItem);


                    placedOrderDetailsDto.setOrderId(ordersTable.getOrderId());
                    placedOrderDetailsDto.setOrderDetailsId(orderDetails.getOrderDetailsId());
                    placedOrderDetailsDto.setGroceryItemId(groceryItem.getItemId());
                    placedOrderDetailsDto.setGroceryItemQuantity(groceryItemOrderDto.getQuantity());
                    placedOrderDetailsDto.setGroceryItemName(groceryItem.getItemName());
                    orders.add(placedOrderDetailsDto);

                }
            }

            orderDetailsDto.setOrderDtos(orders);

            responseDto.setData(orderDetailsDto);
            responseDto.setStatus(true);
            responseDto.setMessage("Order placed successfully");
            return responseDto;

        }catch (Exception e) {
            e.printStackTrace();
            responseDto.setData(new ArrayList<>());
            responseDto.setStatus(false);
            responseDto.setMessage("Exception Occured in placing order");
        }finally {
            return  responseDto;
        }
    }

    @Override
    public ResponseDto showOrders(String userEmail) {
        ResponseDto responseDto = new ResponseDto();

        try {
            UserTable user = entityManager.find(UserTable.class, userEmail);
            if (user == null)
            {
                responseDto.setData(new ArrayList<>());
                responseDto.setStatus(false);
                responseDto.setMessage("User Not Found");
                return responseDto;
            }

            if (!checkUser(userEmail))
            {
                responseDto.setData(new ArrayList<>());
                responseDto.setStatus(false);
                responseDto.setMessage("User Does not have user role");
                return responseDto;
            }

            //get all the order ids
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<OrdersTable> ordersTableCriteriaQuery = criteriaBuilder.createQuery(OrdersTable.class);
            Root<OrdersTable> ordersTableRoot = ordersTableCriteriaQuery.from(OrdersTable.class);
            List<Predicate> orderTablePredicates = new ArrayList<>();

            orderTablePredicates.add(criteriaBuilder.equal(ordersTableRoot.get("user"),user));
            ordersTableCriteriaQuery.select(ordersTableRoot).where(orderTablePredicates.toArray(new Predicate[]{}));

            Query ordersTableQuery = entityManager.createQuery(ordersTableCriteriaQuery);
            List<OrdersTable> ordersTables = ordersTableQuery.getResultList();

            if (ordersTables.isEmpty())
            {
                responseDto.setData(new ArrayList<>());
                responseDto.setStatus(true);
                responseDto.setMessage("No Orders");
                return responseDto;
            }

            //get all details of the order
            CriteriaQuery<OrderDetails> orderDetailsCriteriaQuery = criteriaBuilder.createQuery(OrderDetails.class);
            Root<OrderDetails> orderDetailsRoot = orderDetailsCriteriaQuery.from(OrderDetails.class);
            List<Predicate> orderDetailsPredicate = new ArrayList<>();

            orderDetailsPredicate.add(orderDetailsRoot.get("orderId").in(ordersTables));
            orderDetailsCriteriaQuery.select(orderDetailsRoot).where(orderDetailsPredicate.toArray(new Predicate[]{}));

            Query ordersTableTypedQuery = entityManager.createQuery(orderDetailsCriteriaQuery);
            List<OrderDetails> orderDetails = ordersTableTypedQuery.getResultList();

            List<OrderDetailsDto> placedOrderDetailsDtos = new ArrayList<>();
            for (OrdersTable ordersTable : ordersTables)
            {
                OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
                orderDetailsDto.setOrderId( ordersTable.getOrderId());
                List<OrderDetails> orderDetails1 = orderDetails.stream().filter(orderDetail -> orderDetail.getOrderId().getOrderId()==ordersTable.getOrderId()).collect(Collectors.toList());

                List<PlacedOrderDetailsDto> placedOrderDetailsDtoList = new ArrayList<>();
                for (OrderDetails orderDetails2 : orderDetails1)
                {
                    PlacedOrderDetailsDto placedOrderDetailsDto = new PlacedOrderDetailsDto();
                    placedOrderDetailsDto.setGroceryItemName(orderDetails2.getGroceryItem().getItemName());
                    placedOrderDetailsDto.setOrderId(orderDetails2.getOrderId().getOrderId());
                    placedOrderDetailsDto.setOrderDetailsId(orderDetails2.getOrderDetailsId());
                    placedOrderDetailsDto.setGroceryItemQuantity(orderDetails2.getGroceryItemQuantity());
                    placedOrderDetailsDto.setGroceryItemId(orderDetails2.getGroceryItem().getItemId());
                    placedOrderDetailsDtoList.add(placedOrderDetailsDto);
                }

                orderDetailsDto.setOrderDtos(placedOrderDetailsDtoList);
                placedOrderDetailsDtos.add(orderDetailsDto);

            }

            responseDto.setData(placedOrderDetailsDtos);
            responseDto.setStatus(true);
            responseDto.setMessage("Order fetched successfully");
            return responseDto;

        }catch (Exception e) {
            e.printStackTrace();
            responseDto.setData(new ArrayList<>());
            responseDto.setStatus(false);
            responseDto.setMessage("Exception Occured in fetching order details" );
        }finally {
            return responseDto;
        }
    }

    private Boolean checkUser(String userEmail) {
        UserTable user = entityManager.find(UserTable.class, userEmail);

        if (user == null )
        {
            return false;
        }

        Set<Roles> roles = user.getRoles();
        Boolean isUser = false;

        for (Roles role : roles)
        {
            if (role.getRoleName().equalsIgnoreCase("User"))
            {
                isUser = true;
            }
        }

        if (!isUser)
        {
            return false;
        }

        return true;
    }
}
