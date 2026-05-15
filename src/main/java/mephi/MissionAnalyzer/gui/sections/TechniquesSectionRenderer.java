package gui.sections;

import model.Mission;
import model.Technique;

public class TechniquesSectionRenderer implements MissionSectionRenderer {
    @Override public String getSectionName() { 
        return "Применённые техники"; }
    @Override public String render(Mission m) {
        if (m.getTechniques().isEmpty()) return "Данных нет";
        StringBuilder sb = new StringBuilder();
        for (Technique t : m.getTechniques()) {
            
            sb.append("• ").append(t.getName() != null ? t.getName() : "—");
            sb.append(" (тип — [").append(t.getType() != null ? t.getType() : "—");
            sb.append("], владелец — ").append(t.getOwner() != null ? t.getOwner() : "—");
            sb.append(", урон — ").append(t.getDamage() != null ? t.getDamage() : "—");
            sb.append(")\n");
        }
        return sb.toString();
    }
}