package com.wad.combat.dto;

public class SkillDTO {
    private int num;
    private int dmg;
    private RatioDTO ratio;
    private int cooldown;
    private int level;
    private int lvlMax;

    public SkillDTO() {}

    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }
    public int getDmg() { return dmg; }
    public void setDmg(int dmg) { this.dmg = dmg; }
    public RatioDTO getRatio() { return ratio; }
    public void setRatio(RatioDTO ratio) { this.ratio = ratio; }
    public int getCooldown() { return cooldown; }
    public void setCooldown(int cooldown) { this.cooldown = cooldown; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public int getLvlMax() { return lvlMax; }
    public void setLvlMax(int lvlMax) { this.lvlMax = lvlMax; }
}
