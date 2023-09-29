package com.wolroys.socialmediawebapp.mapper;

import com.wolroys.socialmediawebapp.dto.PostDto;
import com.wolroys.socialmediawebapp.entity.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PostMapper {

    private final ModelMapper mapper;

    public Post toEntity(PostDto dto){
        return dto == null ? null : mapper.map(dto, Post.class);
    }

    public PostDto toDto(Post post){
        return post == null ? null : mapper.map(post, PostDto.class);
    }

    public <T> void map(T from, T to){
        mapper.map(from, to);
    }
}
