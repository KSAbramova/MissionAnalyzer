package mephi.MissionAnalyzer.parser;

import model.Mission;
import java.io.File;

public interface MissionParser {
    Mission parse(File file) throws MissionParseException;
    boolean canHandle(File file);
    void setNext(MissionParser next);
}