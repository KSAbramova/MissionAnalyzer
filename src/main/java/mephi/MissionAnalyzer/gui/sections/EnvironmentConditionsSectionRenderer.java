// EnvironmentConditionsSectionRenderer.java
package gui.sections;

import model.Mission;

public class EnvironmentConditionsSectionRenderer implements MissionSectionRenderer {
    @Override
    public String getSectionName() {
        return "Условия среды";
    }

    @Override
    public String render(Mission m) {
        if (m.getEnvironmentConditions() == null) return "Данных нет";
        var ec = m.getEnvironmentConditions();
        StringBuilder sb = new StringBuilder();
        sb.append("Погода                  : ").append(ec.getWeather() != null ? ec.getWeather() : "—").append("\n");
        sb.append("Время суток             : ").append(ec.getTimeOfDay() != null ? ec.getTimeOfDay() : "—").append("\n");
        sb.append("Видимость               : ").append(ec.getVisibility() != null ? ec.getVisibility() : "—").append("\n");
        sb.append("Плотность проклятой энергии : ").append(ec.getCursedEnergyDensity() != null ? ec.getCursedEnergyDensity() : "—");
        return sb.toString();
    }
}