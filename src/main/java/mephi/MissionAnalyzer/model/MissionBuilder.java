package model;

public abstract class MissionBuilder {

    protected Mission mission;
    
    public abstract void createNewMission();

    public abstract void buildMissionId(String missionId);
    public abstract void buildDate(java.time.LocalDate date);
    public abstract void buildLocation(String location);
    public abstract void buildOutcome(Mission.Outcome outcome);
    public abstract void buildDamageCost(long damageCost);
    public abstract void buildNote(String note);

    public abstract void buildCurse(Curse curse);
    public abstract void buildEconomicAssessment(EconomicAssessment economicAssessment);
    public abstract void buildCivilianImpact(CivilianImpact civilianImpact);
    public abstract void buildEnemyActivity(EnemyActivity enemyActivity);
    public abstract void buildEnvironmentConditions(EnvironmentConditions environmentConditions);

    public abstract void addSorcerer(Sorcerer sorcerer);
    public abstract void addTechnique(Technique technique);
    public abstract void addOperationTimeline(OperationTimeline timeline);

    public abstract void addOperationTag(String tag);
    public abstract void addSupportUnit(String unit);
    public abstract void addRecommendation(String rec);
    public abstract void addArtifactRecovered(String artifact);
    public abstract void addEvacuationZone(String zone);
    public abstract void addStatusEffect(String effect);

    public Mission getMission() {
        return mission;
    }
}