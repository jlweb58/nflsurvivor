package com.webber.nflsurvivor.controller;

import com.webber.nflsurvivor.domain.Pool;
import com.webber.nflsurvivor.service.PoolService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Transactional
@RequestMapping("/pools")

public class PoolController {

    private final PoolService poolService;

    @Autowired
    public PoolController(PoolService poolService) {
        this.poolService = poolService;
    }

    @PostMapping
    public ResponseEntity<Pool> createPool(@RequestBody Pool pool) {
        Pool newPool = poolService.create(new Pool(pool.getName()));
        return ResponseEntity.ok(newPool);
    }

    @GetMapping
    public ResponseEntity<Set<Pool>> listAll() {
        Set<Pool> allPools = poolService.loadAll();
        return ResponseEntity.ok(allPools);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<List<Pool>> getForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(poolService.findByUserId(userId));
    }
}
