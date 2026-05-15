package model;

import java.util.ArrayList;
import java.util.List;

public class EnemyActivity {
    private String behaviorType;
    private final List<String> targetPriority = new ArrayList<>();
    private final List<String> attackPatterns = new ArrayList<>();
    private String mobility;
    private String escalationRisk;

    public EnemyActivity() {}

    public String getBehaviorType() { return behaviorType; }
    public List<String> getTargetPriority() { return new ArrayList<>(targetPriority); }
    public List<String> getAttackPatterns() { return new ArrayList<>(attackPatterns); }
    public String getMobility() { return mobility; }
    public String getEscalationRisk() { return escalationRisk; }

    public void setBehaviorType(String behaviorType) { this.behaviorType = behaviorType; }
    public void addTargetPriority(String priority) { if (priority != null) this.targetPriority.add(priority); }
    public void addAttackPattern(String pattern) { if (pattern != null) this.attackPatterns.add(pattern); }
    public void setMobility(String mobility) { this.mobility = mobility; }
    public void setEscalationRisk(String escalationRisk) { this.escalationRisk = escalationRisk; }
}