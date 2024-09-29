package com.example.blog.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;

    @Column(name = "userName")
    private String userName;
}
