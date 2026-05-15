package gui.sections;

import java.util.*;

public class SectionRendererRegistry {

    private static final List<MissionSectionRenderer> renderers = new ArrayList<>();

    static {
        register(new BasicInfoSectionRenderer());
        register(new CurseSectionRenderer());
        register(new SorcerersSectionRenderer());
        register(new TechniquesSectionRenderer());
        register(new EconomicAssessmentSectionRenderer());
        register(new CivilianImpactSectionRenderer());
        register(new EnemyActivitySectionRenderer());
        register(new EnvironmentConditionsSectionRenderer());
        register(new OperationTimelineSectionRenderer());
        register(new OperationTagsSectionRenderer());
        register(new SupportUnitsSectionRenderer());
        register(new RecommendationsSectionRenderer());
        register(new ArtifactsRecoveredSectionRenderer());
        register(new EvacuationZonesSectionRenderer());
        register(new StatusEffectsSectionRenderer());
    }

    public static void register(MissionSectionRenderer renderer) {
        renderers.add(renderer);
    }

    public static List<MissionSectionRenderer> getAllRenderers() {
        return Collections.unmodifiableList(renderers);
    }

    public static List<String> getSectionNames() {
        return renderers.stream()
                .map(MissionSectionRenderer::getSectionName)
                .toList();
    }
    
    public static MissionSectionRenderer getRendererByName(String sectionName) {
        for (MissionSectionRenderer renderer : renderers) {
            if (renderer.getSectionName().equals(sectionName)) {
                return renderer;
            }
        }
        throw new IllegalArgumentException("Секция с названием '" + sectionName + "' не найдена");
    }
    
    public static boolean contains(String sectionName) {
        for (MissionSectionRenderer renderer : renderers) {
            if (renderer.getSectionName().equals(sectionName)) {
                return true;
            }
        }
        return false;
    }
}