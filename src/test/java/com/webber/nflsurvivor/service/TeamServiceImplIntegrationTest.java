package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.SurvivorApplication;
import com.webber.nflsurvivor.domain.Team;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SurvivorApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class TeamServiceImplIntegrationTest {

    @Autowired
    private TeamService teamService;

    @Test
    public void testCreateAndFindByName() {
        Team team = new Team("Seattle Seahawks", new byte[]{0x1, 0x2, 0x3, 0x4});
        Team created = teamService.create(team);
        assertNotNull(created);
        Team found = teamService.findByName(created.getName());
        assertEquals(created, found);
    }

    @Test
    public void testCreateAlreadyExists() {
        Team team = new Team("Seattle Seahawks", new byte[]{0x1, 0x2, 0x3, 0x4});
        Team created = teamService.create(team);
        assertThrows(IllegalArgumentException.class, () -> teamService.create(created));
    }

}
