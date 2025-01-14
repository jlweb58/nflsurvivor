package com.webber.nflsurvivor.user;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequest(@NotBlank String oldPassword, @NotBlank String newPassword) {
}
