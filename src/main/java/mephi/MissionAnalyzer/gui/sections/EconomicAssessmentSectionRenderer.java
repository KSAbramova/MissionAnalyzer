package gui.sections;

import model.Mission;

public class EconomicAssessmentSectionRenderer implements MissionSectionRenderer {

    @Override
    public String getSectionName() {
        return "Экономическая оценка ущерба";
    }

    @Override
    public String render(Mission m) {
        if (m.getEconomicAssessment() == null) return "Данных нет";
        var ea = m.getEconomicAssessment();
        StringBuilder sb = new StringBuilder();
        sb.append("Общий ущерб               : ").append(ea.getTotalDamageCost() != null ? ea.getTotalDamageCost() : "—").append("\n");
        sb.append("Ущерб инфраструктуре      : ").append(ea.getInfrastructureDamage() != null ? ea.getInfrastructureDamage() : "—").append("\n");
        sb.append("Коммерческий ущерб        : ").append(ea.getCommercialDamage() != null ? ea.getCommercialDamage() : "—").append("\n");
        sb.append("Ущерб транспорту          : ").append(ea.getTransportDamage() != null ? ea.getTransportDamage() : "—").append("\n");
        sb.append("Время восстановления      : ").append(ea.getRecoveryEstimateDays() != null ? ea.getRecoveryEstimateDays() + " дней" : "—").append("\n");
        sb.append("Покрытие страхованием     : ").append(ea.isInsuranceCovered() != null ? (ea.isInsuranceCovered() ? "Да" : "Нет") : "—");
        return sb.toString();
    }
}
