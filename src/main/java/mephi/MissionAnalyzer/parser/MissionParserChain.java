package mephi.MissionAnalyzer.parser;

import model.Mission;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import mephi.MissionAnalyzer.parser.handlers.*;

@Component
public class MissionParserChain {

    private final List<MissionParser> parsers = new ArrayList<>();

    public MissionParserChain() {
        
        parsers.add(new TxtMissionParserHandler());
        parsers.add(new JsonMissionParserHandler());
        parsers.add(new XmlMissionParserHandler());
        parsers.add(new YamlMissionParserHandler());
        parsers.add(new NoExtensionMissionParserHandler());

        for (int i = 0; i < parsers.size() - 1; i++) {
            parsers.get(i).setNext(parsers.get(i + 1));
        }
    }

    public Mission parse(File file) throws MissionParseException {
        if (parsers.isEmpty()) {
            throw new MissionParseException("Нет зарегистрированных парсеров");
        }
        return parsers.get(0).parse(file);
    }
}