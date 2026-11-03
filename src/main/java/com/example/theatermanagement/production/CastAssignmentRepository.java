package com.example.theatermanagement.production;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CastAssignmentRepository extends JpaRepository<CastAssignment, Long> {

    List<CastAssignment> findByProductionId(Long productionId);
}
