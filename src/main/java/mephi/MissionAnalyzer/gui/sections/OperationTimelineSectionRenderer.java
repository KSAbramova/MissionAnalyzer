package gui.sections;

import model.Mission;
import model.OperationTimeline;

public class OperationTimelineSectionRenderer implements MissionSectionRenderer {
    @Override
    public String getSectionName() {
        return "Хронология операции";
    }

    @Override
    public String render(Mission m) {
        if (m.getOperationTimeline().isEmpty()) return "Данных нет";
        StringBuilder sb = new StringBuilder();
        for (OperationTimeline ot : m.getOperationTimeline()) {
            sb.append(ot.getTimestamp()).append(" — ")
              .append(ot.getType()).append(": ")
              .append(ot.getDescription()).append("\n");
        }
        return sb.toString();
    }
}