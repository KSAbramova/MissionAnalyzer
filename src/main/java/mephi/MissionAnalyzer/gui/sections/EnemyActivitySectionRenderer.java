package gui.sections;

import model.Mission;

public class EnemyActivitySectionRenderer implements MissionSectionRenderer {
    @Override
    public String getSectionName() {
        return "Деятельность противника";
    }

    @Override
    public String render(Mission m) {
        if (m.getEnemyActivity() == null) return "Данных нет";
        var ea = m.getEnemyActivity();
        StringBuilder sb = new StringBuilder();
        sb.append("Тип поведения     : ").append(ea.getBehaviorType() != null ? ea.getBehaviorType() : "—").append("\n");
        sb.append("Мобильность       : ").append(ea.getMobility() != null ? ea.getMobility() : "—").append("\n");
        sb.append("Риск эскалации    : ").append(ea.getEscalationRisk() != null ? ea.getEscalationRisk() : "—").append("\n");
        if (!ea.getTargetPriority().isEmpty()) {
            sb.append("Приоритеты целей  : ").append(String.join(", ", ea.getTargetPriority())).append("\n");
        }
        if (!ea.getAttackPatterns().isEmpty()) {
            sb.append("Паттерны атак     : ").append(String.join(", ", ea.getAttackPatterns()));
        }
        return sb.toString();
    }
}