package gui.sections;

import model.Mission;

public class ArtifactsRecoveredSectionRenderer implements MissionSectionRenderer {
    @Override
    public String getSectionName() {
        return "Найденные артефакты";
    }

    @Override
    public String render(Mission m) {
        return m.getArtifactsRecovered().isEmpty()
                ? "Данных нет"
                : String.join("\n• ", m.getArtifactsRecovered());
    }
}