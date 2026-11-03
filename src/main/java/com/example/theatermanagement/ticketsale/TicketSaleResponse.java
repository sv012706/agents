package com.example.theatermanagement.ticketsale;

import java.math.BigDecimal;
import java.time.Instant;

public record TicketSaleResponse(
        Long id,
        Long performanceId,
        String purchaserName,
        String purchaserEmail,
        int quantity,
        BigDecimal totalPrice,
        Instant purchasedAt) {

    public static TicketSaleResponse from(TicketSale ticketSale) {
        return new TicketSaleResponse(
                ticketSale.getId(),
                ticketSale.getPerformance().getId(),
                ticketSale.getPurchaserName(),
                ticketSale.getPurchaserEmail(),
                ticketSale.getQuantity(),
                ticketSale.getTotalPrice(),
                ticketSale.getPurchasedAt());
    }
}
