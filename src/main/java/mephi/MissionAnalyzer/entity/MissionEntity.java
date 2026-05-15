package mephi.MissionAnalyzer.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "missions")
public class MissionEntity {

    @Id
    private String missionId;

    private LocalDate date;
    private String location;

    @Enumerated(EnumType.STRING)
    private Outcome outcome;

    private Long damageCost;

    @Column(length = 1000)
    private String note = "";

    // Связи с другими сущностями
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "curse_id")
    private CurseEntity curse;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "economic_assessment_id")
    private EconomicAssessmentEntity economicAssessment;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "civilian_impact_id")
    private CivilianImpactEntity civilianImpact;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "enemy_activity_id")
    private EnemyActivityEntity enemyActivity;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "environment_conditions_id")
    private EnvironmentConditionsEntity environmentConditions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mission_id")
    private List<SorcererEntity> sorcerers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mission_id")
    private List<TechniqueEntity> techniques = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mission_id")
    private List<OperationTimelineEntity> operationTimeline = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_operation_tags", joinColumns = @JoinColumn(name = "mission_id"))
    private List<String> operationTags = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_support_units", joinColumns = @JoinColumn(name = "mission_id"))
    private List<String> supportUnits = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_recommendations", joinColumns = @JoinColumn(name = "mission_id"))
    private List<String> recommendations = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_artifacts", joinColumns = @JoinColumn(name = "mission_id"))
    private List<String> artifactsRecovered = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_evacuation_zones", joinColumns = @JoinColumn(name = "mission_id"))
    private List<String> evacuationZones = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_status_effects", joinColumns = @JoinColumn(name = "mission_id"))
    private List<String> statusEffects = new ArrayList<>();

    public enum Outcome {
        SUCCESS, PARTIAL_SUCCESS, FAILURE
    }

    public MissionEntity() {}

    // Getters and Setters

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Outcome getOutcome() { return outcome; }
    public void setOutcome(Outcome outcome) { this.outcome = outcome; }

    public Long getDamageCost() { return damageCost; }
    public void setDamageCost(Long damageCost) { this.damageCost = damageCost; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note != null ? note : ""; }

    public CurseEntity getCurse() { return curse; }
    public void setCurse(CurseEntity curse) { this.curse = curse; }

    public EconomicAssessmentEntity getEconomicAssessment() { return economicAssessment; }
    public void setEconomicAssessment(EconomicAssessmentEntity economicAssessment) { this.economicAssessment = economicAssessment; }

    public CivilianImpactEntity getCivilianImpact() { return civilianImpact; }
    public void setCivilianImpact(CivilianImpactEntity civilianImpact) { this.civilianImpact = civilianImpact; }

    public EnemyActivityEntity getEnemyActivity() { return enemyActivity; }
    public void setEnemyActivity(EnemyActivityEntity enemyActivity) { this.enemyActivity = enemyActivity; }

    public EnvironmentConditionsEntity getEnvironmentConditions() { return environmentConditions; }
    public void setEnvironmentConditions(EnvironmentConditionsEntity environmentConditions) { this.environmentConditions = environmentConditions; }

    public List<SorcererEntity> getSorcerers() { return sorcerers; }
    public void setSorcerers(List<SorcererEntity> sorcerers) { this.sorcerers = sorcerers != null ? sorcerers : new ArrayList<>(); }

    public List<TechniqueEntity> getTechniques() { return techniques; }
    public void setTechniques(List<TechniqueEntity> techniques) { this.techniques = techniques != null ? techniques : new ArrayList<>(); }

    public List<OperationTimelineEntity> getOperationTimeline() { return operationTimeline; }
    public void setOperationTimeline(List<OperationTimelineEntity> operationTimeline) { 
        this.operationTimeline = operationTimeline != null ? operationTimeline : new ArrayList<>(); 
    }

    public List<String> getOperationTags() { return operationTags; }
    public void setOperationTags(List<String> operationTags) { this.operationTags = operationTags != null ? operationTags : new ArrayList<>(); }

    public List<String> getSupportUnits() { return supportUnits; }
    public void setSupportUnits(List<String> supportUnits) { this.supportUnits = supportUnits != null ? supportUnits : new ArrayList<>(); }

    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations != null ? recommendations : new ArrayList<>(); }

    public List<String> getArtifactsRecovered() { return artifactsRecovered; }
    public void setArtifactsRecovered(List<String> artifactsRecovered) { this.artifactsRecovered = artifactsRecovered != null ? artifactsRecovered : new ArrayList<>(); }

    public List<String> getEvacuationZones() { return evacuationZones; }
    public void setEvacuationZones(List<String> evacuationZones) { this.evacuationZones = evacuationZones != null ? evacuationZones : new ArrayList<>(); }

    public List<String> getStatusEffects() { return statusEffects; }
    public void setStatusEffects(List<String> statusEffects) { this.statusEffects = statusEffects != null ? statusEffects : new ArrayList<>(); }
}