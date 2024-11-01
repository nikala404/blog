package com.example.blog.controller;

import com.example.blog.DTO.CommentUpdateRequest;
import com.example.blog.model.Comment;
import com.example.blog.service.CommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
            Principal principal,
            @RequestBody Comment comment) {
        Comment savedComment = commentService.createComment(postId, principal, comment);
        return ResponseEntity.ok(savedComment);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<String> updateComment(
            Principal principal,
            @PathVariable long commentId,
            @RequestBody CommentUpdateRequest newText) {
        String updatedComment = commentService.updateComment(principal, commentId, newText.getNewText());
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            Principal principal,
            @PathVariable long commentId) {
        commentService.deleteComment(principal, commentId);
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
