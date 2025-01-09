package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.domain.Pool;
import com.webber.nflsurvivor.domain.User;

import java.util.Set;

public interface PoolService {

    Pool create(Pool pool);

    Set<Pool> loadAll();
}
