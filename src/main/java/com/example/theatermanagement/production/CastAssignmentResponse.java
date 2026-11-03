package com.example.theatermanagement.production;

public record CastAssignmentResponse(
        Long id,
        Long productionId,
        Long castMemberId,
        String castMemberName,
        String roleName) {

    public static CastAssignmentResponse from(CastAssignment assignment) {
        return new CastAssignmentResponse(
                assignment.getId(),
                assignment.getProduction().getId(),
                assignment.getCastMember().getId(),
                assignment.getCastMember().getFullName(),
                assignment.getRoleName());
    }
}
