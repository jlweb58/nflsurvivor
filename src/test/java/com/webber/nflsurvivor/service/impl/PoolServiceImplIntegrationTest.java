package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.SurvivorApplication;
import com.webber.nflsurvivor.domain.Pool;
import com.webber.nflsurvivor.user.User;
import com.webber.nflsurvivor.service.PoolService;
import com.webber.nflsurvivor.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest(classes = SurvivorApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class PoolServiceImplIntegrationTest {

    @Autowired
    private PoolService poolService;

    @Autowired
    private UserService userService;


    @Test
    public void testCreate() {
        Pool pool = new Pool("rsf");
        pool = poolService.create(pool);
        assertNotNull("pool was null", pool);
        assertNotNull("ID was null", pool.getId());
    }

    @Test
    public void testFindPoolsForUser() throws Exception {
        User user1 = userService.create(new User("DD", "dd@dd.com", "pw"));
        User user2 = userService.create(new User("DD", "dd2@dd.com", "pw"));
        Pool pool = new Pool("rsf");
        pool.addPoolMember(user1);
        pool.addPoolMember(user2);

        poolService.create(pool);
        Set<Pool> pools = user1.getPools();
        assertNotNull("pools were null", pools);
        assertEquals(1, pools.size());
        assertTrue(pools.contains(pool));

        pools = user2.getPools();
        assertNotNull("pools were null", pools);
        assertEquals(1, pools.size());
        assertTrue(pools.contains(pool));
    }

    @Test
    public void testFindUsersInPool() throws Exception {
        User user1 = userService.create(new User("DD", "dd@dd.com", "pw"));
        User user2 = userService.create(new User("DD", "dd2@dd.com", "pw"));
        User user3 = userService.create(new User("DD", "dd3@dd.com", "pw"));
        Pool pool = new Pool("rsf");
        pool.addPoolMember(user1);
        pool.addPoolMember(user2);
        poolService.create(pool);

        Set<User> poolMembers = pool.getPoolMembers();
        assertNotNull("poolMembers were null", poolMembers);
        assertEquals(2, poolMembers.size());
        assertTrue(poolMembers.contains(user1));
        assertTrue(poolMembers.contains(user2));
    }
}
