package mephi.MissionAnalyzer.gui.reports;

import gui.sections.MissionSectionRenderer;
import gui.sections.SectionRendererRegistry;
import model.Mission;

public class DetailedMissionReportStrategy implements MissionReportStrategy{
    @Override
    public String getName() {
        return "Detailed";
    }

    @Override
    public String generate(Mission mission) {
        StringBuilder report = new StringBuilder();
        report.append("                 ДЕТАЛЬНЫЙ ОТЧЁТ ПО МИССИИ\n");
        for (MissionSectionRenderer renderer : SectionRendererRegistry.getAllRenderers()) {
            String sectionContent = renderer.render(mission);
            if (!sectionContent.equals("Данных нет")) {
                report.append("【").append(renderer.getSectionName()).append("】\n");
                report.append(sectionContent).append("\n\n");
            }
        }

        return report.toString();
    }
}
