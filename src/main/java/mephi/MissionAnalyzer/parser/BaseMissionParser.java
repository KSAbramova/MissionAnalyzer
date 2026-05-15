package mephi.MissionAnalyzer.parser;

import model.Mission;
import java.io.File;

public abstract class BaseMissionParser implements MissionParser {

    protected MissionParser next;

    @Override
    public void setNext(MissionParser next) {
        this.next = next;
    }

    @Override
    public final Mission parse(File file) throws MissionParseException {
        if (canHandle(file)) {
            return doParse(file);
        }

        if (next != null) {
            return next.parse(file);
        }

        throw new MissionParseException("Неподдерживаемый формат файла: " + file.getName());
    }

    public abstract boolean canHandle(File file);
    protected abstract Mission doParse(File file) throws MissionParseException;
}