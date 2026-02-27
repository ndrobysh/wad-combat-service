package com.wad.combat.repository;

import com.wad.combat.model.CombatLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombatLogRepository extends MongoRepository<CombatLog, String> {
}
