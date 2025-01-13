package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.Pool;
import com.webber.nflsurvivor.domain.User;
import com.webber.nflsurvivor.repository.PoolRepository;
import com.webber.nflsurvivor.service.PoolService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PoolServiceImpl implements PoolService {
    private final PoolRepository poolRepository;

    @Autowired
    public PoolServiceImpl(PoolRepository poolRepository) {
        this.poolRepository = poolRepository;
    }


    @Override
    public Pool create(Pool pool) {
        return poolRepository.save(pool);
    }

    @Override
    public Set<Pool> loadAll() {
        return new HashSet<>(poolRepository.findAll());
    }

    @Override
    public Pool find(Long poolId) {
        return poolRepository.getReferenceById(poolId);
    }

    @Override
    public List<Pool> findByUserId(Long userId) {
        return poolRepository.findByUserId(userId);
    }


}
