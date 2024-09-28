package com.example.blog.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "coommentar_id")
    private long commentarId;

    @Column(name = "commentar_text")
    private String commentarText;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User writtenBy;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

}
