package com.example.theatermanagement.production;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CastAssignmentRequest(
        @NotNull Long castMemberId,
        @NotBlank String roleName) {
}
