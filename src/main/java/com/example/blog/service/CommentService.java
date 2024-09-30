package com.example.blog.service;

import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment createComment(long postId, String userName, Comment comment) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with ID " + postId + " not found"));

        User user = userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + userName + " not found"));

        comment.setPost(post);
        comment.setWrittenBy(user);
        return commentRepository.save(comment);
    }

    public void deleteComment(String userName, long id) {
        boolean userExists = userRepository.existsByUserName(userName);
        if (!userExists) {
            throw new ResourceNotFoundException("User does not exist");
        }

        boolean commentExists = commentRepository.existsById(id);
        if (!commentExists) {
            throw new ResourceNotFoundException("Comment with ID " + id + " does not exist");
        }

        commentRepository.deleteById(id);
    }

    public Comment updateComment(String userName, long commentId, String newText) {
        userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with ID " + commentId + " does not exist"));

        comment.setCommentarText(newText);
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(long postId) {
        boolean postExists = postRepository.existsById(postId);
        if (!postExists) {
            throw new ResourceNotFoundException("Post with ID " + postId + " does not exist");
        }

        return commentRepository.findByPostId(postId);
    }
}
