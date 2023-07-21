package de.uka.ilkd.key.gui.actions;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.staticanalysis.StaticAnalysisSettings;
import de.uka.ilkd.key.staticanalysis.runner.AnalysisLevel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpalMethodPuritySetL1Action extends MainWindowAction{

    public OpalMethodPuritySetL1Action(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use L1");
        setTooltip("If ticked, Opal's Method Purity uses L1");
        setSelected(StaticAnalysisSettings.getINST().getMethodPurityLevel() == AnalysisLevel.L1);
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.getINST().setMethodPurityLevel(AnalysisLevel.L1);
    }
}
