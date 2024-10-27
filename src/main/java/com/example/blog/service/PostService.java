package com.example.blog.service;

import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;





    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(Principal principal, Post post){

           Post newPost = new Post();
           newPost.setUserName(principal.getName());
           newPost.setText(post.getText());
           return postRepository.save(newPost);
    }

    public void deletePost(Principal principal, long postId){
        String userName = principal.getName();
        User user = userRepository.findUserByUserName(userName).get();
        Optional<Post> currentPostOptional = postRepository.findById(postId);

        if(currentPostOptional.isPresent()){
            Post currentPost = currentPostOptional.get();
            if(currentPost.getUserName() == userName || user.getRoles().contains("ADMIN")){
                postRepository.deleteById(postId);
            } else {
                throw new ResourceNotFoundException("Post with ID " + postId + " does not exist, or This Post Don't Belong To You");
            }
        } else {
            throw new ResourceNotFoundException("User does not exist");
        }
    }

    public void updateText(Principal principal, long postId, String newText) {
        String userName = principal.getName();
        User user = userRepository.findUserByUserName(userName).get();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with ID " + postId + " does not exist"));

        if (Objects.equals(post.getUserName(), user.getUserName()) || user.getRoles().contains("ADMIN")) {
            post.setText(newText);
            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("You cant update others post");
        }

    }

    public Post getPostById(String userName, long id){
        boolean userExists = userRepository.existsByUserName(userName);
        if(userExists){
            return postRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Post with ID " + id + " does not exist"));
        } else{
            throw new ResourceNotFoundException("User does not exist");
        }
    }

    public Page<Post> getAllPosts(String userName, Pageable pageable){
        boolean userExists = userRepository.existsByUserName(userName);
        if(userExists) {
            return postRepository.findAll(pageable);
        } else{
            throw new ResourceNotFoundException("User does not exist");
        }
    }

    public Page<Post> getUsersAllPosts(String userName, Pageable pageable){
        boolean userExists = userRepository.existsByUserName(userName);
        if(userExists){
            return postRepository.findByUserName(userName, pageable);
        } else{
            throw new ResourceNotFoundException("User does not exist");
        }
    }
}
