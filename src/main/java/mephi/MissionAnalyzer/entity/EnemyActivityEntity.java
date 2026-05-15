package mephi.MissionAnalyzer.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enemy_activities")
public class EnemyActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String behaviorType;

    @ElementCollection
    @CollectionTable(name = "enemy_target_priorities", joinColumns = @JoinColumn(name = "enemy_activity_id"))
    private List<String> targetPriority = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "enemy_attack_patterns", joinColumns = @JoinColumn(name = "enemy_activity_id"))
    private List<String> attackPatterns = new ArrayList<>();

    private String mobility;
    private String escalationRisk;

    public EnemyActivityEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBehaviorType() { return behaviorType; }
    public void setBehaviorType(String behaviorType) { this.behaviorType = behaviorType; }

    public List<String> getTargetPriority() { return targetPriority; }
    public void setTargetPriority(List<String> targetPriority) { this.targetPriority = targetPriority != null ? targetPriority : new ArrayList<>(); }

    public List<String> getAttackPatterns() { return attackPatterns; }
    public void setAttackPatterns(List<String> attackPatterns) { this.attackPatterns = attackPatterns != null ? attackPatterns : new ArrayList<>(); }

    public String getMobility() { return mobility; }
    public void setMobility(String mobility) { this.mobility = mobility; }

    public String getEscalationRisk() { return escalationRisk; }
    public void setEscalationRisk(String escalationRisk) { this.escalationRisk = escalationRisk; }
}