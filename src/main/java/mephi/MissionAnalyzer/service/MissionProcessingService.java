package mephi.MissionAnalyzer.service;

import model.Mission;
import mephi.MissionAnalyzer.parser.MissionParseException;
import mephi.MissionAnalyzer.parser.MissionParserChain;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import mephi.MissionAnalyzer.gui.reports.MissionReportStrategy;
import mephi.MissionAnalyzer.gui.reports.ReportStrategyRegistry;

@Service
public class MissionProcessingService {

    private final MissionParserChain parserChain;
    private final ReportStrategyRegistry reportStrategyRegistry;

    public MissionProcessingService(MissionParserChain parserChain,
                                    ReportStrategyRegistry reportStrategyRegistry) {
        this.parserChain = parserChain;
        this.reportStrategyRegistry = reportStrategyRegistry;
    }

    public Mission parse(File file) throws MissionParseException {
        return parserChain.parse(file);
    }

    public String generateReport(Mission mission, String reportType) {
        if (mission == null) {
            return "Ошибка: миссия не найдена";
        }

        MissionReportStrategy strategy = reportStrategyRegistry.getStrategyByName(reportType);
        return strategy.generate(mission);
    }

    public Collection<String> getAvailableReportTypes() {
        return reportStrategyRegistry.getStrategyNames();
    }
}