package com.example.theatermanagement.ticketsale;

import java.math.BigDecimal;
import java.time.Instant;

public record TicketSaleCreatedEvent(
        Long saleId,
        Long performanceId,
        String purchaserName,
        String purchaserEmail,
        int quantity,
        BigDecimal totalPrice,
        Instant purchasedAt) {

    public static TicketSaleCreatedEvent from(TicketSale ticketSale) {
        return new TicketSaleCreatedEvent(
                ticketSale.getId(),
                ticketSale.getPerformance().getId(),
                ticketSale.getPurchaserName(),
                ticketSale.getPurchaserEmail(),
                ticketSale.getQuantity(),
                ticketSale.getTotalPrice(),
                ticketSale.getPurchasedAt());
    }
}
