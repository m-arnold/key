package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;
import de.uka.ilkd.key.opal.runner.AnalysisLevel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpalMethodPuritySetL0Action extends MainWindowAction {

    public OpalMethodPuritySetL0Action(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use L0");
        setTooltip("If ticked, Opal's Method Purity uses L0");
        setSelected(StaticAnalysisSettings.methodPurityLevel == AnalysisLevel.L0);
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StaticAnalysisSettings.methodPurityLevel = AnalysisLevel.L0;
    }
}
