package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FieldImmutabilityAnalysisAction extends MainWindowAction {

    public FieldImmutabilityAnalysisAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use Immutability Analyis");
        setTooltip("If ticked, Opal's Field Immutability Analysis is executed");
        setSelected(StaticAnalysisSettings.useFieldImmutabilityAnalysis);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.useFieldImmutabilityAnalysis = b;
    }

}