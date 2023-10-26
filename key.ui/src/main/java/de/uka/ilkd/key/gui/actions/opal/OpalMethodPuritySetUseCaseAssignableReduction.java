package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;
import de.uka.ilkd.key.opal.runner.AnalysisLevel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpalMethodPuritySetUseCaseAssignableReduction extends MainWindowAction {

    public OpalMethodPuritySetUseCaseAssignableReduction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use assignable clause reduction");
        setTooltip("If ticked, Method purity information are used to reduce assignable clauses");
        setSelected(StaticAnalysisSettings.useAssignableClauseReduction());
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.setUseAssignableClauseReduction(b);
    }
}
