package gui.sections;

import model.Mission;

public class BasicInfoSectionRenderer implements MissionSectionRenderer {
    @Override public String getSectionName() { return "Основная информация"; }
    @Override public String render(Mission m) {
        StringBuilder sb = new StringBuilder();
        sb.append("Идентификатор : ").append(m.getMissionId() != null ? m.getMissionId() : "—").append("\n");
        sb.append("Дата          : ").append(m.getDate() != null ? m.getDate() : "—").append("\n");
        sb.append("Локация       : ").append(m.getLocation() != null ? m.getLocation() : "—").append("\n");
        sb.append("Итог          : ").append(m.getOutcome() != null ? m.getOutcome() : "—").append("\n");
        if (m.getDamageCost() != null) sb.append("Общий ущерб   : ").append(m.getDamageCost());
        if (!"".equals(m.getNote())) sb.append("\n").append(m.getNote());
        return sb.toString();
    }
}