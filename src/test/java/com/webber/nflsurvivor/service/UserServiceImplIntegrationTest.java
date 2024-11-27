package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.SurvivorApplication;
import com.webber.nflsurvivor.domain.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// @ActiveProfiles("test")
@SpringBootTest(classes = SurvivorApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceImplIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateAndFind() {
        User user = new User("testname", "test@test.com", "password");
        User created = userService.create(user);
        assertNotNull(created);
        User found = userService.find("test@test.com");
        assertEquals(created, found);
    }

}
