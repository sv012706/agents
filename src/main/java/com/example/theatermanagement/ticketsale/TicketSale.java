package com.example.theatermanagement.ticketsale;

import com.example.theatermanagement.performance.Performance;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "ticket_sales")
public class TicketSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Performance performance;

    @Column(nullable = false)
    private String purchaserName;

    @Column(nullable = false)
    private String purchaserEmail;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private Instant purchasedAt;

    protected TicketSale() {
    }

    public TicketSale(Performance performance, String purchaserName, String purchaserEmail,
                      int quantity, BigDecimal totalPrice, Instant purchasedAt) {
        this.performance = performance;
        this.purchaserName = purchaserName;
        this.purchaserEmail = purchaserEmail;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.purchasedAt = purchasedAt;
    }

    public Long getId() {
        return id;
    }

    public Performance getPerformance() {
        return performance;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public String getPurchaserEmail() {
        return purchaserEmail;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Instant getPurchasedAt() {
        return purchasedAt;
    }
}
