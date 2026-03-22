package com.wad.combat.service;

import com.wad.combat.client.MonsterClient;
import com.wad.combat.dto.MonsterDTO;
import com.wad.combat.dto.RatioDTO;
import com.wad.combat.dto.SkillDTO;
import com.wad.combat.model.CombatLog;
import com.wad.combat.repository.CombatLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CombatServiceTest {

    private CombatService combatService;

    @Mock
    private MonsterClient monsterClient;

    @Mock
    private CombatLogRepository combatLogRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        combatService = new CombatService(monsterClient, combatLogRepository);
    }

    @Test
    void quand_stats_meilleures_devrait_gagner() {
        // Setup monsters
        MonsterDTO m1 = new MonsterDTO();
        m1.setId("m1");
        m1.setName("Attacker");
        m1.setHp(100);
        m1.setAtk(20);
        m1.setDef(5);
        m1.setVit(15);

        SkillDTO s1 = new SkillDTO();
        s1.setNum(1);
        s1.setDmg(10);
        s1.setCooldown(0);
        m1.setSkills(Collections.singletonList(s1));

        MonsterDTO m2 = new MonsterDTO();
        m2.setId("m2");
        m2.setName("Defender");
        m2.setHp(100);
        m2.setAtk(10);
        m2.setDef(10);
        m2.setVit(10);

        SkillDTO s2 = new SkillDTO();
        s2.setNum(1);
        s2.setDmg(5);
        s2.setCooldown(0);
        m2.setSkills(Collections.singletonList(s2));

        when(monsterClient.getMonster("m1", "token")).thenReturn(m1);
        when(monsterClient.getMonster("m2", "token")).thenReturn(m2);
        when(combatLogRepository.save(any(CombatLog.class))).thenAnswer(i -> i.getArgument(0));

        CombatLog result = combatService.fight("m1", "m2", "token");

        assertNotNull(result);
        assertEquals("m1", result.getMonster1Id());
        assertEquals("m2", result.getMonster2Id());
        assertTrue(result.getLogs().size() > 0);

        // m1 has more vit, should go first
        assertEquals("m1", result.getLogs().get(0).getAttackerId());

        // Winner should be m1 since it has better stats
        assertEquals("m1", result.getWinnerId());
    }

    @Test
    void quand_skill_en_cooldown_utilise_skill_basique() {
        // Setup m1 with a high CD skill
        MonsterDTO m1 = new MonsterDTO();
        m1.setId("m1");
        m1.setName("M1");
        m1.setHp(1000);
        m1.setAtk(100);
        m1.setDef(0);
        m1.setVit(100);

        SkillDTO s1 = new SkillDTO();
        s1.setNum(1);
        s1.setDmg(10);
        s1.setCooldown(0);

        SkillDTO s2 = new SkillDTO();
        s2.setNum(2);
        s2.setDmg(50);
        s2.setCooldown(2); // CD of 2

        m1.setSkills(Arrays.asList(s1, s2));

        MonsterDTO m2 = new MonsterDTO();
        m2.setId("m2");
        m2.setName("M2");
        m2.setHp(1000);
        m2.setAtk(1);
        m2.setDef(0);
        m2.setVit(1);
        m2.setSkills(Collections.singletonList(s1));

        when(monsterClient.getMonster("m1", "token")).thenReturn(m1);
        when(monsterClient.getMonster("m2", "token")).thenReturn(m2);
        when(combatLogRepository.save(any(CombatLog.class))).thenAnswer(i -> i.getArgument(0));

        CombatLog result = combatService.fight("m1", "m2", "token");

        // skill 2 a CD=2 donc M1 alterne entre skill 2 et skill 1

        int m1TurnCount = 0;
        for (var log : result.getLogs()) {
            if (log.getAttackerId().equals("m1")) {
                m1TurnCount++;
                if (m1TurnCount == 1) assertEquals(2, log.getSkillNum());
                if (m1TurnCount == 2) assertEquals(1, log.getSkillNum());
                if (m1TurnCount == 3) assertEquals(2, log.getSkillNum());
            }
        }
    }

    @Test
    void test_combat_retourne_un_resultat() throws Exception {
        MonsterDTO m1 = new MonsterDTO();
        m1.setId("x1"); m1.setName("A"); m1.setHp(50); m1.setAtk(10); m1.setDef(0); m1.setVit(10);
        SkillDTO sk = new SkillDTO(); sk.setNum(1); sk.setDmg(20); sk.setCooldown(0);
        m1.setSkills(Collections.singletonList(sk));

        MonsterDTO m2 = new MonsterDTO();
        m2.setId("x2"); m2.setName("B"); m2.setHp(50); m2.setAtk(10); m2.setDef(0); m2.setVit(5);
        m2.setSkills(Collections.singletonList(sk));

        when(monsterClient.getMonster("x1", "t")).thenReturn(m1);
        when(monsterClient.getMonster("x2", "t")).thenReturn(m2);
        when(combatLogRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Thread.sleep(100); // pas sur pourquoi mais sans ca le test echoue parfois
        CombatLog res = combatService.fight("x1", "x2", "t");
        assertNotNull(res);
    }
}
