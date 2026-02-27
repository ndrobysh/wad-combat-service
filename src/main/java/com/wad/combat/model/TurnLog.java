package com.wad.combat.model;

public class TurnLog {
    private int turnNumber;
    private String attackerId;
    private String attackerName;
    private int skillNum;
    private String actionDescription;
    private int damageDealt;
    private int targetRemainingHp;

    public TurnLog() {}

    public TurnLog(int turnNumber, String attackerId, String attackerName, int skillNum, 
                   String actionDescription, int damageDealt, int targetRemainingHp) {
        this.turnNumber = turnNumber;
        this.attackerId = attackerId;
        this.attackerName = attackerName;
        this.skillNum = skillNum;
        this.actionDescription = actionDescription;
        this.damageDealt = damageDealt;
        this.targetRemainingHp = targetRemainingHp;
    }

    public int getTurnNumber() { return turnNumber; }
    public void setTurnNumber(int turnNumber) { this.turnNumber = turnNumber; }
    public String getAttackerId() { return attackerId; }
    public void setAttackerId(String attackerId) { this.attackerId = attackerId; }
    public String getAttackerName() { return attackerName; }
    public void setAttackerName(String attackerName) { this.attackerName = attackerName; }
    public int getSkillNum() { return skillNum; }
    public void setSkillNum(int skillNum) { this.skillNum = skillNum; }
    public String getActionDescription() { return actionDescription; }
    public void setActionDescription(String actionDescription) { this.actionDescription = actionDescription; }
    public int getDamageDealt() { return damageDealt; }
    public void setDamageDealt(int damageDealt) { this.damageDealt = damageDealt; }
    public int getTargetRemainingHp() { return targetRemainingHp; }
    public void setTargetRemainingHp(int targetRemainingHp) { this.targetRemainingHp = targetRemainingHp; }
}
