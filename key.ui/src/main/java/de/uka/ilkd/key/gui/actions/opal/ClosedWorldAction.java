package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ClosedWorldAction extends MainWindowAction {

    public ClosedWorldAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use Closed World Assumption");
        setTooltip("If ticked, Opal's uses closed world assumption");
        setSelected(StaticAnalysisSettings.useCloseWorldAssumption);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.useCloseWorldAssumption = b;
    }

}
