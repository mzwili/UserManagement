package com.mzwiliapp.userManage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, message = "Username must be at least 3 characters")
    private String username;

    @Column(nullable = false)
    @Size(min = 3, message = "Name must be at least 3 characters")
    private String name;

    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;
}
