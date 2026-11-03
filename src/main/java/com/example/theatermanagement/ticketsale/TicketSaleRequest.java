package com.example.theatermanagement.ticketsale;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TicketSaleRequest(
        @NotNull Long performanceId,
        @NotBlank String purchaserName,
        @NotBlank @Email String purchaserEmail,
        @Positive int quantity) {
}
