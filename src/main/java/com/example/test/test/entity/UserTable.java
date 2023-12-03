package com.example.test.test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User_Table")
public class UserTable {

    @Id
    @Column(name = "User_Email")
    private String email;

    @Column(name = "Name")
    private String name;

    @Column(name = "Username")
    private String username;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "User_Email"),
            inverseJoinColumns = @JoinColumn(name = "Role_Id")
    )
    private Set<Roles> roles = new HashSet<>();
}
