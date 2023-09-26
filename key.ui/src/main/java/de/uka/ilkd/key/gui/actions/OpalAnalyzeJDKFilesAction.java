package de.uka.ilkd.key.gui.actions;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.staticanalysis.StaticAnalysisSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpalAnalyzeJDKFilesAction extends MainWindowAction {

    public OpalAnalyzeJDKFilesAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Analyze JDK Files");
        setTooltip("If ticked, Opal's also analyzes files provided by the JDK");
        setSelected(StaticAnalysisSettings.getINST().analyzeJDKFiles());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.getINST().setAnalyzeJDKFiles(b);
    }

}
