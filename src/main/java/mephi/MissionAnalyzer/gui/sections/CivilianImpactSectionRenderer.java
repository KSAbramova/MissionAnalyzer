package gui.sections;

import model.Mission;

public class CivilianImpactSectionRenderer implements MissionSectionRenderer {
    @Override
    public String getSectionName() {
        return "Влияние на гражданских";
    }

    @Override
    public String render(Mission m) {
        if (m.getCivilianImpact() == null) return "Данных нет";
        var ci = m.getCivilianImpact();
        StringBuilder sb = new StringBuilder();
        sb.append("Эвакуировано   : ").append(ci.getEvacuated() != null ? ci.getEvacuated() : "—").append("\n");
        sb.append("Пострадавшие   : ").append(ci.getInjured() != null ? ci.getInjured() : "—").append("\n");
        sb.append("Пропавшие      : ").append(ci.getMissing() != null ? ci.getMissing() : "—").append("\n");
        sb.append("Риск раскрытия : ").append(ci.getPublicExposureRisk() != null ? ci.getPublicExposureRisk() : "—");
        return sb.toString();
    }
}