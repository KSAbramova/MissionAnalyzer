package mephi.MissionAnalyzer.gui.reports;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class ReportStrategyRegistry {

    private static final Map<String, MissionReportStrategy> strategies = new LinkedHashMap<>();

    static {
        register(new DefaultMissionReportStrategy());
        register(new DetailedMissionReportStrategy());
    }

    public static void register(MissionReportStrategy strategy) {
        strategies.put(strategy.getName().toUpperCase(), strategy);
    }

    public static List<String> getStrategyNames() {
        return new ArrayList<>(strategies.keySet());
    }

    public static MissionReportStrategy getStrategyByNameStatic(String name) {
        if (name == null) return strategies.get("DEFAULT");
        return strategies.getOrDefault(name.toUpperCase(), strategies.get("DEFAULT"));
    }

    public MissionReportStrategy getStrategyByName(String name) {
        return getStrategyByNameStatic(name);
    }

    public Collection<MissionReportStrategy> getAllStrategies() {
        return strategies.values();
    }
}