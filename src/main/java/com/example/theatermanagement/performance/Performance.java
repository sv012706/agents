package com.example.theatermanagement.performance;

import com.example.theatermanagement.production.Production;
import com.example.theatermanagement.venue.Venue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "performances")
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Production production;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Venue venue;

    @Column(nullable = false)
    private LocalDateTime startsAt;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal ticketPrice;

    @Column(nullable = false)
    private int ticketsSold;

    protected Performance() {
    }

    public Performance(Production production, Venue venue, LocalDateTime startsAt, BigDecimal ticketPrice) {
        this.production = production;
        this.venue = venue;
        this.startsAt = startsAt;
        this.ticketPrice = ticketPrice;
        this.ticketsSold = 0;
    }

    public Long getId() {
        return id;
    }

    public Production getProduction() {
        return production;
    }

    public Venue getVenue() {
        return venue;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void update(Production production, Venue venue, LocalDateTime startsAt, BigDecimal ticketPrice) {
        this.production = production;
        this.venue = venue;
        this.startsAt = startsAt;
        this.ticketPrice = ticketPrice;
    }

    public void addTicketsSold(int quantity) {
        this.ticketsSold += quantity;
    }
}
