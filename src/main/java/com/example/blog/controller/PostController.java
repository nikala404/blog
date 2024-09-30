package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<String> createPost(
            @RequestParam String userName,
            @RequestBody Post post){
        postService.createPost(userName, post);
        return ResponseEntity.ok("Post Created Successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(
            @RequestParam String userName,
            @PathVariable long id){
        postService.deletePost(userName, id);
        return ResponseEntity.ok("Post Deleted Successfully");
    }

    @PatchMapping("/update-post/{id}")
    public ResponseEntity<String> updatePost(
            @RequestParam String userName,
            @PathVariable long id,
            @RequestBody String newText) {
        postService.updateText(userName, id, newText);
        return ResponseEntity.ok("Post Updated Successfully");
    }

    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(
            @RequestParam String userName,
            Pageable pageable) {
        Page<Post> posts = postService.getAllPosts(userName, pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(
            @RequestParam String userName,
            @PathVariable long id){
        Post post = postService.getPostById(userName, id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<Page<Post>> getUsersAllPosts(
            @PathVariable String userName,
            Pageable pageable){
        Page<Post> usersPosts = postService.getUsersAllPosts(userName, pageable);
        return ResponseEntity.ok(usersPosts);
    }
}