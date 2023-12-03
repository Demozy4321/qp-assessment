package com.example.test.test.repositories;

import com.example.test.test.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Integer> {


    @Query(value = "SELECT r FROM Roles r where r.roleName in :defaultRoles")
    Set<Roles> findByNames(@Param("defaultRoles") Set<String> defaultRoles);
}
