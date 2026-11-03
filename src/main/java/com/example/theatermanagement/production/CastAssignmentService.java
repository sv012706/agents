package com.example.theatermanagement.production;

import com.example.theatermanagement.castmember.CastMember;
import com.example.theatermanagement.castmember.CastMemberService;
import com.example.theatermanagement.common.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CastAssignmentService {

    private final ProductionService productionService;
    private final CastMemberService castMemberService;
    private final CastAssignmentRepository castAssignmentRepository;

    public CastAssignmentService(
            ProductionService productionService,
            CastMemberService castMemberService,
            CastAssignmentRepository castAssignmentRepository) {
        this.productionService = productionService;
        this.castMemberService = castMemberService;
        this.castAssignmentRepository = castAssignmentRepository;
    }

    @Transactional(readOnly = true)
    public List<CastAssignmentResponse> findByProduction(Long productionId) {
        productionService.getProduction(productionId);
        return castAssignmentRepository.findByProductionId(productionId).stream()
                .map(CastAssignmentResponse::from)
                .toList();
    }

    @Transactional
    public CastAssignmentResponse assign(Long productionId, CastAssignmentRequest request) {
        Production production = productionService.getProduction(productionId);
        CastMember castMember = castMemberService.getCastMember(request.castMemberId());
        CastAssignment assignment = new CastAssignment(production, castMember, request.roleName());
        return CastAssignmentResponse.from(castAssignmentRepository.save(assignment));
    }

    @Transactional
    public void remove(Long productionId, Long assignmentId) {
        CastAssignment assignment = castAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Cast assignment", assignmentId));
        if (!assignment.getProduction().getId().equals(productionId)) {
            throw new ResourceNotFoundException("Cast assignment", assignmentId);
        }
        castAssignmentRepository.delete(assignment);
    }
}
