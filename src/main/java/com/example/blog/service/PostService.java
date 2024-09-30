package com.example.blog.service;

import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(String userName, Post post){
        boolean userExists = userRepository.existsByUserName(userName);
        if(userExists){
            return postRepository.save(post);
        } else {
            throw new ResourceNotFoundException("User does not exist");
        }
    }

    public void deletePost(String userName, long id){
        boolean userExists = userRepository.existsByUserName(userName);
        if(userExists){
            boolean postExists = postRepository.existsById(id);
            if(postExists){
                postRepository.deleteById(id);
            } else {
                throw new ResourceNotFoundException("Post with ID " + id + " does not exist");
            }
        } else {
            throw new ResourceNotFoundException("User does not exist");
        }
    }

    public void updateText(String userName, long id, String newText){
        boolean userExists = userRepository.existsByUserName(userName);
        if(userExists){
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Post with ID " + id + " does not exist"));
            post.setText(newText);
            postRepository.save(post);
        } else{
            throw new ResourceNotFoundException("User does not exist");
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
