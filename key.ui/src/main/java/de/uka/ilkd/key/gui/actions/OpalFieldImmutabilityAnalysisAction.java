package de.uka.ilkd.key.gui.actions;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.staticanalysis.StaticAnalysisSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpalFieldImmutabilityAnalysisAction extends MainWindowAction {

    public OpalFieldImmutabilityAnalysisAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use Immutability Analyis");
        setTooltip("If ticked, Opal's Field Immutability Analysis is executed");
        setSelected(StaticAnalysisSettings.getINST().useFieldImmutabilityAnalysis());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.getINST().setUseFieldImmutabilityAnalysis(b);
    }

}