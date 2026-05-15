package gui.sections;

import model.Mission;

public interface MissionSectionRenderer {

    String getSectionName();
    String render(Mission mission);
}