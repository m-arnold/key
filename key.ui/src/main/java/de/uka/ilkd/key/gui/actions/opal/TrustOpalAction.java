package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TrustOpalAction extends MainWindowAction {

    public TrustOpalAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Trust Opal");
        setTooltip("If ticked, Opal's analysis results are regarded correct!");
        setSelected(StaticAnalysisSettings.trustOpal());
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.setTrustOpal(b);
    }
}
