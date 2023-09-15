package com.wolroys.socialmediawebapp.service;

import com.wolroys.socialmediawebapp.dto.UserCreateEditDto;
import com.wolroys.socialmediawebapp.dto.UserReadDto;
import com.wolroys.socialmediawebapp.entity.User;
import com.wolroys.socialmediawebapp.mapper.UserMapper;
import com.wolroys.socialmediawebapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public List<UserReadDto> findAll(){
        return userRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public Optional<UserReadDto> findById(int id){
        return userRepository.findById(id)
                .map(mapper::toDto);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto user){
        return Optional.of(user)
                .map(mapper::toEntity)
                .map(userRepository::saveAndFlush)
                .map(mapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(int id, UserCreateEditDto user) {
        Optional<User> existUser = userRepository.findById(id);

        existUser.ifPresent(value -> mapper.map(user, value));

        return existUser
                .map(userRepository::saveAndFlush)
                .map(mapper::toDto);
    }

    @Transactional
    public boolean remove(int id){
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    userRepository.flush();
                    return true;
                }).orElse(false);
    }
}
