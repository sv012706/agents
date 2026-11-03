package com.example.theatermanagement.performance;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PerformanceResponse(
        Long id,
        Long productionId,
        String productionTitle,
        Long venueId,
        String venueName,
        LocalDateTime startsAt,
        BigDecimal ticketPrice,
        int capacity,
        int ticketsSold,
        int ticketsAvailable) {

    public static PerformanceResponse from(Performance performance) {
        int capacity = performance.getVenue().getCapacity();
        return new PerformanceResponse(
                performance.getId(),
                performance.getProduction().getId(),
                performance.getProduction().getTitle(),
                performance.getVenue().getId(),
                performance.getVenue().getName(),
                performance.getStartsAt(),
                performance.getTicketPrice(),
                capacity,
                performance.getTicketsSold(),
                capacity - performance.getTicketsSold());
    }
}
