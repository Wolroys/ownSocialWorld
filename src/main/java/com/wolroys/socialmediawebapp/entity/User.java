package com.wolroys.socialmediawebapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AuditingEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(name = "user_subscription",
    joinColumns = @JoinColumn(name = "follower_id"),
    inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<User> followers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_subscription",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<User> follows = new HashSet<>();

    public User(int id, String username, String email, String password, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
