package gui.sections;

import model.Mission;

public class OperationTagsSectionRenderer implements MissionSectionRenderer {
    @Override
    public String getSectionName() {
        return "Теги миссии";
    }

    @Override
    public String render(Mission m) {
        return m.getOperationTags().isEmpty()
                ? "Данных нет"
                : String.join(", ", m.getOperationTags());
    }
}