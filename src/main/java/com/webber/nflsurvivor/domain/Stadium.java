package com.webber.nflsurvivor.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "stadiums")
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "espn_id", unique = true)
    private Long espnId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "zone_id", nullable = false)
    private String zoneId;

    protected Stadium() {}

    public Stadium(Long espnId, String name, String zoneId) {
        this.espnId = espnId;
        this.name = name;
        this.zoneId = zoneId;
    }

    public Long getEspnId() {
        return espnId;
    }

    public String getName() {
        return name;
    }

    public String getZoneId() {
        return zoneId;
    }

    @Override
    public String toString() {
        return "Stadium{" +
                "espnId=" + espnId +
                ", name='" + name + '\'' +
                ", zoneId='" + zoneId + '\'' +
                '}';
    }
}
