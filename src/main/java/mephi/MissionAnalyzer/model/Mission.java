package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)   // игнорирует лишние поля
public class Mission {

    public enum Outcome {
        SUCCESS,
        PARTIAL_SUCCESS,
        FAILURE
    }

    private String missionId;
    private LocalDate date;
    private String location;
    private Outcome outcome;
    private Long damageCost = null;
    private String note = "";
    
    private final List<Sorcerer> sorcerers = new ArrayList<>();
    private final List<Technique> techniques = new ArrayList<>();
    private Curse curse;

    private EconomicAssessment economicAssessment;
    private CivilianImpact civilianImpact;
    private EnemyActivity enemyActivity;
    private EnvironmentConditions environmentConditions;
    private final List<OperationTimeline> operationTimeline = new ArrayList<>();

    private final List<String> operationTags = new ArrayList<>();
    private final List<String> supportUnits = new ArrayList<>();
    private final List<String> recommendations = new ArrayList<>();
    private final List<String> artifactsRecovered = new ArrayList<>();
    private final List<String> evacuationZones = new ArrayList<>();
    private final List<String> statusEffects = new ArrayList<>();
    
    public Mission() {
    }
    
    //геттеры
    public String getMissionId() { return missionId; }
    public LocalDate getDate() { return date; }
    public String getLocation() { return location; }
    public Outcome getOutcome() { return outcome; }
    public Long getDamageCost() { return damageCost; }
    
    @JsonProperty("comment")
    public String getNote() { return note; }
    public Curse getCurse() { return curse; }
    public EconomicAssessment getEconomicAssessment() { return economicAssessment; }
    public CivilianImpact getCivilianImpact() { return civilianImpact; }
    public EnemyActivity getEnemyActivity() { return enemyActivity; }
    public EnvironmentConditions getEnvironmentConditions() { return environmentConditions; }
    
    public List<OperationTimeline> getOperationTimeline() { return new ArrayList<>(operationTimeline); }
    public List<Sorcerer> getSorcerers() { return new ArrayList<>(sorcerers); }
    public List<Technique> getTechniques() { return new ArrayList<>(techniques); }
    
    public int getSorcerersSize() { return sorcerers.size(); }
    public int geTechniqueSize() { return techniques.size(); }
    
    public List<String> getOperationTags() { return new ArrayList<>(operationTags); }
    public List<String> getSupportUnits() { return new ArrayList<>(supportUnits); }
    public List<String> getRecommendations() { return new ArrayList<>(recommendations); }
    public List<String> getArtifactsRecovered() { return new ArrayList<>(artifactsRecovered); }
    public List<String> getEvacuationZones() { return new ArrayList<>(evacuationZones); }
    public List<String> getStatusEffects() { return new ArrayList<>(statusEffects); }

    //сеттеры
    public void setMissionId(String missionId) { this.missionId = missionId; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }
    public void setOutcome(Outcome outcome) { this.outcome = outcome; }
    public void setDamageCost(long damageCost) { this.damageCost = damageCost; }
    public void setNote(String note) { this.note = note; }

    public void setCurse(Curse curse) { this.curse = curse; }
    public void setEconomicAssessment(EconomicAssessment ea) { this.economicAssessment = ea; }
    public void setCivilianImpact(CivilianImpact ci) { this.civilianImpact = ci; }
    public void setEnemyActivity(EnemyActivity ea) { this.enemyActivity = ea; }
    public void setEnvironmentConditions(EnvironmentConditions ec) { this.environmentConditions = ec; }

    public void addSorcerer(Sorcerer s) { if (s != null) this.sorcerers.add(s); }
    public void addTechnique(Technique t) { if (t != null) this.techniques.add(t); }
    public void addOperationTimeline(OperationTimeline t) { if (t != null) this.operationTimeline.add(t); }
    
    public void addOperationTag(String tag) { if (tag != null) this.operationTags.add(tag); }
    public void addSupportUnit(String unit) { if (unit != null) this.supportUnits.add(unit); }
    public void addRecommendation(String rec) { if (rec != null) this.recommendations.add(rec); }
    public void addArtifactRecovered(String artifact) { if (artifact != null) this.artifactsRecovered.add(artifact); }
    public void addEvacuationZone(String zone) { if (zone != null) this.evacuationZones.add(zone); }
    public void addStatusEffect(String effect) { if (effect != null) this.statusEffects.add(effect); }
}
