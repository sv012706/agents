package com.example.theatermanagement.performance;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PerformanceRequest(
        @NotNull Long productionId,
        @NotNull Long venueId,
        @NotNull @Future LocalDateTime startsAt,
        @NotNull @DecimalMin("0.00") BigDecimal ticketPrice) {
}
