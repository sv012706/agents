package com.example.theatermanagement.production;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productions/{productionId}/cast")
public class CastAssignmentController {

    private final CastAssignmentService castAssignmentService;

    public CastAssignmentController(CastAssignmentService castAssignmentService) {
        this.castAssignmentService = castAssignmentService;
    }

    @GetMapping
    public List<CastAssignmentResponse> findByProduction(@PathVariable Long productionId) {
        return castAssignmentService.findByProduction(productionId);
    }

    @PostMapping
    public ResponseEntity<CastAssignmentResponse> assign(
            @PathVariable Long productionId,
            @Valid @RequestBody CastAssignmentRequest request) {
        CastAssignmentResponse response = castAssignmentService.assign(productionId, request);
        return ResponseEntity.created(URI.create(
                "/api/productions/" + productionId + "/cast/" + response.id())).body(response);
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<Void> remove(
            @PathVariable Long productionId,
            @PathVariable Long assignmentId) {
        castAssignmentService.remove(productionId, assignmentId);
        return ResponseEntity.noContent().build();
    }
}
