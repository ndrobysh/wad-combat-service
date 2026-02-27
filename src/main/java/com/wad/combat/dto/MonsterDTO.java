package com.wad.combat.dto;

import java.util.List;

public class MonsterDTO {
    private String id;
    private String owner;
    private String name;
    private String elementType;
    private int hp;
    private int atk;
    private int def;
    private int vit;
    private int level;
    private List<SkillDTO> skills;

    public MonsterDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getElementType() { return elementType; }
    public void setElementType(String elementType) { this.elementType = elementType; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public int getAtk() { return atk; }
    public void setAtk(int atk) { this.atk = atk; }
    public int getDef() { return def; }
    public void setDef(int def) { this.def = def; }
    public int getVit() { return vit; }
    public void setVit(int vit) { this.vit = vit; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public List<SkillDTO> getSkills() { return skills; }
    public void setSkills(List<SkillDTO> skills) { this.skills = skills; }
}
