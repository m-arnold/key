package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AssignableOptimizationAction extends MainWindowAction {

    public AssignableOptimizationAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use assignable clause optimization");
        setTooltip("If ticked, Method purity information are used to optimize assignable clauses");
        setSelected(StaticAnalysisSettings.useAssignableClauseOptimization());
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.setUseAssignableOptimization(b);
    }
}
