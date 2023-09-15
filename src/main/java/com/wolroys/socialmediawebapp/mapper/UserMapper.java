package com.wolroys.socialmediawebapp.mapper;

import com.wolroys.socialmediawebapp.dto.UserCreateEditDto;
import com.wolroys.socialmediawebapp.dto.UserDto;
import com.wolroys.socialmediawebapp.entity.User;
import com.wolroys.socialmediawebapp.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper mapper;

    public User toEntity(UserDto dto){
        return dto == null ? null : mapper.map(dto, User.class);
    }

    public UserReadDto toDto(User user){
        return user == null ? null : mapper.map(user, UserReadDto.class);
    }


    public <T> void map(T from, T to){
        mapper.map(from, to);
    }
}
