package mephi.MissionAnalyzer.gui;

import gui.view.MissionView;
import gui.view.JTextAreaMissionView;

import javax.swing.*;
import java.awt.*;
import mephi.MissionAnalyzer.gui.components.FileOpenButton;
import mephi.MissionAnalyzer.gui.components.ReportTypeSelector;
import mephi.MissionAnalyzer.service.MissionProcessingService;

public class MissionAnalyzerGUI extends JFrame {

    private final MissionPresenter presenter;

    private final JTextArea textArea;
    private final FileOpenButton openButton;
    private final ReportTypeSelector reportTypeSelector;

    public MissionAnalyzerGUI(MissionProcessingService missionService) {
        this.textArea = createTextArea();

        MissionView view = new JTextAreaMissionView(textArea);
        this.presenter = new MissionPresenter(view, missionService);

        this.openButton = new FileOpenButton(presenter);
        this.reportTypeSelector = new ReportTypeSelector(presenter);

        setupWindow();
        setupLayout();
    }

    private JTextArea createTextArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    private void setupWindow() {
        setTitle("Анализатор миссий — ЛР №2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = createTopPanel();
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.add(openButton);
        panel.add(new JLabel("Тип отчёта:"));
        panel.add(reportTypeSelector);
        return panel;
    }

}