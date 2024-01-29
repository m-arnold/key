package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ThrownExceptionAnalysisAction extends MainWindowAction {
    public ThrownExceptionAnalysisAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use Thrown Exception Analysis");
        setTooltip("If ticked, Opal's Thrown Exception Analysis is executed");
        setSelected(StaticAnalysisSettings.useExceptionUsageAnalysis);
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.useExceptionUsageAnalysis = b;
    }
}
