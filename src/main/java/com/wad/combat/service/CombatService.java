package com.wad.combat.service;

import com.wad.combat.client.MonsterClient;
import com.wad.combat.dto.MonsterDTO;
import com.wad.combat.dto.SkillDTO;
import com.wad.combat.model.CombatLog;
import com.wad.combat.model.TurnLog;
import com.wad.combat.repository.CombatLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CombatService {

    private final MonsterClient monsterClient;
    private final CombatLogRepository combatLogRepository;

    public CombatService(MonsterClient monsterClient, CombatLogRepository combatLogRepository) {
        this.monsterClient = monsterClient;
        this.combatLogRepository = combatLogRepository;
    }

    public CombatLog fight(String monster1Id, String monster2Id, String token) {
        MonsterDTO m1 = monsterClient.getMonster(monster1Id, token);
        MonsterDTO m2 = monsterClient.getMonster(monster2Id, token);

        if (m1 == null || m2 == null) {
            throw new RuntimeException("Un des deux monstres n'existe pas");
        }

        List<TurnLog> logs = new ArrayList<>();
        
        // Internal state for combat
        int hp1 = m1.getHp();
        int hp2 = m2.getHp();
        
        // Cooldowns: Skill number -> turns remaining
        Map<Integer, Integer> cooldowns1 = new HashMap<>();
        Map<Integer, Integer> cooldowns2 = new HashMap<>();
        
        // Initialize skills (sort by number descending)
        List<SkillDTO> skills1 = new ArrayList<>(m1.getSkills());
        skills1.sort((s1, s2) -> Integer.compare(s2.getNum(), s1.getNum()));
        
        List<SkillDTO> skills2 = new ArrayList<>(m2.getSkills());
        skills2.sort((s1, s2) -> Integer.compare(s2.getNum(), s1.getNum()));

        int turnCount = 1;
        MonsterDTO currentAttacker = (m1.getVit() >= m2.getVit()) ? m1 : m2;
        MonsterDTO currentTarget = (currentAttacker == m1) ? m2 : m1;

        while (hp1 > 0 && hp2 > 0 && turnCount < 100) { // Safety limit of 100 turns
            MonsterDTO attacker = currentAttacker;
            MonsterDTO target = currentTarget;
            Map<Integer, Integer> attackerCooldowns = (attacker == m1) ? cooldowns1 : cooldowns2;
            List<SkillDTO> attackerSkills = (attacker == m1) ? skills1 : skills2;
            
            // Decrement all existing cooldowns for this attacker at the start of their turn
            for (Integer skillNum : attackerCooldowns.keySet()) {
                attackerCooldowns.put(skillNum, Math.max(0, attackerCooldowns.get(skillNum) - 1));
            }

            // Choose skill
            SkillDTO chosenSkill = null;
            for (SkillDTO skill : attackerSkills) {
                if (attackerCooldowns.getOrDefault(skill.getNum(), 0) <= 0) {
                    chosenSkill = skill;
                    break;
                }
            }
            
            // If no skill available, default to basic action (skill 1)
            if (chosenSkill == null) {
                chosenSkill = attackerSkills.get(attackerSkills.size() - 1);
            }

            // Calculate damage
            int damage = calculateDamage(attacker, target, chosenSkill);
            if (target == m1) hp1 -= damage; else hp2 -= damage;
            if (hp1 < 0) hp1 = 0;
            if (hp2 < 0) hp2 = 0;

            // Set the cooldown for the used skill
            attackerCooldowns.put(chosenSkill.getNum(), chosenSkill.getCooldown());

            // Log the turn
            logs.add(new TurnLog(
                turnCount,
                attacker.getId(),
                attacker.getName(),
                chosenSkill.getNum(),
                attacker.getName() + " utilise " + (chosenSkill.getName() != null ? chosenSkill.getName() : "Skill " + chosenSkill.getNum()) + " sur " + target.getName(),
                damage,
                (target == m1) ? hp1 : hp2
            ));

            if (hp1 <= 0 || hp2 <= 0) break;

            // Swap roles
            MonsterDTO temp = currentAttacker;
            currentAttacker = currentTarget;
            currentTarget = temp;
            turnCount++;
        }

        MonsterDTO winner = (hp1 > 0) ? m1 : m2;

        CombatLog combatLog = CombatLog.builder()
                .monster1Id(m1.getId())
                .monster1Name(m1.getName())
                .monster2Id(m2.getId())
                .monster2Name(m2.getName())
                .winnerId(winner.getId())
                .winnerName(winner.getName())
                .logs(logs)
                .timestamp(LocalDateTime.now())
                .build();

        return combatLogRepository.save(combatLog);
    }

    // formule inspiree de Pokemon mais simplifiee
    private int calculateDamage(MonsterDTO attacker, MonsterDTO target, SkillDTO skill) {
        double statValue = 0;
        if (skill.getRatio() != null) {
            String stat = skill.getRatio().getStat();
            statValue = switch (stat.toLowerCase()) {
                case "atk" -> attacker.getAtk();
                case "hp" -> attacker.getHp();
                case "def" -> attacker.getDef();
                case "vit" -> attacker.getVit();
                default -> 0;
            };
            statValue = statValue * (skill.getRatio().getPercent() / 100.0);
        }

        double baseDamage = skill.getDmg() + statValue;
        double levelBonus = 1.0 + Math.max(0, skill.getLevel() - 1) * 0.10;
        int totalDamage = (int) (baseDamage * levelBonus);
        return Math.max(1, totalDamage - target.getDef());
    }

    public List<CombatLog> history() {
        return combatLogRepository.findAll();
    }

    public CombatLog getLog(String id) {
        return combatLogRepository.findById(id).orElse(null);
    }
}
