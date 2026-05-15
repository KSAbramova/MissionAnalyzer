package mephi.MissionAnalyzer.gui.reports;

import model.Mission;

public interface MissionReportStrategy {

    String getName();
    String generate(Mission mission);
}