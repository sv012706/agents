package com.example.theatermanagement.ticketsale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.theatermanagement.common.BusinessRuleException;
import com.example.theatermanagement.performance.Performance;
import com.example.theatermanagement.performance.PerformanceRepository;
import com.example.theatermanagement.production.Production;
import com.example.theatermanagement.venue.Venue;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class TicketSaleServiceTest {

    @Mock
    private TicketSaleRepository ticketSaleRepository;

    @Mock
    private PerformanceRepository performanceRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private TicketSaleService ticketSaleService;
    private Performance performance;

    @BeforeEach
    void setUp() {
        ticketSaleService = new TicketSaleService(ticketSaleRepository, performanceRepository, eventPublisher);
        Venue venue = new Venue("Main Stage", "1 Theater Lane", 100);
        Production production = new Production("Twelfth Night", "A comedy", "Comedy", 150);
        performance = new Performance(production, venue, LocalDateTime.now().plusDays(2), new BigDecimal("25.00"));
    }

    @Test
    void purchaseUpdatesCapacityAndPublishesSaleEvent() {
        when(performanceRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(performance));
        when(ticketSaleRepository.save(any(TicketSale.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        TicketSaleRequest request = new TicketSaleRequest(1L, "Alex Actor", "alex@example.com", 3);

        TicketSaleResponse response = ticketSaleService.purchase(request);

        assertEquals(3, response.quantity());
        assertEquals(new BigDecimal("75.00"), response.totalPrice());
        assertEquals(3, performance.getTicketsSold());
        ArgumentCaptor<TicketSaleCreatedEvent> eventCaptor = ArgumentCaptor.forClass(TicketSaleCreatedEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        assertEquals(3, eventCaptor.getValue().quantity());
        assertEquals(new BigDecimal("75.00"), eventCaptor.getValue().totalPrice());
    }

    @Test
    void purchaseRejectsQuantityAboveRemainingCapacity() {
        performance.addTicketsSold(99);
        when(performanceRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(performance));

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> ticketSaleService.purchase(
                        new TicketSaleRequest(1L, "Alex Actor", "alex@example.com", 2)));

        assertEquals("Not enough tickets are available for this performance", exception.getMessage());
        verify(ticketSaleRepository, never()).save(any(TicketSale.class));
        verify(eventPublisher, never()).publishEvent(any(Object.class));
    }

    @Test
    void purchaseRejectsPerformanceThatHasAlreadyStarted() {
        performance = new Performance(
                new Production("Twelfth Night", "A comedy", "Comedy", 150),
                new Venue("Main Stage", "1 Theater Lane", 100),
                LocalDateTime.now().minusMinutes(1),
                new BigDecimal("25.00"));
        when(performanceRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(performance));

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> ticketSaleService.purchase(
                        new TicketSaleRequest(1L, "Alex Actor", "alex@example.com", 1)));

        assertEquals("Tickets cannot be purchased for a performance that has started", exception.getMessage());
        verify(ticketSaleRepository, never()).save(any(TicketSale.class));
        verify(eventPublisher, never()).publishEvent(any(Object.class));
    }
}
