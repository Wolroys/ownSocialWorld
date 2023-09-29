package com.wolroys.socialmediawebapp.repository;

import com.wolroys.socialmediawebapp.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT CASE WHEN count(1) > 0 THEN true ELSE false END FROM " +
            "Like l WHERE l.likeId.userId = :userId AND l.likeId.postsId = :postId")
    boolean existsByUserIdAndPostId(@Param("userId") long userId, @Param("postId") long postId);

    @Query("SELECT COUNT(*) FROM Like l WHERE l.likeId.postsId = :postId")
    long countByPostId(@Param("postId") long postId);

    @Modifying
    @Query("delete from Like l where l.likeId.userId = :userId")
    void deleteByUserId(@Param("userId") long userId);

    @Modifying
    @Query(value = "insert into Likes as l (user_id, posts_id) values (:userId, :postId)", nativeQuery = true)
    void save(long userId, long postId);

}
