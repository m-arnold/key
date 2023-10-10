package de.uka.ilkd.key.gui.actions;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;
import de.uka.ilkd.key.opal.runner.AnalysisLevel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpalFieldImmutabilitySetL2Action extends MainWindowAction {

    public OpalFieldImmutabilitySetL2Action(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use L2");
        setTooltip("If ticked, Opal's Field Immutability Analysis uses L2");
        setSelected(StaticAnalysisSettings.getINST().getMethodPurityLevel() == AnalysisLevel.L2);
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.getINST().setFieldImmutabilityLevel(AnalysisLevel.L2);
    }
}
