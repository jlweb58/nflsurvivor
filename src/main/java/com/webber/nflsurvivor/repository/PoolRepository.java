package com.webber.nflsurvivor.repository;

import com.webber.nflsurvivor.domain.Pool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PoolRepository extends JpaRepository<Pool, Long> {


    @Query("SELECT p FROM Pool p JOIN p.poolMembers m where m.id = :userId")
    List<Pool> findByUserId(Long userId);
}
