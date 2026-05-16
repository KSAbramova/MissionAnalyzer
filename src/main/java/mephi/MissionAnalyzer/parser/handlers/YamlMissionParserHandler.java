package mephi.MissionAnalyzer.parser.handlers;

import model.*;
import mephi.MissionAnalyzer.parser.BaseMissionParser;
import mephi.MissionAnalyzer.parser.MissionParseException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class YamlMissionParserHandler extends BaseMissionParser {

    private final MissionDirector director = new MissionDirector();

    @Override
    public boolean canHandle(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".yaml") || name.endsWith(".yml");
    }

    @Override
    protected Mission doParse(File file) throws MissionParseException {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(new FileInputStream(file));

            ConcreteMissionBuilder builder = new ConcreteMissionBuilder();

            builder.createNewMission();

            parseBasicFields(data, builder);
            parseCurse(data, builder);
            parseSorcerers(data, builder);
            parseTechniques(data, builder);
            parseEconomicAssessment(data, builder);
            // заглушки
            parseCivilianImpact(data, builder);
            parseEnemyActivity(data, builder);
            parseEnvironmentConditions(data, builder);
            parseOperationTimeline(data, builder);
            parseListFields(data, builder);

            return director.constructFromBuilder(builder);
        } catch (Exception e) {
            throw new MissionParseException("Ошибка при разборе YAML-файла: " + file.getName(), e);
        }
    }

    private LocalDate safeLocalDate(Object dateObj) {
        if (dateObj instanceof LocalDate ld) return ld;
        if (dateObj instanceof Date d) {
            return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (dateObj instanceof String s) {
            return LocalDate.parse(s);
        }
        return null;
    }

    private LocalDateTime safeLocalDateTime(Object dateObj) {
        if (dateObj instanceof LocalDateTime ldt) return ldt;
        if (dateObj instanceof Date d) {
            return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        if (dateObj instanceof String s) {
            return LocalDateTime.parse(s);
        }
        return null;
    }

    // основные поля
    private void parseBasicFields(Map<String, Object> data, ConcreteMissionBuilder builder) {
        if (data.containsKey("missionId")) {
            builder.buildMissionId((String) data.get("missionId"));
        }
        if (data.containsKey("date")) {
            LocalDate date = safeLocalDate(data.get("date"));
            if (date != null) builder.buildDate(date);
        }
        if (data.containsKey("location")) {
            builder.buildLocation((String) data.get("location"));
        }
        if (data.containsKey("outcome")) {
            builder.buildOutcome(Mission.Outcome.valueOf(data.get("outcome").toString().toUpperCase()));
        }
        if (data.containsKey("damageCost")) {
            builder.buildDamageCost(((Number) data.get("damageCost")).longValue());
        }
        if (data.containsKey("note") || data.containsKey("comment")) {
            String note = (String) data.getOrDefault("note", data.get("comment"));
            if (note != null) builder.buildNote(note);
        }
    }

    // curse
    private void parseCurse(Map<String, Object> data, ConcreteMissionBuilder builder) {
        if (data.containsKey("curse")) {
            Map<String, Object> c = (Map<String, Object>) data.get("curse");
            Curse curse = new Curse();
            if (c.containsKey("name")) curse.setName((String) c.get("name"));
            if (c.containsKey("threatLevel")) {
                curse.setThreatLevel(Curse.ThreatLevel.valueOf(c.get("threatLevel").toString().toUpperCase()));
            }
            builder.buildCurse(curse);
        }
    }

    // sorcerers
    private void parseSorcerers(Map<String, Object> data, ConcreteMissionBuilder builder) {
        if (data.containsKey("sorcerers")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("sorcerers");
            for (Map<String, Object> s : list) {
                Sorcerer sorcerer = new Sorcerer();
                if (s.containsKey("name")) sorcerer.setName((String) s.get("name"));
                if (s.containsKey("rank")) {
                    sorcerer.setRank(Sorcerer.Rank.valueOf(s.get("rank").toString().toUpperCase()));
                }
                builder.addSorcerer(sorcerer);
            }
        }
    }

    // techniques
    private void parseTechniques(Map<String, Object> data, ConcreteMissionBuilder builder) {
        if (data.containsKey("techniques")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("techniques");
            for (Map<String, Object> t : list) {
                Technique tech = new Technique();
                if (t.containsKey("name")) tech.setName((String) t.get("name"));
                if (t.containsKey("type")) tech.setType(Technique.Type.valueOf(t.get("type").toString().toUpperCase()));
                if (t.containsKey("owner")) tech.setOwner((String) t.get("owner"));
                if (t.containsKey("damage")) {
                    tech.setDamage(((Number) t.get("damage")).longValue());
                }
                builder.addTechnique(tech);
            }
        }
    }

    // economicAssessment
    private void parseEconomicAssessment(Map<String, Object> data, ConcreteMissionBuilder builder) {
        if (data.containsKey("economicAssessment")) {
            Map<String, Object> eaMap = (Map<String, Object>) data.get("economicAssessment");
            EconomicAssessment ea = new EconomicAssessment();

            if (eaMap.containsKey("totalDamageCost"))
                ea.setTotalDamageCost(((Number) eaMap.get("totalDamageCost")).longValue());
            if (eaMap.containsKey("infrastructureDamage"))
                ea.setInfrastructureDamage(((Number) eaMap.get("infrastructureDamage")).longValue());
            if (eaMap.containsKey("transportDamage"))
                ea.setTransportDamage(((Number) eaMap.get("transportDamage")).longValue());
            if (eaMap.containsKey("commercialDamage"))
                ea.setCommercialDamage(((Number) eaMap.get("commercialDamage")).longValue());
            if (eaMap.containsKey("recoveryEstimateDays"))
                ea.setRecoveryEstimateDays((Integer) eaMap.get("recoveryEstimateDays"));
            if (eaMap.containsKey("insuranceCovered"))
                ea.setInsuranceCovered((Boolean) eaMap.get("insuranceCovered"));

            builder.buildEconomicAssessment(ea);
        }
    }

    private void parseCivilianImpact(Map<String, Object> data, ConcreteMissionBuilder builder) {
        if (data.containsKey("civilianImpact")) {
            Map<String, Object> ciMap = (Map<String, Object>) data.get("civilianImpact");
            CivilianImpact ci = new CivilianImpact();
            if (ciMap.containsKey("evacuated")) ci.setEvacuated(((Number) ciMap.get("evacuated")).intValue());
            if (ciMap.containsKey("injured")) ci.setInjured(((Number) ciMap.get("injured")).intValue());
            if (ciMap.containsKey("missing")) ci.setMissing(((Number) ciMap.get("missing")).intValue());
            if (ciMap.containsKey("publicExposureRisk")) ci.setPublicExposureRisk(ciMap.get("publicExposureRisk").toString());
            builder.buildCivilianImpact(ci);
        }
    }

    private void parseEnemyActivity(Map<String, Object> data, ConcreteMissionBuilder builder) {
        if (data.containsKey("enemyActivity")) {
            Map<String, Object> eaMap = (Map<String, Object>) data.get("enemyActivity");
            EnemyActivity ea = new EnemyActivity();
            if (eaMap.containsKey("behaviorType")) ea.setBehaviorType(eaMap.get("behaviorType").toString());
            if (eaMap.containsKey("mobility")) ea.setMobility(eaMap.get("mobility").toString());
            if (eaMap.containsKey("escalationRisk")) ea.setEscalationRisk(eaMap.get("escalationRisk").toString());
            parseStringList(eaMap, "targetPriority", ea::addTargetPriority);
            parseStringList(eaMap, "attackPatterns", ea::addAttackPattern);
            builder.buildEnemyActivity(ea);
        }
    }

    private void parseEnvironmentConditions(Map<String, Object> data, ConcreteMissionBuilder builder) {
        Object value = data.getOrDefault("environmentConditions", data.get("environment"));
        if (value instanceof Map) {
            Map<String, Object> ecMap = (Map<String, Object>) value;
            EnvironmentConditions ec = new EnvironmentConditions();
            if (ecMap.containsKey("weather")) ec.setWeather(ecMap.get("weather").toString());
            if (ecMap.containsKey("timeOfDay")) ec.setTimeOfDay(ecMap.get("timeOfDay").toString());
            if (ecMap.containsKey("visibility")) ec.setVisibility(ecMap.get("visibility").toString());
            if (ecMap.containsKey("cursedEnergyDensity")) {
                ec.setCursedEnergyDensity(((Number) ecMap.get("cursedEnergyDensity")).doubleValue());
            }
            builder.buildEnvironmentConditions(ec);
        }
    }

    private void parseOperationTimeline(Map<String, Object> data, ConcreteMissionBuilder builder) {
        Object value = data.getOrDefault("operationTimeline", data.get("timeline"));
        if (value instanceof List) {
            for (Object item : (List<?>) value) {
                if (item instanceof Map) {
                    builder.addOperationTimeline(toOperationTimeline((Map<String, Object>) item));
                }
            }
        } else if (value instanceof Map) {
            builder.addOperationTimeline(toOperationTimeline((Map<String, Object>) value));
        }
    }

    private OperationTimeline toOperationTimeline(Map<String, Object> timelineMap) {
        OperationTimeline timeline = new OperationTimeline();
        Object timestamp = timelineMap.getOrDefault("timestamp", timelineMap.get("timestamps"));
        LocalDateTime parsedTimestamp = safeLocalDateTime(timestamp);
        if (parsedTimestamp != null) timeline.setTimestamp(parsedTimestamp);
        if (timelineMap.containsKey("type")) timeline.setType(timelineMap.get("type").toString());
        if (timelineMap.containsKey("description")) timeline.setDescription(timelineMap.get("description").toString());
        return timeline;
    }

    private void parseListFields(Map<String, Object> data, ConcreteMissionBuilder builder) {
        parseStringList(data, "operationTags", builder::addOperationTag);
        parseStringList(data, "supportUnits", builder::addSupportUnit);
        parseStringList(data, "recommendations", builder::addRecommendation);
        parseStringList(data, "artifactsRecovered", builder::addArtifactRecovered);
        parseStringList(data, "evacuationZones", builder::addEvacuationZone);
        parseStringList(data, "statusEffects", builder::addStatusEffect);
    }

    private void parseStringList(Map<String, Object> data, String key, java.util.function.Consumer<String> adder) {
        if (data.containsKey(key)) {
            Object value = data.get(key);
            if (value instanceof List) {
                for (Object item : (List<?>) value) {
                    if (item != null) adder.accept(item.toString());
                }
            } else if (value != null) {
                adder.accept(value.toString());
            }
        }
    }
}
