package model;

public class ConcreteMissionBuilder extends MissionBuilder {

    @Override
    public void createNewMission() {
        mission = new Mission();
    }

    @Override
    public void buildMissionId(String missionId) {
        if (mission != null) mission.setMissionId(missionId);
    }

    @Override
    public void buildDate(java.time.LocalDate date) {
        if (mission != null) mission.setDate(date);
    }

    @Override
    public void buildLocation(String location) {
        if (mission != null) mission.setLocation(location);
    }

    @Override
    public void buildOutcome(Mission.Outcome outcome) {
        if (mission != null) mission.setOutcome(outcome);
    }

    @Override
    public void buildDamageCost(long damageCost) {
        if (mission != null) mission.setDamageCost(damageCost);
    }

    @Override
    public void buildNote(String note) {
        if (mission != null) mission.setNote(note);
    }

    @Override
    public void buildCurse(Curse curse) {
        if (mission != null) mission.setCurse(curse);
    }

    @Override
    public void buildEconomicAssessment(EconomicAssessment economicAssessment) {
        if (mission != null) mission.setEconomicAssessment(economicAssessment);
    }

    @Override
    public void buildCivilianImpact(CivilianImpact civilianImpact) {
        if (mission != null) mission.setCivilianImpact(civilianImpact);
    }

    @Override
    public void buildEnemyActivity(EnemyActivity enemyActivity) {
        if (mission != null) mission.setEnemyActivity(enemyActivity);
    }

    @Override
    public void buildEnvironmentConditions(EnvironmentConditions environmentConditions) {
        if (mission != null) mission.setEnvironmentConditions(environmentConditions);
    }

    @Override
    public void addSorcerer(Sorcerer sorcerer) {
        if (mission != null && sorcerer != null) mission.addSorcerer(sorcerer);
    }

    @Override
    public void addTechnique(Technique technique) {
        if (mission != null && technique != null) mission.addTechnique(technique);
    }

    @Override
    public void addOperationTimeline(OperationTimeline timeline) {
        if (mission != null && timeline != null) mission.addOperationTimeline(timeline);
    }

    @Override
    public void addOperationTag(String tag) {
        if (mission != null && tag != null) mission.addOperationTag(tag);
    }

    @Override
    public void addSupportUnit(String unit) {
        if (mission != null && unit != null) mission.addSupportUnit(unit);
    }

    @Override
    public void addRecommendation(String rec) {
        if (mission != null && rec != null) mission.addRecommendation(rec);
    }

    @Override
    public void addArtifactRecovered(String artifact) {
        if (mission != null && artifact != null) mission.addArtifactRecovered(artifact);
    }

    @Override
    public void addEvacuationZone(String zone) {
        if (mission != null && zone != null) mission.addEvacuationZone(zone);
    }

    @Override
    public void addStatusEffect(String effect) {
        if (mission != null && effect != null) mission.addStatusEffect(effect);
    }

    @Override
    public Mission getMission() {
        return mission;
    }
}