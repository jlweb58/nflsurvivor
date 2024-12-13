package com.webber.nflsurvivor.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logo", columnDefinition = "BLOB")
    private byte[] logo;

    protected Team() {

    }

    public Team(String name, byte[] logo) {
        this.name = name;
        this.logo = logo;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getLogo() {
        return logo;
    }

    @Override
    public String toString() {
        return name;
    }
}
