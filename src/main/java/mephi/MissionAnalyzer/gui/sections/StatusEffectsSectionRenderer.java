package gui.sections;

import model.Mission;

public class StatusEffectsSectionRenderer implements MissionSectionRenderer {
    @Override
    public String getSectionName() {
        return "Эффекты / состояния";
    }

    @Override
    public String render(Mission m) {
        return m.getStatusEffects().isEmpty()
                ? "Данных нет"
                : String.join(", ", m.getStatusEffects());
    }
}