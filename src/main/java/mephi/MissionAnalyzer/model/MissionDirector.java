package model;

public class MissionDirector {

    public Mission constructMission(Mission missionFromJson) {
        ConcreteMissionBuilder builder = new ConcreteMissionBuilder();
        return constructMission(builder, missionFromJson);
    }

    public Mission constructMission(MissionBuilder builder, Mission source) {
        builder.createNewMission();

        builder.buildMissionId(source.getMissionId());
        builder.buildDate(source.getDate());
        builder.buildLocation(source.getLocation());
        builder.buildOutcome(source.getOutcome());
        builder.buildCurse(source.getCurse());

        builder.buildDamageCost(source.getDamageCost());
        builder.buildNote(source.getNote());

        builder.buildEconomicAssessment(source.getEconomicAssessment());
        builder.buildCivilianImpact(source.getCivilianImpact());
        builder.buildEnemyActivity(source.getEnemyActivity());
        builder.buildEnvironmentConditions(source.getEnvironmentConditions());

        source.getSorcerers().forEach(builder::addSorcerer);
        source.getTechniques().forEach(builder::addTechnique);
        source.getOperationTimeline().forEach(builder::addOperationTimeline);
        source.getOperationTags().forEach(builder::addOperationTag);
        source.getSupportUnits().forEach(builder::addSupportUnit);
        source.getRecommendations().forEach(builder::addRecommendation);
        source.getArtifactsRecovered().forEach(builder::addArtifactRecovered);
        source.getEvacuationZones().forEach(builder::addEvacuationZone);
        source.getStatusEffects().forEach(builder::addStatusEffect);

        return builder.getMission();
    }
    
    public Mission constructFromBuilder(MissionBuilder builder) {
        return builder.getMission();
    }
}