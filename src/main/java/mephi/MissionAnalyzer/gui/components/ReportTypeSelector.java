package mephi.MissionAnalyzer.gui.components;

import mephi.MissionAnalyzer.gui.MissionPresenter;
import mephi.MissionAnalyzer.gui.reports.ReportStrategyRegistry;

import javax.swing.*;

public class ReportTypeSelector extends JComboBox<String> {

    public ReportTypeSelector(MissionPresenter presenter) {
        super();

        for (String name : ReportStrategyRegistry.getStrategyNames()) {
            addItem(name);
        }

        addActionListener(e -> {
            String selected = (String) getSelectedItem();
            if (selected != null) {
                presenter.setCurrentReportType(selected);
                presenter.renderCurrentMission();
            }
        });

        if (getItemCount() > 0) {
            setSelectedIndex(0);
        }
    }
}