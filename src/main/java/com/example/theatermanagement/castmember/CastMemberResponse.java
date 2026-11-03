package com.example.theatermanagement.castmember;

public record CastMemberResponse(Long id, String fullName, String biography) {

    public static CastMemberResponse from(CastMember castMember) {
        return new CastMemberResponse(castMember.getId(), castMember.getFullName(), castMember.getBiography());
    }
}
