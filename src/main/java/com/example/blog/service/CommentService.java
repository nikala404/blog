package com.example.blog.service;

import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

    public Comment createComment(long postId, Principal principal, Comment comment) {
        String userName = principal.getName();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with ID " + postId + " not found"));

        User user = userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + userName + " not found"));

        comment.setPost(post);
        comment.setWrittenBy(user);
        return commentRepository.save(comment);
    }

    public void deleteComment(Principal principal, long commentId) {
        String userName = principal.getName();

        User user = userRepository.findByUserName(userName);

        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (commentOptional.isPresent()) {
            Comment currentComment = commentOptional.get();
            if (currentComment.getWrittenBy().getUserName().equals(user.getUserName()) || user.getRoles().contains("ADMIN")) {
                commentRepository.deleteById(commentId);
            } else {
                throw new IllegalArgumentException("This Comment Is Not Written By you...");
            }


        } else {
            throw new ResourceNotFoundException("Comment with ID " + commentId + " does not exist");
        }

    }

    public String updateComment(Principal principal, long commentId, String newText) {
        String userName = principal.getName();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with ID " + commentId + " does not exist"));

        if (comment.getWrittenBy().getUserName().equals(userName)) {
            comment.setCommentarText(newText);
            commentRepository.save(comment);
            return "Comment Updated!";
        } else {
            return "This Comment Don't belong to you";
        }

    }

    public List<Comment> getComments(long postId) {
        boolean postExists = postRepository.existsById(postId);
        if (!postExists) {
            throw new ResourceNotFoundException("Post with ID " + postId + " does not exist");
        }

        return commentRepository.findByPostId(postId);
    }
}
