package mephi.MissionAnalyzer.gui;

import gui.view.MissionView;
import model.Mission;

import javax.swing.*;
import java.io.File;
import mephi.MissionAnalyzer.parser.MissionParseException;
import mephi.MissionAnalyzer.service.MissionProcessingService;

public class MissionPresenter {

    private final MissionView view;
    private final MissionProcessingService service;

    private Mission currentMission;
    private String currentReportType = "Default";

    public MissionPresenter(MissionView view, MissionProcessingService service) {
        this.view = view;
        this.service = service;
    }

    public void onOpenFileClicked() {
        File file = selectFile();
        if (file == null) return;
        try {
            currentMission = service.parse(file);
            renderCurrentMission();
            showSuccessMessage();
        } catch (MissionParseException e) {
            view.showError("Ошибка парсинга файла:\n" + e.getMessage());
        } 
    }

    private File selectFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Выберите файл миссии");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Файлы миссий", "txt", "json", "xml", "yaml"));

        return chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION
                ? chooser.getSelectedFile()
                : null;
    }

    private void showSuccessMessage() {
        JOptionPane.showMessageDialog(null,
                "Миссия успешно загружена: " + currentMission.getMissionId(),
                "Успех",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void setCurrentReportType(String reportType) {
        this.currentReportType = reportType;
    }

    public void renderCurrentMission() {
        if (currentMission == null);
        try {
            String report = service.generateReport(currentMission, currentReportType);
            view.showReport(report);
        } catch (Exception e) {
            view.showError("Ошибка формирования отчёта:\n" + e.getMessage());
        }
    }
}