package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpalMethodPuritySetUseCaseAssignableGeneration extends MainWindowAction {

    public OpalMethodPuritySetUseCaseAssignableGeneration(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use assignable clause generation");
        setTooltip("If ticked, Method purity information are used to generate additional assignable clauses");
        setSelected(StaticAnalysisSettings.useAssignableClauseGeneration());
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.setUseAssignableClauseGeneration(b);
    }
}
