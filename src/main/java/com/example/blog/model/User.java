package com.example.blog.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "user")
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
    @Min(value = 4, message = "Size must be shorter than 4 character")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Min(value = 6, message = "Size must be longer or equal 6 character")
    private String lastName;

    @Column(name = "user_name")
    @NotBlank
    @Max(value = 24, message = "Username must be shorter or equal 24 character")
    private String userName;
}
