package com.example.theatermanagement.performance;

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
@RequestMapping("/api/performances")
public class PerformanceController {

    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @GetMapping
    public List<PerformanceResponse> findAll() {
        return performanceService.findAll();
    }

    @GetMapping("/{id}")
    public PerformanceResponse findById(@PathVariable Long id) {
        return performanceService.findById(id);
    }

    @PostMapping
    public ResponseEntity<PerformanceResponse> create(@Valid @RequestBody PerformanceRequest request) {
        PerformanceResponse response = performanceService.create(request);
        return ResponseEntity.created(URI.create("/api/performances/" + response.id())).body(response);
    }
}
