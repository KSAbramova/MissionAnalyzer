package mephi.MissionAnalyzer.parser.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.*;
import java.io.File;
import mephi.MissionAnalyzer.parser.BaseMissionParser;
import mephi.MissionAnalyzer.parser.MissionParseException;

public class JsonMissionParserHandler extends BaseMissionParser {

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private final MissionDirector director = new MissionDirector();

    @Override
    public boolean canHandle(File file) {
        return file.getName().toLowerCase().endsWith(".json");
    }

    @Override
    protected Mission doParse(File file) throws MissionParseException {
        try {
            Mission missionFromJson = mapper.readValue(file, Mission.class);
           
            return director.constructMission(missionFromJson);

        } catch (Exception e) {
            throw new MissionParseException("Ошибка при разборе JSON-файла: " + file.getName(), e);
        }
    }
}