package com.webber.nflsurvivor.security;

import com.webber.nflsurvivor.domain.Pool;
import com.webber.nflsurvivor.user.PlayerStatus;
import com.webber.nflsurvivor.user.UserRole;

import java.util.Set;

public record JwtResponse(String accessToken, Long id, String username, Set<UserRole> roles, Set<Pool> pools, PlayerStatus playerStatus) {

}
