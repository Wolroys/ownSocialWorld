package com.wolroys.socialmediawebapp.entity;

import com.wolroys.socialmediawebapp.entity.util.LikeId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "likes")
public class Like {

    @EmbeddedId
    private LikeId likeId;
}
