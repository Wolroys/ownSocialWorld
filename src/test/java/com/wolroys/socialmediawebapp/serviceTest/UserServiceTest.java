package com.wolroys.socialmediawebapp.serviceTest;

import com.wolroys.socialmediawebapp.service.UserService;
import com.wolroys.socialmediawebapp.TestSocialWebApplication;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@RequiredArgsConstructor
@ActiveProfiles("test")
@SpringBootTest
public class UserServiceTest extends TestSocialWebApplication {

    private final UserService userService;

    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    void findAllTest(){
        var actualResult = userService.findAll();

        assertThat(actualResult).hasSize(3);
    }

    @Test
    void findByIdTest(){
        var actualResult = userService.findById(1);

        String email = "user1@example.com";

        assertTrue(actualResult.isPresent());

        actualResult.ifPresent(user -> assertEquals(email, user.getEmail()));
    }
}
