package de.uka.ilkd.key.gui.actions;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.staticanalysis.StaticAnalysisSettings;
import de.uka.ilkd.key.staticanalysis.runner.AnalysisLevel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpalFieldImmutabilitySetL1Action extends MainWindowAction {

    public OpalFieldImmutabilitySetL1Action(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use L1");
        setTooltip("If ticked, Opal's Field Immutability Analysis uses L1");
        setSelected(StaticAnalysisSettings.getINST().getMethodPurityLevel() == AnalysisLevel.L1);
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.getINST().setFieldImmutabilityLevel(AnalysisLevel.L1);
    }
}