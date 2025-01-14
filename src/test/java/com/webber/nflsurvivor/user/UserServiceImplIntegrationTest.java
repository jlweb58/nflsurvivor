package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.SurvivorApplication;
import com.webber.nflsurvivor.user.User;
import com.webber.nflsurvivor.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SurvivorApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceImplIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateAndFindByEmail() throws Exception {
        User user = new User("testname", "test@test.com", "password");
        User created = userService.create(user);
        assertNotNull(created);
        User found = userService.findByEmail("test@test.com");
        assertEquals(created, found);
    }

    @Test
    public void testCreateAlreadyExists() throws Exception {
        User user = new User("testname", "test@test.com", "password");
        User created = userService.create(user);
        assertThrows(IllegalArgumentException.class, () -> userService.create(created));
    }
}
