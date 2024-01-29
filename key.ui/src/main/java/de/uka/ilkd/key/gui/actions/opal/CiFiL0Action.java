package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;
import de.uka.ilkd.key.opal.runner.AnalysisLevel;

import java.awt.event.ActionEvent;

public class CiFiL0Action extends MainWindowAction {

    public CiFiL0Action(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use L0");
        setTooltip("If ticked, Opal's Field Immutability Analysis uses L0");
        setSelected(StaticAnalysisSettings.methodPurityLevel == AnalysisLevel.L1);
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StaticAnalysisSettings.fieldImmutabilityLevel = AnalysisLevel.L0;
    }
}
