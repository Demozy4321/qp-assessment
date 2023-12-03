package com.example.test.test.serviceImpl;

import com.example.test.test.DTOs.ResponseDto;
import com.example.test.test.entity.GroceryItem;
import com.example.test.test.entity.UserTable;
import com.example.test.test.repositories.GroceryRepo;
import com.example.test.test.repositories.UserRepo;
import com.example.test.test.service.MainService;
import jakarta.persistence.EntityManager;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GroceryRepo groceryRepo;


    @Override
    public void addUser() {
        UserTable userTable = new UserTable();
        userTable.setName("Demozy");
        userTable.setEmail("demozy@gmail.com");

        userRepo.save(userTable);

    }

    @Override
    public List<UserTable> findUser() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserTable> userTableCriteriaQuery = criteriaBuilder.createQuery(UserTable.class);
        Root<UserTable> userTableRoot = userTableCriteriaQuery.from(UserTable.class);
        List<Predicate> userPredicates = new ArrayList<>();

        userPredicates.add(criteriaBuilder.equal(userTableRoot.get("name"),"demozy"));
        userTableCriteriaQuery.select(userTableRoot).where(userPredicates.toArray(new Predicate[]{}));
        List<UserTable> userTables = entityManager.createQuery(userTableCriteriaQuery).getResultList();

        return userTables;

    }

    @Override
    public ResponseDto viewGroceryItems() {
        ResponseDto responseDto = new ResponseDto();

        try {

            List<GroceryItem> groceryItems = groceryRepo.findAll();

            responseDto.setData(groceryItems);
            responseDto.setStatus(true);
            responseDto.setMessage("Grocery Item Fetched Successfully");

        }catch (Exception e) {
            e.printStackTrace();
            responseDto.setData(new ArrayList<>());
            responseDto.setStatus(false);
            responseDto.setMessage("Exception Occured in Fetching Items");
        }finally {
            return responseDto;
        }
    }
}
