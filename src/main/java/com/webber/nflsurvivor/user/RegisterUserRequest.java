package com.webber.nflsurvivor.user;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequest(String name, @NotBlank String username, @NotBlank String password, @NotBlank Long poolId) {
}
