package com.webber.nflsurvivor.controller;

import java.util.List;

public record JwtResponse(String accessToken, Long id, String username, List<String> roles) {

}
