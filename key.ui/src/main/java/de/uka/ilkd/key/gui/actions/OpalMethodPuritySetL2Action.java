package de.uka.ilkd.key.gui.actions;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;
import de.uka.ilkd.key.opal.runner.AnalysisLevel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpalMethodPuritySetL2Action extends MainWindowAction{

    public OpalMethodPuritySetL2Action(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use L2");
        setTooltip("If ticked, Opal's Method Purity uses L2");
        setSelected(StaticAnalysisSettings.getINST().getMethodPurityLevel() == AnalysisLevel.L2);
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.getINST().setMethodPurityLevel(AnalysisLevel.L2);
    }
}
