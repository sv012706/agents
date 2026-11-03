package com.example.theatermanagement.castmember;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cast_members")
public class CastMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(length = 2000)
    private String biography;

    protected CastMember() {
    }

    public CastMember(String fullName, String biography) {
        this.fullName = fullName;
        this.biography = biography;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBiography() {
        return biography;
    }

    public void update(String fullName, String biography) {
        this.fullName = fullName;
        this.biography = biography;
    }
}
