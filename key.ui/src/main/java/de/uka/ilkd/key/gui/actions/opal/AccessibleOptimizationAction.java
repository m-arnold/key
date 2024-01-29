package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AccessibleOptimizationAction extends MainWindowAction {

    public AccessibleOptimizationAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use accessible clause optimization");
        setTooltip("If ticked, Method purity information are used to optimize accessible clauses");
        setSelected(StaticAnalysisSettings.useAccessibleClauseOptimization());
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.setUseAccessibleClauseOptimization(b);
    }
}
