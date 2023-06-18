package de.uka.ilkd.key.gui.actions;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.staticanalysis.StaticAnalysisSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpalMethodPurityAnalysisAction extends MainWindowAction {
    public OpalMethodPurityAnalysisAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use Method Purity Analyis");
        setTooltip("If ticked, Opal's Method Purity Analysis is executed");
        setSelected(StaticAnalysisSettings.getINST().useMethodPurityAnalysis());
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.getINST().setUseMethodPurityAnalysis(b);
    }
}
