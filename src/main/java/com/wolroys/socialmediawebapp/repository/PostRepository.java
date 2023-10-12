package com.wolroys.socialmediawebapp.repository;

import com.wolroys.socialmediawebapp.dto.PostDto;
import com.wolroys.socialmediawebapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findAllByUserId(Pageable pageable, int userId);
}
