package gui.sections;

import model.Mission;

public class SupportUnitsSectionRenderer implements MissionSectionRenderer {
    @Override
    public String getSectionName() {
        return "Вспомогательные подразделения";
    }

    @Override
    public String render(Mission m) {
        return m.getSupportUnits().isEmpty()
                ? "Данных нет"
                : String.join(", ", m.getSupportUnits());
    }
}