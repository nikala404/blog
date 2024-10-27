package com.example.blog.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "first_name")
    @NotBlank
    @Size(min = 2, message = "First name must be at least 4 characters long")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Size(min = 3, message = "Last name must be at least 3 characters long")
    private String lastName;

    @Column(name = "user_name", unique = true)
    @NotBlank
    @Size(max = 56, message = "Username must be 56 characters or less")
    private String userName;

    @NotBlank
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;

}
