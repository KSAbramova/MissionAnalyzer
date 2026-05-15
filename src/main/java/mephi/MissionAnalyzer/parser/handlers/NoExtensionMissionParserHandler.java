package mephi.MissionAnalyzer.parser.handlers;

import model.*;
import mephi.MissionAnalyzer.parser.BaseMissionParser;
import mephi.MissionAnalyzer.parser.MissionParseException;

import java.io.*;
import java.time.LocalDate;

public class NoExtensionMissionParserHandler extends BaseMissionParser {

    private final MissionDirector director = new MissionDirector();
    
    @Override
    public boolean canHandle(File file) {
        String name = file.getName().toLowerCase();
        return !name.contains(".");
    }

    @Override
    protected Mission doParse(File file) throws MissionParseException {
        try {
            ConcreteMissionBuilder builder = new ConcreteMissionBuilder();
            builder.createNewMission();

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    String[] parts = line.split("\\|");
                    if (parts.length < 2) continue;

                    String type = parts[0].trim();

                    parseCurrentLine(type, parts, builder);
                }
            }

            return director.constructFromBuilder(builder);

        } catch (Exception e) {
            throw new MissionParseException("Ошибка при разборе файла без формата: " + file.getName(), e);
        }
    }

    private void parseCurrentLine(String type, String[] parts, ConcreteMissionBuilder builder) {
        try {
            switch (type) {
                case "MISSION_CREATED"   -> parseMissionCreated(parts, builder);
                case "CURSE_DETECTED"    -> parseCurseDetected(parts, builder);
                case "SORCERER_ASSIGNED" -> parseSorcererAssigned(parts, builder);
                case "TECHNIQUE_USED"    -> parseTechniqueUsed(parts, builder);
                case "MISSION_RESULT"    -> parseMissionResult(parts, builder);

                case "OPERATION_TAG"      -> parseOperationTag(parts, builder);
                case "SUPPORT_UNIT"       -> parseSupportUnit(parts, builder);
                case "RECOMMENDATION"     -> parseRecommendation(parts, builder);
                case "ARTIFACT_RECOVERED" -> parseArtifactRecovered(parts, builder);
                case "EVACUATION_ZONE"    -> parseEvacuationZone(parts, builder);
                case "STATUS_EFFECT"      -> parseStatusEffect(parts, builder);

            }
        } catch (Exception e) {
        }
    }
    
    private void parseMissionCreated(String[] parts, ConcreteMissionBuilder builder) {
        if (parts.length > 1) builder.buildMissionId(parts[1]);
        if (parts.length > 2) {
            try { builder.buildDate(LocalDate.parse(parts[2])); }
            catch (Exception ignored) {}
        }
        if (parts.length > 3) builder.buildLocation(parts[3]);
    }

    private void parseCurseDetected(String[] parts, ConcreteMissionBuilder builder) {
        Curse curse = new Curse();
        if (parts.length > 1) curse.setName(parts[1]);
        if (parts.length > 2) {
            try { curse.setThreatLevel(Curse.ThreatLevel.valueOf(parts[2])); }
            catch (Exception ignored) {}
        }
        builder.buildCurse(curse);
    }

    private void parseSorcererAssigned(String[] parts, ConcreteMissionBuilder builder) {
        Sorcerer s = new Sorcerer();
        if (parts.length > 1) s.setName(parts[1]);
        if (parts.length > 2) {
            try { s.setRank(Sorcerer.Rank.valueOf(parts[2])); }
            catch (Exception ignored) {}
        }
        builder.addSorcerer(s);
    }

    private void parseTechniqueUsed(String[] parts, ConcreteMissionBuilder builder) {
        Technique t = new Technique();
        if (parts.length > 1) t.setName(parts[1]);
        if (parts.length > 2) {
            try { t.setType(Technique.Type.valueOf(parts[2])); }
            catch (Exception ignored) {}
        }
        if (parts.length > 3) t.setOwner(parts[3]);
        if (parts.length > 4) {
            try { t.setDamage(Long.parseLong(parts[4])); }
            catch (Exception ignored) {}
        }
        builder.addTechnique(t);
    }

    private void parseMissionResult(String[] parts, ConcreteMissionBuilder builder) {
        if (parts.length > 1) {
            try {
                builder.buildOutcome(Mission.Outcome.valueOf(parts[1]));
            } catch (Exception ignored) {}
        }
    }

    private void parseOperationTag(String[] parts, ConcreteMissionBuilder builder) {
        if (parts.length > 1) builder.addOperationTag(parts[1]);
    }

    private void parseSupportUnit(String[] parts, ConcreteMissionBuilder builder) {
        if (parts.length > 1) builder.addSupportUnit(parts[1]);
    }

    private void parseRecommendation(String[] parts, ConcreteMissionBuilder builder) {
        if (parts.length > 1) builder.addRecommendation(parts[1]);
    }

    private void parseArtifactRecovered(String[] parts, ConcreteMissionBuilder builder) {
        if (parts.length > 1) builder.addArtifactRecovered(parts[1]);
    }

    private void parseEvacuationZone(String[] parts, ConcreteMissionBuilder builder) {
        if (parts.length > 1) builder.addEvacuationZone(parts[1]);
    }

    private void parseStatusEffect(String[] parts, ConcreteMissionBuilder builder) {
        if (parts.length > 1) builder.addStatusEffect(parts[1]);
    }
}