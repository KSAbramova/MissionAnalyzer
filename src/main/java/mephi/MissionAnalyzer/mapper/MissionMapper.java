package mephi.MissionAnalyzer.mapper;

import mephi.MissionAnalyzer.entity.*;
import model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MissionMapper {

    // Mission → MissionEntity
    public MissionEntity toEntity(Mission mission) {
        if (mission == null) return null;

        MissionEntity entity = new MissionEntity();

        entity.setMissionId(mission.getMissionId());
        entity.setDate(mission.getDate());
        entity.setLocation(mission.getLocation());
        entity.setOutcome(convertToEntityOutcome(mission.getOutcome()));
        entity.setNote(mission.getNote());
        entity.setDamageCost(mission.getDamageCost() != null ? mission.getDamageCost() : 0L);

        entity.setCurse(toCurseEntity(mission.getCurse()));
        entity.setEconomicAssessment(toEconomicAssessmentEntity(mission.getEconomicAssessment()));
        entity.setCivilianImpact(toCivilianImpactEntity(mission.getCivilianImpact()));
        entity.setEnemyActivity(toEnemyActivityEntity(mission.getEnemyActivity()));
        entity.setEnvironmentConditions(toEnvironmentConditionsEntity(mission.getEnvironmentConditions()));

        entity.setSorcerers(toSorcererEntityList(mission.getSorcerers()));
        entity.setTechniques(toTechniqueEntityList(mission.getTechniques()));
        entity.setOperationTimeline(toOperationTimelineEntityList(mission.getOperationTimeline()));

        entity.setOperationTags(copyList(mission.getOperationTags()));
        entity.setSupportUnits(copyList(mission.getSupportUnits()));
        entity.setRecommendations(copyList(mission.getRecommendations()));
        entity.setArtifactsRecovered(copyList(mission.getArtifactsRecovered()));
        entity.setEvacuationZones(copyList(mission.getEvacuationZones()));
        entity.setStatusEffects(copyList(mission.getStatusEffects()));

        return entity;
    }

    // MissionEntity → Mission 
    public Mission toModel(MissionEntity entity) {
        if (entity == null) return null;

        Mission mission = new Mission();

        mission.setMissionId(entity.getMissionId());
        mission.setDate(entity.getDate());
        mission.setLocation(entity.getLocation());
        mission.setOutcome(convertToModelOutcome(entity.getOutcome()));
        mission.setNote(entity.getNote());

        mission.setDamageCost(entity.getDamageCost() != null ? entity.getDamageCost() : 0L);

        mission.setCurse(toCurseModel(entity.getCurse()));
        mission.setEconomicAssessment(toEconomicAssessmentModel(entity.getEconomicAssessment()));
        mission.setCivilianImpact(toCivilianImpactModel(entity.getCivilianImpact()));
        mission.setEnemyActivity(toEnemyActivityModel(entity.getEnemyActivity()));
        mission.setEnvironmentConditions(toEnvironmentConditionsModel(entity.getEnvironmentConditions()));

        if (entity.getSorcerers() != null) {
            entity.getSorcerers().forEach(s -> mission.addSorcerer(toSorcererModel(s)));
        }
        if (entity.getTechniques() != null) {
            entity.getTechniques().forEach(t -> mission.addTechnique(toTechniqueModel(t)));
        }
        if (entity.getOperationTimeline() != null) {
            entity.getOperationTimeline().forEach(t -> mission.addOperationTimeline(toOperationTimelineModel(t)));
        }

        copyStringList(entity.getOperationTags(), mission::addOperationTag);
        copyStringList(entity.getSupportUnits(), mission::addSupportUnit);
        copyStringList(entity.getRecommendations(), mission::addRecommendation);
        copyStringList(entity.getArtifactsRecovered(), mission::addArtifactRecovered);
        copyStringList(entity.getEvacuationZones(), mission::addEvacuationZone);
        copyStringList(entity.getStatusEffects(), mission::addStatusEffect);

        return mission;
    }

    // Вспомогательные методы 

    private MissionEntity.Outcome convertToEntityOutcome(Mission.Outcome outcome) {
        return outcome == null ? null : MissionEntity.Outcome.valueOf(outcome.name());
    }

    private Mission.Outcome convertToModelOutcome(MissionEntity.Outcome outcome) {
        return outcome == null ? null : Mission.Outcome.valueOf(outcome.name());
    }

    private <T> List<T> copyList(List<T> source) {
        return source == null ? List.of() : List.copyOf(source);
    }

    private void copyStringList(List<String> source, java.util.function.Consumer<String> adder) {
        if (source != null) {
            source.forEach(adder);
        }
    }

    // Конвертеры отдельных сущностей

    private CurseEntity toCurseEntity(Curse curse) {
        if (curse == null) return null;
        CurseEntity e = new CurseEntity();
        e.setName(curse.getName());
        if (curse.getThreatLevel() != null) {
            e.setThreatLevel(CurseEntity.ThreatLevel.valueOf(curse.getThreatLevel().name()));
        }
        return e;
    }

    private Curse toCurseModel(CurseEntity entity) {
        if (entity == null) return null;
        Curse curse = new Curse();
        curse.setName(entity.getName());
        if (entity.getThreatLevel() != null) {
            curse.setThreatLevel(Curse.ThreatLevel.valueOf(entity.getThreatLevel().name()));
        }
        return curse;
    }

    private SorcererEntity toSorcererEntity(Sorcerer s) {
        if (s == null) return null;
        SorcererEntity e = new SorcererEntity();
        e.setName(s.getName());
        if (s.getRank() != null) {
            e.setRank(SorcererEntity.Rank.valueOf(s.getRank().name()));
        }
        return e;
    }

    private Sorcerer toSorcererModel(SorcererEntity e) {
        if (e == null) return null;
        Sorcerer s = new Sorcerer();
        s.setName(e.getName());
        if (e.getRank() != null) {
            s.setRank(Sorcerer.Rank.valueOf(e.getRank().name()));
        }
        return s;
    }

    private TechniqueEntity toTechniqueEntity(Technique t) {
        if (t == null) return null;
        TechniqueEntity e = new TechniqueEntity();
        e.setName(t.getName());
        if (t.getType() != null) {
            e.setType(TechniqueEntity.Type.valueOf(t.getType().name()));
        }
        e.setOwner(t.getOwner());
        e.setDamage(t.getDamage() != null ? t.getDamage() : 0L);
        return e;
    }

    private Technique toTechniqueModel(TechniqueEntity e) {
        if (e == null) return null;
        Technique t = new Technique();
        t.setName(e.getName());
        if (e.getType() != null) {
            t.setType(Technique.Type.valueOf(e.getType().name()));
        }
        t.setOwner(e.getOwner());
        t.setDamage(e.getDamage() != null ? e.getDamage() : 0L);
        return t;
    }

    private EconomicAssessmentEntity toEconomicAssessmentEntity(EconomicAssessment ea) {
        if (ea == null) return null;
        EconomicAssessmentEntity e = new EconomicAssessmentEntity();
        e.setTotalDamageCost(ea.getTotalDamageCost() != null ? ea.getTotalDamageCost() : 0L);
        e.setInfrastructureDamage(ea.getInfrastructureDamage() != null ? ea.getInfrastructureDamage() : 0L);
        e.setCommercialDamage(ea.getCommercialDamage() != null ? ea.getCommercialDamage() : 0L);
        e.setTransportDamage(ea.getTransportDamage() != null ? ea.getTransportDamage() : 0L);
        e.setRecoveryEstimateDays(ea.getRecoveryEstimateDays() != null ? ea.getRecoveryEstimateDays() : 0);
        e.setInsuranceCovered(ea.isInsuranceCovered() != null && ea.isInsuranceCovered());
        return e;
    }

    private EconomicAssessment toEconomicAssessmentModel(EconomicAssessmentEntity e) {
        if (e == null) return null;
        EconomicAssessment ea = new EconomicAssessment();
        ea.setTotalDamageCost(e.getTotalDamageCost() != null ? e.getTotalDamageCost() : 0L);
        ea.setInfrastructureDamage(e.getInfrastructureDamage() != null ? e.getInfrastructureDamage() : 0L);
        ea.setCommercialDamage(e.getCommercialDamage() != null ? e.getCommercialDamage() : 0L);
        ea.setTransportDamage(e.getTransportDamage() != null ? e.getTransportDamage() : 0L);
        ea.setRecoveryEstimateDays(e.getRecoveryEstimateDays() != null ? e.getRecoveryEstimateDays() : 0);
        ea.setInsuranceCovered(e.getInsuranceCovered() != null && e.getInsuranceCovered());
        return ea;
    }

    private CivilianImpactEntity toCivilianImpactEntity(CivilianImpact ci) {
        if (ci == null) return null;
        CivilianImpactEntity e = new CivilianImpactEntity();
        e.setEvacuated(ci.getEvacuated() != null ? ci.getEvacuated() : 0);
        e.setInjured(ci.getInjured() != null ? ci.getInjured() : 0);
        e.setMissing(ci.getMissing() != null ? ci.getMissing() : 0);
        e.setPublicExposureRisk(ci.getPublicExposureRisk());
        return e;
    }

    private CivilianImpact toCivilianImpactModel(CivilianImpactEntity e) {
        if (e == null) return null;
        CivilianImpact ci = new CivilianImpact();
        ci.setEvacuated(e.getEvacuated() != null ? e.getEvacuated() : 0);
        ci.setInjured(e.getInjured() != null ? e.getInjured() : 0);
        ci.setMissing(e.getMissing() != null ? e.getMissing() : 0);
        ci.setPublicExposureRisk(e.getPublicExposureRisk());
        return ci;
    }

    private EnemyActivityEntity toEnemyActivityEntity(EnemyActivity ea) {
        if (ea == null) return null;
        EnemyActivityEntity e = new EnemyActivityEntity();
        e.setBehaviorType(ea.getBehaviorType());
        e.setMobility(ea.getMobility());
        e.setEscalationRisk(ea.getEscalationRisk());
        e.setTargetPriority(copyList(ea.getTargetPriority()));
        e.setAttackPatterns(copyList(ea.getAttackPatterns()));
        return e;
    }

    private EnemyActivity toEnemyActivityModel(EnemyActivityEntity e) {
        if (e == null) return null;
        EnemyActivity ea = new EnemyActivity();
        ea.setBehaviorType(e.getBehaviorType());
        ea.setMobility(e.getMobility());
        ea.setEscalationRisk(e.getEscalationRisk());

        if (e.getTargetPriority() != null) e.getTargetPriority().forEach(ea::addTargetPriority);
        if (e.getAttackPatterns() != null) e.getAttackPatterns().forEach(ea::addAttackPattern);

        return ea;
    }

    private EnvironmentConditionsEntity toEnvironmentConditionsEntity(EnvironmentConditions ec) {
        if (ec == null) return null;
        EnvironmentConditionsEntity e = new EnvironmentConditionsEntity();
        e.setWeather(ec.getWeather());
        e.setTimeOfDay(ec.getTimeOfDay());
        e.setVisibility(ec.getVisibility());
        e.setCursedEnergyDensity(ec.getCursedEnergyDensity());
        return e;
    }

    private EnvironmentConditions toEnvironmentConditionsModel(EnvironmentConditionsEntity e) {
        if (e == null) return null;
        EnvironmentConditions ec = new EnvironmentConditions();
        ec.setWeather(e.getWeather());
        ec.setTimeOfDay(e.getTimeOfDay());
        ec.setVisibility(e.getVisibility());
        ec.setCursedEnergyDensity(e.getCursedEnergyDensity() != null ? e.getCursedEnergyDensity() : 0.0);
        return ec;
    }

    private OperationTimelineEntity toOperationTimelineEntity(OperationTimeline ot) {
        if (ot == null) return null;
        OperationTimelineEntity e = new OperationTimelineEntity();
        e.setTimestamp(ot.getTimestamp());
        e.setType(ot.getType());
        e.setDescription(ot.getDescription());
        return e;
    }

    private OperationTimeline toOperationTimelineModel(OperationTimelineEntity e) {
        if (e == null) return null;
        OperationTimeline ot = new OperationTimeline();
        ot.setTimestamp(e.getTimestamp());
        ot.setType(e.getType());
        ot.setDescription(e.getDescription());
        return ot;
    }

    // List конвертеры

    private List<SorcererEntity> toSorcererEntityList(List<Sorcerer> list) {
        return list == null ? List.of() : list.stream().map(this::toSorcererEntity).collect(Collectors.toList());
    }

    private List<TechniqueEntity> toTechniqueEntityList(List<Technique> list) {
        return list == null ? List.of() : list.stream().map(this::toTechniqueEntity).collect(Collectors.toList());
    }

    private List<OperationTimelineEntity> toOperationTimelineEntityList(List<OperationTimeline> list) {
        return list == null ? List.of() : list.stream().map(this::toOperationTimelineEntity).collect(Collectors.toList());
    }
}