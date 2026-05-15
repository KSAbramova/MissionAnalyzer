package gui.sections;

import model.Mission;

public class EvacuationZonesSectionRenderer implements MissionSectionRenderer {
    @Override
    public String getSectionName() {
        return "Зоны эвакуации";
    }

    @Override
    public String render(Mission m) {
        return m.getEvacuationZones().isEmpty()
                ? "Данных нет"
                : String.join(", ", m.getEvacuationZones());
    }
}