package com.example.test.test.serviceImpl;

import com.example.test.test.DTOs.GroceryDtos.GroceryEntryDto;
import com.example.test.test.DTOs.GroceryDtos.GroceryUpdateDto;
import com.example.test.test.DTOs.ResponseDto;
import com.example.test.test.entity.GroceryItem;
import com.example.test.test.entity.Roles;
import com.example.test.test.entity.UserTable;
import com.example.test.test.repositories.GroceryRepo;
import com.example.test.test.service.AdminService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Transactional(rollbackOn = Exception.class)
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private GroceryRepo groceryRepo;

    @Override
    public ResponseDto addItem(String userEmail, GroceryEntryDto groceryEntryDto) {
        ResponseDto responseDto = new ResponseDto();

        try {

            UserTable user = entityManager.find(UserTable.class, userEmail);

            if (user == null )
            {
                responseDto.setData(new ArrayList<>());
                responseDto.setStatus(false);
                responseDto.setMessage("User Not Found");
                return responseDto;
            }

            Set<Roles> roles = user.getRoles();
            Boolean isAdmin = false;

            for (Roles role : roles)
            {
                if (role.getRoleName().equalsIgnoreCase("Admin"))
                {
                    isAdmin = true;
                }
            }

            if (!isAdmin)
            {
                responseDto.setData(new ArrayList<>());
                responseDto.setStatus(false);
                responseDto.setMessage("User Not An Admin");
                return responseDto;
            }

            GroceryItem groceryItem = groceryRepo.findByGroceryName(groceryEntryDto.getName());

            if (groceryItem == null)
            {
                groceryItem = new GroceryItem();
                groceryItem.setItemName(groceryEntryDto.getName());
                groceryItem.setItemQuantity(groceryEntryDto.getQuantity());
                groceryItem.setItemPrice(groceryEntryDto.getPrice());

                groceryRepo.save(groceryItem);
            }

            responseDto.setData(groceryItem);
            responseDto.setStatus(true);
            responseDto.setMessage("Grocery Item Added Successfully");

        }catch (Exception e) {
            e.printStackTrace();
            responseDto.setData(new ArrayList<>());
            responseDto.setStatus(false);
            responseDto.setMessage("Exception Occured in Adding Item");
        }finally {
            return  responseDto;
        }
    }

//    @Override
//    public ResponseDto viewGroceryItems(String userEmail) {
//        ResponseDto responseDto = new ResponseDto();
//
//        try {
//
//            if (!checkAdmin(userEmail))
//            {
//                responseDto.setData(new ArrayList<>());
//                responseDto.setStatus(false);
//                responseDto.setMessage("User Not An Admin");
//                return responseDto;
//            }
//
//            List<GroceryItem> groceryItems = groceryRepo.findAll();
//
//            responseDto.setData(groceryItems);
//            responseDto.setStatus(true);
//            responseDto.setMessage("Grocery Item Fetched Successfully");
//
//        }catch (Exception e) {
//            e.printStackTrace();
//            responseDto.setData(new ArrayList<>());
//            responseDto.setStatus(false);
//            responseDto.setMessage("Exception Occured in Fetching Items");
//        }finally {
//            return responseDto;
//        }
//    }

    @Override
    public ResponseDto removeGroceryItem(String userEmail, Integer groceryItemId) {
        ResponseDto responseDto = new ResponseDto();

        try {
            if (!checkAdmin(userEmail))
            {
                responseDto.setData(new ArrayList<>());
                responseDto.setStatus(false);
                responseDto.setMessage("User Not An Admin");
                return responseDto;
            }

            GroceryItem groceryItem = groceryRepo.findById(groceryItemId).orElse(null);

            if (groceryItem == null)
            {
                responseDto.setData(new ArrayList<>());
                responseDto.setStatus(false);
                responseDto.setMessage("Grocery Item Not Found");
                return responseDto;

            }

            groceryRepo.delete(groceryItem);

            responseDto.setData(groceryItem);
            responseDto.setStatus(true);
            responseDto.setMessage("Grocery Item Deleted Successfully");

        }catch (Exception e) {
            e.printStackTrace();
            responseDto.setData(new ArrayList<>());
            responseDto.setStatus(false);
            responseDto.setMessage("Exception Occured in Deleting Item");
        }finally {
            return responseDto;
        }
    }

    @Override
    public ResponseDto udpateItem(String userEmail, GroceryUpdateDto groceryUpdateDto) {
        ResponseDto responseDto = new ResponseDto();

        try {
            if (!checkAdmin(userEmail))
            {
                responseDto.setData(new ArrayList<>());
                responseDto.setStatus(false);
                responseDto.setMessage("User Not An Admin");
                return responseDto;
            }

            GroceryItem groceryItem = entityManager.find(GroceryItem.class, groceryUpdateDto.getGroceryItemId());

            if (groceryItem == null)
            {
                responseDto.setData(new ArrayList<>());
                responseDto.setStatus(false);
                responseDto.setMessage("Grocery Item Not Found");
                return responseDto;

            }


            groceryItem.setItemPrice(groceryUpdateDto.getPrice());
            groceryItem.setItemQuantity(groceryUpdateDto.getQuantity());
            groceryItem.setItemName(groceryUpdateDto.getName());

            entityManager.merge(groceryItem);

            responseDto.setData(groceryItem);
            responseDto.setStatus(true);
            responseDto.setMessage("Grocery Item Updated Successfully");

        }catch (Exception e) {
            e.printStackTrace();
            responseDto.setData(new ArrayList<>());
            responseDto.setStatus(false);
            responseDto.setMessage("Exception Occured in Updating Item");
        }finally {
            return responseDto;
        }
    }

    private Boolean checkAdmin(String userEmail) {

        UserTable user = entityManager.find(UserTable.class, userEmail);

        if (user == null )
        {
            return false;
        }

        Set<Roles> roles = user.getRoles();
        Boolean isAdmin = false;

        for (Roles role : roles)
        {
            if (role.getRoleName().equalsIgnoreCase("Admin"))
            {
                isAdmin = true;
            }
        }

        if (!isAdmin)
        {
            return false;
        }

        return true;
    }

}


