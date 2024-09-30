package com.example.blog.controller;

import com.example.blog.model.Comment;
import com.example.blog.service.CommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<Comment> createComment(
            @PathVariable long postId,
            @RequestParam String userName,
            @RequestBody Comment comment) {
        Comment savedComment = commentService.createComment(postId, userName, comment);
        return ResponseEntity.ok(savedComment);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @RequestParam String userName,
            @PathVariable long commentId,
            @RequestBody String newText) {
        Comment updatedComment = commentService.updateComment(userName, commentId, newText);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @RequestParam String userName,
            @PathVariable long commentId) {
        commentService.deleteComment(userName, commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(
            @PathVariable long postId,
            Pageable pageable) {
        List<Comment> comments = commentService.getComments(postId);
        return ResponseEntity.ok(comments);
    }
}
