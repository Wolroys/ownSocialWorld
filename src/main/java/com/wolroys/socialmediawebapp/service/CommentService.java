package com.wolroys.socialmediawebapp.service;

import com.wolroys.socialmediawebapp.entity.Comment;
import com.wolroys.socialmediawebapp.entity.Post;
import com.wolroys.socialmediawebapp.mapper.PostMapper;
import com.wolroys.socialmediawebapp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final PostMapper mapper;

    public List<Comment> findAllByPostId(int id){
        return commentRepository.findAllByPostId(id);
    }

    @Transactional
    public void create(int postId, Comment comment){
        Post post = mapper.toEntity(
                postService.findById(postId).get());
        comment.setPost(post);
        commentRepository.save(comment);
    }


}
