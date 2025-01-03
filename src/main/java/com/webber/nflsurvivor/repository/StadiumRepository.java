package com.webber.nflsurvivor.repository;

import com.webber.nflsurvivor.domain.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {

    Stadium findByEspnId(Long espnId);
}
