package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;
import de.uka.ilkd.key.opal.runner.AnalysisLevel;

import java.awt.event.ActionEvent;

public class CiFiL1Action extends MainWindowAction {

    public CiFiL1Action(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use L1");
        setTooltip("If ticked, Opal's Field Immutability Analysis uses L1");
        setSelected(StaticAnalysisSettings.methodPurityLevel == AnalysisLevel.L1);
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StaticAnalysisSettings.fieldImmutabilityLevel = AnalysisLevel.L1;
    }
}
