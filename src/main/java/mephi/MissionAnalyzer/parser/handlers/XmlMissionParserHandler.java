package mephi.MissionAnalyzer.parser.handlers;

import model.*;
import mephi.MissionAnalyzer.parser.BaseMissionParser;
import mephi.MissionAnalyzer.parser.MissionParseException;

import javax.xml.stream.*;
import java.io.*;
import java.time.LocalDate;

public class XmlMissionParserHandler extends BaseMissionParser {

    private final MissionDirector director = new MissionDirector();
    
    @Override
    public boolean canHandle(File file) {
        return file.getName().toLowerCase().endsWith(".xml");
    }

    @Override
    protected Mission doParse(File file) throws MissionParseException {
        try {
            ConcreteMissionBuilder builder = new ConcreteMissionBuilder();
            builder.createNewMission();

            try (FileInputStream fis = new FileInputStream(file)) {
                XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(fis);

                while (reader.hasNext()) {
                    int event = reader.next();

                    if (event == XMLStreamConstants.START_ELEMENT) {
                        String tag = reader.getLocalName();
                        handleElement(tag, reader, builder);
                    }
                }
            }

            return director.constructFromBuilder(builder);

        } catch (Exception e) {
            throw new MissionParseException("Ошибка при разборе XML-файла: " + file.getName(), e);
        }
    }

    private void handleElement(String tag, XMLStreamReader reader, ConcreteMissionBuilder builder) throws XMLStreamException {
        switch (tag) {
            case "missionId"   -> builder.buildMissionId(reader.getElementText());
            case "date"        -> builder.buildDate(LocalDate.parse(reader.getElementText().trim()));
            case "location"    -> builder.buildLocation(reader.getElementText());
            case "outcome"     -> builder.buildOutcome(Mission.Outcome.valueOf(reader.getElementText().trim().toUpperCase()));
            case "damageCost"  -> builder.buildDamageCost(Long.parseLong(reader.getElementText().trim()));
            case "note", "comment" -> builder.buildNote(reader.getElementText());

            case "curse"       -> parseCurse(reader, builder);
            case "sorcerer"    -> parseSorcerer(reader, builder);
            case "technique"   -> parseTechnique(reader, builder);
            
            case "economicAssessment" -> parseEconomicAssessment(reader, builder);
            case "civilianImpact"     -> parseCivilianImpact(reader, builder);
            case "enemyActivity"      -> parseEnemyActivity(reader, builder);
            case "environment"        -> parseEnvironmentConditions(reader, builder);
            case "timeline"           -> parseOperationTimeline(reader, builder);
            
            case "operationTags"      -> parseStringList(reader, builder::addOperationTag);
            case "supportUnits"       -> parseStringList(reader, builder::addSupportUnit);
            case "recommendations"    -> parseStringList(reader, builder::addRecommendation);
            case "artifactsRecovered" -> parseStringList(reader, builder::addArtifactRecovered);
            case "evacuationZones"    -> parseStringList(reader, builder::addEvacuationZone);
            case "statusEffects"      -> parseStringList(reader, builder::addStatusEffect);
        }
    }

    private void parseCurse(XMLStreamReader reader, ConcreteMissionBuilder builder) throws XMLStreamException {
        Curse curse = new Curse();

        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.END_ELEMENT && "curse".equals(reader.getLocalName())) break;

            if (event == XMLStreamConstants.START_ELEMENT) {
                String subTag = reader.getLocalName();
                switch (subTag) {
                    case "name" -> curse.setName(reader.getElementText());
                    case "threatLevel" -> {
                        try {
                            curse.setThreatLevel(Curse.ThreatLevel.valueOf(reader.getElementText().trim().toUpperCase()));
                        } catch (Exception ignored) {}
                    }
                }
            }
        }
        builder.buildCurse(curse);
    }

    private void parseSorcerer(XMLStreamReader reader, ConcreteMissionBuilder builder) throws XMLStreamException {
        Sorcerer sorcerer = new Sorcerer();

        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.END_ELEMENT && "sorcerer".equals(reader.getLocalName())) break;

            if (event == XMLStreamConstants.START_ELEMENT) {
                String subTag = reader.getLocalName();
                switch (subTag) {
                    case "name" -> sorcerer.setName(reader.getElementText());
                    case "rank" -> {
                        try {
                            sorcerer.setRank(Sorcerer.Rank.valueOf(reader.getElementText().trim().toUpperCase()));
                        } catch (Exception ignored) {}
                    }
                }
            }
        }
        builder.addSorcerer(sorcerer);
    }

    private void parseTechnique(XMLStreamReader reader, ConcreteMissionBuilder builder) throws XMLStreamException {
        Technique technique = new Technique();

        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.END_ELEMENT && "technique".equals(reader.getLocalName())) break;

            if (event == XMLStreamConstants.START_ELEMENT) {
                String subTag = reader.getLocalName();
                switch (subTag) {
                    case "name"  -> technique.setName(reader.getElementText());
                    case "type"  -> {
                        try {
                            technique.setType(Technique.Type.valueOf(reader.getElementText().trim().toUpperCase()));
                        } catch (Exception ignored) {}
                    }
                    case "owner" -> technique.setOwner(reader.getElementText());
                    case "damage"-> technique.setDamage(Long.parseLong(reader.getElementText().trim()));
                }
            }
        }
        builder.addTechnique(technique);
    }
    
    private void parseStringList(XMLStreamReader reader, java.util.function.Consumer<String> adder) throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.END_ELEMENT) break;
            if (event == XMLStreamConstants.START_ELEMENT && "item".equals(reader.getLocalName())) {
                adder.accept(reader.getElementText());
            }
        }
    }
    
    // заглушки
    private void parseEconomicAssessment(XMLStreamReader reader, ConcreteMissionBuilder builder) throws XMLStreamException {}
    private void parseCivilianImpact(XMLStreamReader reader, ConcreteMissionBuilder builder) throws XMLStreamException {}
    private void parseEnemyActivity(XMLStreamReader reader, ConcreteMissionBuilder builder) throws XMLStreamException {}
    private void parseEnvironmentConditions(XMLStreamReader reader, ConcreteMissionBuilder builder) throws XMLStreamException {}
    private void parseOperationTimeline(XMLStreamReader reader, ConcreteMissionBuilder builder) throws XMLStreamException {}
}