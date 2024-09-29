package com.example.blog.service;

import com.example.blog.model.Post;
import com.example.blog.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post){
        return postRepository.save(post);
    }

    public void deletePost(long id){
        postRepository.deleteById(id);
    }

    public void updateText(long id, String newText){

        Optional<Post> postOptional = postRepository.findById(id);

        if(postOptional.isPresent()){
            postOptional.get().setText(newText);
        }else throw new IllegalArgumentException();
    }

    public Post getPostById(long id){
        Optional<Post> postOptional = postRepository.findById(id);

        if(postOptional.isPresent()){
            return postOptional.get();
        }else{
            throw new IllegalArgumentException("post with this id does not exists");
        }
    }

    public Page<Post> getAllPost(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    public Page<Post> getUsersAllPosts(String userName, Pageable pageable){

        return postRepository.findByUserName(userName, pageable);
    }
}
