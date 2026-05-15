package mephi.MissionAnalyzer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "techniques")
public class TechniqueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String owner;
    private Long damage;

    public enum Type {
        INNATE, SHIKIGAMI, BODY, WEAPON
    }

    public TechniqueEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public Long getDamage() { return damage; }
    public void setDamage(Long damage) { this.damage = damage; }
}