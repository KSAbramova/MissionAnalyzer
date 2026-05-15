package gui.sections;

import model.Mission;

public class CurseSectionRenderer implements MissionSectionRenderer {
    @Override public String getSectionName() { return "Проклятие"; }
    @Override public String render(Mission m) {
        if (m.getCurse() == null) return "Данных нет";
        var c = m.getCurse();
        StringBuilder sb = new StringBuilder();
        sb.append("Название      : ").append(c.getName() != null ?  c.getName(): "—").append("\n");
        sb.append("Уровень угрозы: ").append(c.getThreatLevel() != null ? c.getThreatLevel() : "—");
        return sb.toString();
    }
}