package com.example.theatermanagement.castmember;

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
@RequestMapping("/api/cast-members")
public class CastMemberController {

    private final CastMemberService castMemberService;

    public CastMemberController(CastMemberService castMemberService) {
        this.castMemberService = castMemberService;
    }

    @GetMapping
    public List<CastMemberResponse> findAll() {
        return castMemberService.findAll();
    }

    @GetMapping("/{id}")
    public CastMemberResponse findById(@PathVariable Long id) {
        return castMemberService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CastMemberResponse> create(@Valid @RequestBody CastMemberRequest request) {
        CastMemberResponse response = castMemberService.create(request);
        return ResponseEntity.created(URI.create("/api/cast-members/" + response.id())).body(response);
    }

    @PutMapping("/{id}")
    public CastMemberResponse update(@PathVariable Long id, @Valid @RequestBody CastMemberRequest request) {
        return castMemberService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        castMemberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
