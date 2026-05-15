package gui.sections;

import model.Mission;
import model.Sorcerer;

public class SorcerersSectionRenderer implements MissionSectionRenderer {
    @Override 
    public String getSectionName() { return "Участники миссии"; }
    
    @Override
    public String render(Mission m) {
        if (m.getSorcerers().isEmpty()) return "Данных нет";
        StringBuilder sb = new StringBuilder();
        for (Sorcerer s : m.getSorcerers()) {
            sb.append("• ").append(s.getName() != null ? s.getName() : "—");
            sb.append(" (").append(s.getRank() != null ? s.getRank() : "—");
            sb.append(")\n");
        }
        return sb.toString();
    }
}