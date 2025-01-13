package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.domain.Pool;
import com.webber.nflsurvivor.domain.User;

import java.util.List;
import java.util.Set;

public interface PoolService {

    Pool create(Pool pool);

    Set<Pool> loadAll();

    Pool find(Long poolId);

    List<Pool> findByUserId(Long userId);
}
