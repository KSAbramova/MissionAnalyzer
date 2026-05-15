package mephi.MissionAnalyzer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "curses")
public class CurseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ThreatLevel threatLevel;

    public enum ThreatLevel {
        HIGH, SPECIAL_GRADE
    }

    public CurseEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ThreatLevel getThreatLevel() { return threatLevel; }
    public void setThreatLevel(ThreatLevel threatLevel) { this.threatLevel = threatLevel; }
}