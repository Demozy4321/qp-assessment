package com.example.test.test.serviceImpl;

import com.example.test.test.entity.Roles;
import com.example.test.test.entity.UserTable;
import com.example.test.test.repositories.RolesRepo;
import com.example.test.test.repositories.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        List<String> defaultRolesStr = new ArrayList<>();
        defaultRolesStr.add("Admin");
        defaultRolesStr.add("User");

        Set<Roles> defaultRoles = new HashSet<>();
        for (String role : defaultRolesStr)
        {
            Roles roles = new Roles();
            roles.setRoleName(role);

            defaultRoles.add(roles);
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Roles> rolesCriteriaQuery = criteriaBuilder.createQuery(Roles.class);
        Root<Roles> rolesRoot = rolesCriteriaQuery.from(Roles.class);

        List<Predicate> rolesPredicate = new ArrayList<>();
        rolesPredicate.add(rolesRoot.get("roleName").in(defaultRolesStr));
        Query query = entityManager.createQuery(rolesCriteriaQuery);
        List<Roles> rolesList = query.getResultList();


        if (rolesList == null || rolesList.isEmpty())
        {
            rolesRepo.saveAll(defaultRoles);
        }else{
            defaultRoles = new HashSet<>();
            for (Roles roles: rolesList)
            {
                defaultRoles.add(roles);
            }
        }

        UserTable defUser = entityManager.find(UserTable.class, "demozy@gmail.com");

        if (defUser == null)
        {
            UserTable defaultUser = new UserTable();
            defaultUser.setEmail("demozy@gmail.com");
            defaultUser.setRoles(defaultRoles);
            defaultUser.setName("Demozy");
            defaultUser.setUsername("Demozy_4321");

            userRepo.save(defaultUser);
        }

    }
}
