package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpalMethodPurityAnalysisAction extends MainWindowAction {
    public OpalMethodPurityAnalysisAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use Method Purity Analyis");
        setTooltip("If ticked, Opal's Method Purity Analysis is executed");
        setSelected(StaticAnalysisSettings.useMethodPurityAnalysis);
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.useMethodPurityAnalysis = b;
    }
}
