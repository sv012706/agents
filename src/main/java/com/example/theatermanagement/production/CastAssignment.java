package com.example.theatermanagement.production;

import com.example.theatermanagement.castmember.CastMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cast_assignments")
public class CastAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Production production;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CastMember castMember;

    @Column(nullable = false)
    private String roleName;

    protected CastAssignment() {
    }

    public CastAssignment(Production production, CastMember castMember, String roleName) {
        this.production = production;
        this.castMember = castMember;
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public Production getProduction() {
        return production;
    }

    public CastMember getCastMember() {
        return castMember;
    }

    public String getRoleName() {
        return roleName;
    }
}
