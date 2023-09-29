package com.wolroys.socialmediawebapp.entity.util;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class LikeId implements Serializable {
    private Long userId;

    private Long postsId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeId likeId = (LikeId) o;
        return Objects.equals(userId, likeId.userId) && Objects.equals(postsId, likeId.postsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postsId);
    }
}
