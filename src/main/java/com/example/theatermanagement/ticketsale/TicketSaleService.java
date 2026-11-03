package com.example.theatermanagement.ticketsale;

import com.example.theatermanagement.common.BusinessRuleException;
import com.example.theatermanagement.common.ResourceNotFoundException;
import com.example.theatermanagement.performance.Performance;
import com.example.theatermanagement.performance.PerformanceRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketSaleService {

    private final TicketSaleRepository ticketSaleRepository;
    private final PerformanceRepository performanceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public TicketSaleService(
            TicketSaleRepository ticketSaleRepository,
            PerformanceRepository performanceRepository,
            ApplicationEventPublisher eventPublisher) {
        this.ticketSaleRepository = ticketSaleRepository;
        this.performanceRepository = performanceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public List<TicketSaleResponse> findAll() {
        return ticketSaleRepository.findAll().stream().map(TicketSaleResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public TicketSaleResponse findById(Long id) {
        return TicketSaleResponse.from(getTicketSale(id));
    }

    @Transactional
    public TicketSaleResponse purchase(TicketSaleRequest request) {
        Performance performance = performanceRepository.findByIdForUpdate(request.performanceId())
                .orElseThrow(() -> new ResourceNotFoundException("Performance", request.performanceId()));

        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        if (!performance.getStartsAt().isAfter(now)) {
            throw new BusinessRuleException("Tickets cannot be purchased for a performance that has started");
        }
        int capacity = performance.getVenue().getCapacity();
        if (request.quantity() > capacity - performance.getTicketsSold()) {
            throw new BusinessRuleException("Not enough tickets are available for this performance");
        }

        Instant purchasedAt = Instant.now();
        TicketSale ticketSale = new TicketSale(
                performance,
                request.purchaserName(),
                request.purchaserEmail(),
                request.quantity(),
                performance.getTicketPrice().multiply(java.math.BigDecimal.valueOf(request.quantity())),
                purchasedAt);
        performance.addTicketsSold(request.quantity());
        TicketSale savedSale = ticketSaleRepository.save(ticketSale);
        eventPublisher.publishEvent(TicketSaleCreatedEvent.from(savedSale));
        return TicketSaleResponse.from(savedSale);
    }

    private TicketSale getTicketSale(Long id) {
        return ticketSaleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket sale", id));
    }
}
