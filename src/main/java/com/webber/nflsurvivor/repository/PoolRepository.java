package com.webber.nflsurvivor.repository;

import com.webber.nflsurvivor.domain.Pool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoolRepository extends JpaRepository<Pool, Long> {
}
