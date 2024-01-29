package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AnalyzeJDKFilesAction extends MainWindowAction {

    public AnalyzeJDKFilesAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Analyze JDK Files");
        setTooltip("If ticked, Opal's also analyzes files provided by the JDK");
        setSelected(StaticAnalysisSettings.analyzeJDKFiles);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.analyzeJDKFiles = b;
    }

}
