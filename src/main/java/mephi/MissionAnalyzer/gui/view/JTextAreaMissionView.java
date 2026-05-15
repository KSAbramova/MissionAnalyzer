package gui.view;

import javax.swing.JTextArea;

public class JTextAreaMissionView implements MissionView {

    private final JTextArea textArea;

    public JTextAreaMissionView(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void showReport(String report) {
        textArea.setText(report);
    }

    @Override
    public void showError(String message) {
        textArea.setText("ОШИБКА:\n" + message);
    }

    @Override
    public void clearDisplay() {
        textArea.setText("");
    }
}