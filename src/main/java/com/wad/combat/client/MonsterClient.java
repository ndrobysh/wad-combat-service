package com.wad.combat.client;

import com.wad.combat.dto.MonsterDTO;

public interface MonsterClient {
    MonsterDTO getMonster(String id, String token);
}
