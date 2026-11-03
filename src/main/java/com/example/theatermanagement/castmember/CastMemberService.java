package com.example.theatermanagement.castmember;

import com.example.theatermanagement.common.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CastMemberService {

    private final CastMemberRepository castMemberRepository;

    public CastMemberService(CastMemberRepository castMemberRepository) {
        this.castMemberRepository = castMemberRepository;
    }

    @Transactional(readOnly = true)
    public List<CastMemberResponse> findAll() {
        return castMemberRepository.findAll().stream().map(CastMemberResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public CastMemberResponse findById(Long id) {
        return CastMemberResponse.from(getCastMember(id));
    }

    @Transactional
    public CastMemberResponse create(CastMemberRequest request) {
        CastMember castMember = new CastMember(request.fullName(), request.biography());
        return CastMemberResponse.from(castMemberRepository.save(castMember));
    }

    @Transactional
    public CastMemberResponse update(Long id, CastMemberRequest request) {
        CastMember castMember = getCastMember(id);
        castMember.update(request.fullName(), request.biography());
        return CastMemberResponse.from(castMember);
    }

    @Transactional
    public void delete(Long id) {
        castMemberRepository.delete(getCastMember(id));
    }

    public CastMember getCastMember(Long id) {
        return castMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cast member", id));
    }
}
