package com.example.theatermanagement.production;

public record ProductionResponse(
        Long id,
        String title,
        String description,
        String genre,
        int durationMinutes) {

    public static ProductionResponse from(Production production) {
        return new ProductionResponse(
                production.getId(),
                production.getTitle(),
                production.getDescription(),
                production.getGenre(),
                production.getDurationMinutes());
    }
}
