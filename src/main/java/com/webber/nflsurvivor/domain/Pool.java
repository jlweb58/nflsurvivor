package com.webber.nflsurvivor.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pools")
public class Pool {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "pool_users",
            joinColumns =  @JoinColumn(name = "pool_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )

    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<User> poolMembers = new HashSet<>();

    @Column(name = "name", nullable = false)
    private String name;

    protected Pool() {}

    public Pool(String name) {
        this.name = name;
    }

    public Set<User> getPoolMembers() {
        return poolMembers;
    }

    public Pool setPoolMembers(Set<User> poolMembers) {
        this.poolMembers = poolMembers;
        return this;
    }

    public Pool addPoolMember(User user) {
        poolMembers.add(user);
        user.getPools().add(this);
        return this;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
