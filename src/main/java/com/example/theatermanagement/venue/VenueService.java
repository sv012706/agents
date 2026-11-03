package com.example.theatermanagement.venue;

import com.example.theatermanagement.common.ResourceNotFoundException;
import com.example.theatermanagement.common.BusinessRuleException;
import com.example.theatermanagement.performance.PerformanceRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VenueService {

    private final VenueRepository venueRepository;
    private final PerformanceRepository performanceRepository;

    public VenueService(VenueRepository venueRepository, PerformanceRepository performanceRepository) {
        this.venueRepository = venueRepository;
        this.performanceRepository = performanceRepository;
    }

    @Transactional(readOnly = true)
    public List<VenueResponse> findAll() {
        return venueRepository.findAll().stream().map(VenueResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public VenueResponse findById(Long id) {
        return VenueResponse.from(getVenue(id));
    }

    @Transactional
    public VenueResponse create(VenueRequest request) {
        Venue venue = new Venue(request.name(), request.address(), request.capacity());
        return VenueResponse.from(venueRepository.save(venue));
    }

    @Transactional
    public VenueResponse update(Long id, VenueRequest request) {
        Venue venue = getVenue(id);
        if (request.capacity() < venue.getCapacity()
                && performanceRepository.existsByVenue_IdAndTicketsSoldGreaterThan(id, request.capacity())) {
            throw new BusinessRuleException("Venue capacity cannot be reduced below tickets already sold");
        }
        venue.update(request.name(), request.address(), request.capacity());
        return VenueResponse.from(venue);
    }

    @Transactional
    public void delete(Long id) {
        venueRepository.delete(getVenue(id));
    }

    private Venue getVenue(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue", id));
    }
}
