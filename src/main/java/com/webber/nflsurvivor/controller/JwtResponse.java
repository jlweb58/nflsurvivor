package com.webber.nflsurvivor.controller;

import com.webber.nflsurvivor.domain.Pool;

import java.util.List;

public record JwtResponse(String accessToken, Long id, String username, List<String> roles, List<Pool> pools) {

}
