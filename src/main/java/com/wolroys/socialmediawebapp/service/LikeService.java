package com.wolroys.socialmediawebapp.service;

import com.wolroys.socialmediawebapp.entity.Like;
import com.wolroys.socialmediawebapp.entity.Post;
import com.wolroys.socialmediawebapp.entity.User;
import com.wolroys.socialmediawebapp.repository.LikeRepository;
import com.wolroys.socialmediawebapp.repository.PostRepository;
import com.wolroys.socialmediawebapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void like(int postId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User user= userRepository.findByUsername(userDetails.getUsername()).get();
        Post post = postRepository.findById(postId).get();
        long userId = user.getId();


        if (likeRepository.existsByUserIdAndPostId(userId, postId)){
            likeRepository.deleteByUserId(userId);
        }
        else {
            likeRepository.save(userId, postId);
        }
    }

    public long getCount(int postId){
        return likeRepository.countByPostId(postId);
    }

    public boolean isLiked(long userId, long postId){
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }
}
