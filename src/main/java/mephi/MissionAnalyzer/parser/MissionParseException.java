package mephi.MissionAnalyzer.parser;

public class MissionParseException extends Exception {
    public MissionParseException(String message) {
        super(message);
    }

    public MissionParseException(String message, Throwable cause) {
        super(message, cause);
    }
}