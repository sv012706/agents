package com.example.theatermanagement.ticketsale;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket-sales")
public class TicketSaleController {

    private final TicketSaleService ticketSaleService;

    public TicketSaleController(TicketSaleService ticketSaleService) {
        this.ticketSaleService = ticketSaleService;
    }

    @GetMapping
    public List<TicketSaleResponse> findAll() {
        return ticketSaleService.findAll();
    }

    @GetMapping("/{id}")
    public TicketSaleResponse findById(@PathVariable Long id) {
        return ticketSaleService.findById(id);
    }

    @PostMapping
    public ResponseEntity<TicketSaleResponse> purchase(@Valid @RequestBody TicketSaleRequest request) {
        TicketSaleResponse response = ticketSaleService.purchase(request);
        return ResponseEntity.created(URI.create("/api/ticket-sales/" + response.id())).body(response);
    }
}
