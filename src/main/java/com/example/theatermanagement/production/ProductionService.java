package com.example.theatermanagement.production;

import com.example.theatermanagement.common.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductionService {

    private final ProductionRepository productionRepository;

    public ProductionService(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductionResponse> findAll() {
        return productionRepository.findAll().stream().map(ProductionResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ProductionResponse findById(Long id) {
        return ProductionResponse.from(getProduction(id));
    }

    @Transactional
    public ProductionResponse create(ProductionRequest request) {
        Production production = new Production(
                request.title(), request.description(), request.genre(), request.durationMinutes());
        return ProductionResponse.from(productionRepository.save(production));
    }

    @Transactional
    public ProductionResponse update(Long id, ProductionRequest request) {
        Production production = getProduction(id);
        production.update(request.title(), request.description(), request.genre(), request.durationMinutes());
        return ProductionResponse.from(production);
    }

    @Transactional
    public void delete(Long id) {
        productionRepository.delete(getProduction(id));
    }

    public Production getProduction(Long id) {
        return productionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Production", id));
    }
}
