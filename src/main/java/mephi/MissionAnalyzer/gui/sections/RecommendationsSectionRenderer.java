package gui.sections;

import model.Mission;

public class RecommendationsSectionRenderer implements MissionSectionRenderer {
    @Override
    public String getSectionName() {
        return "Рекомендации";
    }

    @Override
    public String render(Mission m) {
        return m.getRecommendations().isEmpty()
                ? "Данных нет"
                : String.join("\n• ", m.getRecommendations());
    }
}