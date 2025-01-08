package com.webber.nflsurvivor.controller;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequest(String name, @NotBlank String username, @NotBlank String password) {
}
