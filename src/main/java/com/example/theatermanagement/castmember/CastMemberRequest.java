package com.example.theatermanagement.castmember;

import jakarta.validation.constraints.NotBlank;

public record CastMemberRequest(@NotBlank String fullName, String biography) {
}
