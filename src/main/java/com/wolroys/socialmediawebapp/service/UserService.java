package com.wolroys.socialmediawebapp.service;

import com.wolroys.socialmediawebapp.dto.UserCreateEditDto;
import com.wolroys.socialmediawebapp.dto.UserReadDto;
import com.wolroys.socialmediawebapp.entity.Role;
import com.wolroys.socialmediawebapp.entity.User;
import com.wolroys.socialmediawebapp.mapper.UserMapper;
import com.wolroys.socialmediawebapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public Page<UserReadDto> findAll(Pageable pageable){
        return userRepository.findAll(pageable)
                .map(mapper::toDto);
    }

    public Optional<UserReadDto> findById(int id){
        return userRepository.findById(id)
                .map(mapper::toDto);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto user){
        return Optional.of(user)
                .map(mapper::toEntity)
                .map(u -> {
                    u.setPassword(passwordEncoder.encode(u.getPassword()));
                    u.setRole(Role.USER);
                    return u;
                })
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

    public boolean isExist(String username){
        return userRepository.findByUsername(username)
                .isPresent();
    }

    @Transactional
    public void subscribe(User currentUser, User user){
        if (!user.getFollows().contains(currentUser)){
            user.getFollowers().add(currentUser);

            userRepository.save(user);
        }
    }

    @Transactional
    public void unsubscribe(User currentUser, User user){
        if (user.getFollows().contains(currentUser)){
            user.getFollowers().remove(currentUser);

            userRepository.save(user);
        }
    }

    public boolean hasSubscribe(User currentUser, User user){
        return currentUser.getFollows().contains(user);
    }

    public long countSubscribes(User user){
        return user.getFollowers().size();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(entity -> new org.springframework.security.core.userdetails.User(
                        entity.getUsername(),
                        entity.getPassword().isBlank()
                        ? "123"
                        : entity.getPassword(),
                        Collections.singleton(entity.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}
