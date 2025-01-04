package com.webber.nflsurvivor.controller;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequest(@NotBlank String oldPassword, @NotBlank String newPassword) {
}
