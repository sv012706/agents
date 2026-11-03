package com.example.theatermanagement.production;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductionRequest(
        @NotBlank String title,
        String description,
        @NotBlank String genre,
        @Positive int durationMinutes) {
}
