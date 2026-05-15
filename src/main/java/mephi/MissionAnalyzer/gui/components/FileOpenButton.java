package mephi.MissionAnalyzer.gui.components;

import javax.swing.*;
import mephi.MissionAnalyzer.gui.MissionPresenter;

public class FileOpenButton extends JButton {

    public FileOpenButton(MissionPresenter presenter) {
        super("Открыть файл миссии");
        setToolTipText("Выбрать файл миссии");
        addActionListener(e -> presenter.onOpenFileClicked());
    }

}