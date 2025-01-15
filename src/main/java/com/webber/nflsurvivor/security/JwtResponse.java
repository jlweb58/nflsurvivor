package com.webber.nflsurvivor.security;

import com.webber.nflsurvivor.user.User;

public record JwtResponse(String accessToken, User user) { }
