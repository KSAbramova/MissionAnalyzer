package mephi.MissionAnalyzer.gui.reports;

import gui.sections.MissionSectionRenderer;
import gui.sections.SectionRendererRegistry;
import model.Mission;

public class DefaultMissionReportStrategy implements MissionReportStrategy {

    @Override
    public String getName() {
        return "Default";
    }

    @Override
    public String generate(Mission mission) {
        MissionSectionRenderer basicRenderer = SectionRendererRegistry.getRendererByName("Основная информация");
        MissionSectionRenderer curseRenderer = SectionRendererRegistry.getRendererByName("Проклятие");
        
        StringBuilder sb = new StringBuilder();
        if (!basicRenderer.render(mission).equals("Данных нет")) {
            sb.append("【").append(basicRenderer.getSectionName()).append("】\n");
            sb.append(basicRenderer.render(mission)).append("\n");
        } 
            
        if (!curseRenderer.render(mission).equals("Данных нет")){
            sb.append("【").append(curseRenderer.getSectionName()).append("】\n");
            sb.append(curseRenderer.render(mission)).append("\n");
        } 
        
        return sb.toString();
    }

}