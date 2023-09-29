package com.wolroys.socialmediawebapp.service;

import com.wolroys.socialmediawebapp.dto.PostDto;
import com.wolroys.socialmediawebapp.entity.Post;
import com.wolroys.socialmediawebapp.mapper.PostMapper;
import com.wolroys.socialmediawebapp.repository.PostRepository;
import com.wolroys.socialmediawebapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper mapper;

    public List<PostDto> findAll(){
        return postRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public Optional<PostDto> findById(int id){
        return postRepository.findById(id)
                .map(mapper::toDto);
    }

    @Transactional
    public PostDto create(PostDto dto){
        Post post = Optional.of(dto)
                .map(mapper::toEntity)
                .orElse(null);

        post.setUser(userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()).get());

                return Optional.of(post)
                .map(postRepository::save)
                .map(mapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<PostDto> edit(int id, PostDto postDto){
        Optional<Post> existPost = postRepository.findById(id);

        existPost.ifPresent(post -> mapper.map(postDto, post));

        return existPost
                .map(postRepository::saveAndFlush)
                .map(mapper::toDto);
    }

    @Transactional
    public boolean delete(int id){
        return postRepository.findById(id)
                .map(entity -> {
                    postRepository.delete(entity);
                    postRepository.flush();
                    return true;})
                .orElse(false);
    }
}
