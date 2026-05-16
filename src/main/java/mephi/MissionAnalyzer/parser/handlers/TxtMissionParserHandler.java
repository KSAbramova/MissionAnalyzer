package mephi.MissionAnalyzer.parser.handlers;

import model.*;
import mephi.MissionAnalyzer.parser.BaseMissionParser;
import mephi.MissionAnalyzer.parser.MissionParseException;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TxtMissionParserHandler extends BaseMissionParser {

    private final MissionDirector director = new MissionDirector();

    @Override
    public boolean canHandle(File file) {
        return file.getName().toLowerCase().endsWith(".txt");
    }

    @Override
    protected Mission doParse(File file) throws MissionParseException {
        try {
            ConcreteMissionBuilder builder = new ConcreteMissionBuilder();
            builder.createNewMission();

            String currentSection = "";
            Map<String, Object> currentObjects = new HashMap<>();

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;

                    if (line.startsWith("[") && line.endsWith("]")) {
                        currentSection = line.substring(1, line.length() - 1).trim().toUpperCase();
                        startNewSection(currentSection, builder, currentObjects);
                        continue;
                    }

                    if (line.contains("=")) {
                        handleSectionLine(currentSection, line, builder, currentObjects);
                    }
                }
            }

            return director.constructFromBuilder(builder);

        } catch (Exception e) {
            throw new MissionParseException("Ошибка при разборе TXT-файла: " + file.getName(), e);
        }
    }

    private void startNewSection(String section, ConcreteMissionBuilder builder, Map<String, Object> currentObjects) {
        switch (section) {
            case "SORCERER" -> {
                Sorcerer s = new Sorcerer();
                builder.addSorcerer(s);
                currentObjects.put("SORCERER", s);
            }
            case "TECHNIQUE" -> {
                Technique t = new Technique();
                builder.addTechnique(t);
                currentObjects.put("TECHNIQUE", t);
            }
            case "CURSE" -> {
                Curse curse = new Curse();
                builder.buildCurse(curse);
                currentObjects.put("CURSE", curse);
            }
            case "ENVIRONMENT", "ENVIRONMENTCONDITIONS" -> {
                EnvironmentConditions env = new EnvironmentConditions();
                builder.buildEnvironmentConditions(env);
                currentObjects.put("ENVIRONMENT", env);
            }
            case "ECONOMICASSESSMENT", "ECONOMIC" -> {
                EconomicAssessment assessment = new EconomicAssessment();
                builder.buildEconomicAssessment(assessment);
                currentObjects.put("ECONOMIC", assessment);
            }
            case "CIVILIANIMPACT", "CIVILIAN" -> {
                CivilianImpact impact = new CivilianImpact();
                builder.buildCivilianImpact(impact);
                currentObjects.put("CIVILIAN", impact);
            }
            case "ENEMYACTIVITY", "ENEMY" -> {
                EnemyActivity activity = new EnemyActivity();
                builder.buildEnemyActivity(activity);
                currentObjects.put("ENEMY", activity);
            }
            case "TIMELINE" -> {
                OperationTimeline timeline = new OperationTimeline();
                builder.addOperationTimeline(timeline);
                currentObjects.put("TIMELINE", timeline);
            }
        }
    }

    private void handleSectionLine(String section, String line, ConcreteMissionBuilder builder,
                                   Map<String, Object> currentObjects) {
        String[] parts = line.split("=", 2);
        if (parts.length < 2) return;

        String key = parts[0].trim();
        String value = parts[1].trim();

        switch (section) {
            case "MISSION"     -> parseMissionSection(key, value, builder);
            case "CURSE"       -> parseCurseSection(key, value, currentObjects);
            case "SORCERER"    -> parseSorcererSection(key, value, currentObjects);
            case "TECHNIQUE"   -> parseTechniqueSection(key, value, currentObjects);
            case "ENVIRONMENT", "ENVIRONMENTCONDITIONS" -> parseEnvironmentConditionsSection(key, value, currentObjects);

            case "ECONOMICASSESSMENT", "ECONOMIC" -> parseEconomicAssessmentSection(key, value, currentObjects);
            case "CIVILIANIMPACT", "CIVILIAN"     -> parseCivilianImpactSection(key, value, currentObjects);
            case "ENEMYACTIVITY", "ENEMY"         -> parseEnemyActivitySection(key, value, currentObjects);
            case "TIMELINE"                       -> parseOperationTimelineSection(key, value, currentObjects);

            case "OPERATIONTAGS"   -> parseListSection(key, value, "OPERATIONTAGS", builder);
            case "SUPPORTUNITS"    -> parseListSection(key, value, "SUPPORTUNITS", builder);
            case "RECOMMENDATIONS" -> parseListSection(key, value, "RECOMMENDATIONS", builder);
            case "ARTIFACTSRECOVERED" -> parseListSection(key, value, "ARTIFACTSRECOVERED", builder);
            case "EVACUATIONZONES" -> parseListSection(key, value, "EVACUATIONZONES", builder);
            case "STATUSEFFECTS"   -> parseListSection(key, value, "STATUSEFFECTS", builder);
        }
    }

    private void parseMissionSection(String key, String value, ConcreteMissionBuilder builder) {
        switch (key) {
            case "missionId" -> builder.buildMissionId(value);
            case "date"      -> builder.buildDate(LocalDate.parse(value));
            case "location"  -> builder.buildLocation(value);
            case "outcome"   -> builder.buildOutcome(Mission.Outcome.valueOf(value.toUpperCase()));
            case "damageCost"-> builder.buildDamageCost(Long.parseLong(value));
            case "note", "comment" -> builder.buildNote(value);
        }
    }

    private void parseCurseSection(String key, String value, Map<String, Object> currentObjects) {
        Curse curse = (Curse) currentObjects.get("CURSE");
        if (curse == null) return;
        switch (key) {
            case "name"        -> curse.setName(value);
            case "threatLevel" -> {
                try { curse.setThreatLevel(Curse.ThreatLevel.valueOf(value.toUpperCase())); }
                catch (Exception ignored) {}
            }
        }
    }

    private void parseSorcererSection(String key, String value, Map<String, Object> currentObjects) {
        Sorcerer s = (Sorcerer) currentObjects.get("SORCERER");
        if (s == null) return;
        switch (key) {
            case "name" -> s.setName(value);
            case "rank" -> {
                try { s.setRank(Sorcerer.Rank.valueOf(value.toUpperCase())); }
                catch (Exception ignored) {}
            }
        }
    }

    private void parseTechniqueSection(String key, String value, Map<String, Object> currentObjects) {
        Technique t = (Technique) currentObjects.get("TECHNIQUE");
        if (t == null) return;
        switch (key) {
            case "name"  -> t.setName(value);
            case "type"  -> {
                try { t.setType(Technique.Type.valueOf(value.toUpperCase())); }
                catch (Exception ignored) {}
            }
            case "owner" -> t.setOwner(value);
            case "damage"-> t.setDamage(Long.parseLong(value));
        }
    }

    private void parseEnvironmentConditionsSection(String key, String value, Map<String, Object> currentObjects) {
        EnvironmentConditions env = (EnvironmentConditions) currentObjects.get("ENVIRONMENT");
        if (env == null) return;
        switch (key) {
            case "weather"             -> env.setWeather(value);
            case "timeOfDay"           -> env.setTimeOfDay(value);
            case "visibility"          -> env.setVisibility(value);
            case "cursedEnergyDensity" -> {
                try { env.setCursedEnergyDensity(Double.parseDouble(value)); }
                catch (Exception ignored) {}
            }
        }
    }

    private void parseListSection(String key, String value, String listType, ConcreteMissionBuilder builder) {
        switch (listType) {
            case "OPERATIONTAGS"     -> builder.addOperationTag(value);
            case "SUPPORTUNITS"      -> builder.addSupportUnit(value);
            case "RECOMMENDATIONS"   -> builder.addRecommendation(value);
            case "ARTIFACTSRECOVERED"-> builder.addArtifactRecovered(value);
            case "EVACUATIONZONES"   -> builder.addEvacuationZone(value);
            case "STATUSEFFECTS"     -> builder.addStatusEffect(value);
        }
    }

    private void parseEconomicAssessmentSection(String key, String value, Map<String, Object> currentObjects) {
        EconomicAssessment assessment = (EconomicAssessment) currentObjects.get("ECONOMIC");
        if (assessment == null) return;
        try {
            switch (key) {
                case "totalDamageCost" -> assessment.setTotalDamageCost(Long.parseLong(value));
                case "infrastructureDamage" -> assessment.setInfrastructureDamage(Long.parseLong(value));
                case "commercialDamage" -> assessment.setCommercialDamage(Long.parseLong(value));
                case "transportDamage" -> assessment.setTransportDamage(Long.parseLong(value));
                case "recoveryEstimateDays" -> assessment.setRecoveryEstimateDays(Integer.parseInt(value));
                case "insuranceCovered" -> assessment.setInsuranceCovered(Boolean.parseBoolean(value));
            }
        } catch (Exception ignored) {}
    }

    private void parseCivilianImpactSection(String key, String value, Map<String, Object> currentObjects) {
        CivilianImpact impact = (CivilianImpact) currentObjects.get("CIVILIAN");
        if (impact == null) return;
        try {
            switch (key) {
                case "evacuated" -> impact.setEvacuated(Integer.parseInt(value));
                case "injured" -> impact.setInjured(Integer.parseInt(value));
                case "missing" -> impact.setMissing(Integer.parseInt(value));
                case "publicExposureRisk" -> impact.setPublicExposureRisk(value);
            }
        } catch (Exception ignored) {}
    }

    private void parseEnemyActivitySection(String key, String value, Map<String, Object> currentObjects) {
        EnemyActivity activity = (EnemyActivity) currentObjects.get("ENEMY");
        if (activity == null) return;
        switch (key) {
            case "behaviorType" -> activity.setBehaviorType(value);
            case "mobility" -> activity.setMobility(value);
            case "escalationRisk" -> activity.setEscalationRisk(value);
            case "targetPriority" -> activity.addTargetPriority(value);
            case "attackPatterns", "attackPattern" -> activity.addAttackPattern(value);
        }
    }

    private void parseOperationTimelineSection(String key, String value, Map<String, Object> currentObjects) {
        OperationTimeline timeline = (OperationTimeline) currentObjects.get("TIMELINE");
        if (timeline == null) return;
        try {
            switch (key) {
                case "timestamp", "timestamps" -> timeline.setTimestamp(LocalDateTime.parse(value));
                case "type" -> timeline.setType(value);
                case "description" -> timeline.setDescription(value);
            }
        } catch (Exception ignored) {}
    }
}
