package com.example.theatermanagement.performance;

import com.example.theatermanagement.common.ResourceNotFoundException;
import com.example.theatermanagement.production.Production;
import com.example.theatermanagement.production.ProductionRepository;
import com.example.theatermanagement.venue.Venue;
import com.example.theatermanagement.venue.VenueRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final ProductionRepository productionRepository;
    private final VenueRepository venueRepository;

    public PerformanceService(
            PerformanceRepository performanceRepository,
            ProductionRepository productionRepository,
            VenueRepository venueRepository) {
        this.performanceRepository = performanceRepository;
        this.productionRepository = productionRepository;
        this.venueRepository = venueRepository;
    }

    @Transactional(readOnly = true)
    public List<PerformanceResponse> findAll() {
        return performanceRepository.findAll().stream().map(PerformanceResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public PerformanceResponse findById(Long id) {
        return PerformanceResponse.from(getPerformance(id));
    }

    @Transactional
    public PerformanceResponse create(PerformanceRequest request) {
        Production production = productionRepository.findById(request.productionId())
                .orElseThrow(() -> new ResourceNotFoundException("Production", request.productionId()));
        Venue venue = venueRepository.findById(request.venueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue", request.venueId()));
        Performance performance = new Performance(
                production, venue, request.startsAt(), request.ticketPrice());
        return PerformanceResponse.from(performanceRepository.save(performance));
    }

    private Performance getPerformance(Long id) {
        return performanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Performance", id));
    }
}
