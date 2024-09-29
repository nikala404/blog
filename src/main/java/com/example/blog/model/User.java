package com.example.blog.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @Column(name = "user_name", unique = true)
    @NotBlank
    @Max(value = 24, message = "Username must be shorter or equal 24 character")
    private String userName;

    @Column(name = "birth_date")
    @NotBlank
    @Pattern(regexp = "(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[0,1,2])\\/(19|20)\\d{2}", message = "date format must be like this: dd-mm-yyyy")
    private String brithDate;
}
