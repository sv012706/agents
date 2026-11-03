package com.example.theatermanagement.production;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productions")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping
    public List<ProductionResponse> findAll() {
        return productionService.findAll();
    }

    @GetMapping("/{id}")
    public ProductionResponse findById(@PathVariable Long id) {
        return productionService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProductionResponse> create(@Valid @RequestBody ProductionRequest request) {
        ProductionResponse response = productionService.create(request);
        return ResponseEntity.created(URI.create("/api/productions/" + response.id())).body(response);
    }

    @PutMapping("/{id}")
    public ProductionResponse update(@PathVariable Long id, @Valid @RequestBody ProductionRequest request) {
        return productionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
