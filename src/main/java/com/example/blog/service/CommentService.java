package com.example.blog.service;

import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public Comment createComment(long id, Comment comment){
        Optional<Post> postOptional = postRepository.findById(id);
        if(postOptional.isPresent()){

           return commentRepository.save(comment);

        }else
            throw new IllegalArgumentException("post with this id does not exists");
    }
    
    public void deleteComment (long id){
        commentRepository.deleteById(id);
    }
    
    public Comment updateComment(long commentarId,String newText){
        Optional<Comment> optionalComment = commentRepository.findById(commentarId);
        if (optionalComment.isPresent()){
            Comment commentary = optionalComment.get();
            commentary.setCommentarText(newText);
            return commentary;
        }else throw new IllegalArgumentException("comment with this id does not exists");
    }
}
