package com.webber.nflsurvivor.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app_users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<UserRole> userRoles = new HashSet<>();

    @ManyToMany(mappedBy = "poolMembers")
    @JsonIgnoreProperties("poolMembers")
    private Set<Pool> pools = new HashSet<>();

    protected User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User addUserRole(UserRole userRole) {
        userRoles.add(userRole);
        return this;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    public Set<Pool> getPools() {
        return pools;
    }

    public User setPools(Set<Pool> pools) {
        this.pools = pools;
        return this;
    }

    public User addPool(Pool pool) {
        pools.add(pool);
        return this;
    }
}
