package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpalMethodPuritySetUseCaseHeapParameterRemoval extends MainWindowAction {
    public OpalMethodPuritySetUseCaseHeapParameterRemoval(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use heap parameter removal");
        setTooltip("If ticked, Method purity information are used to remove heap parameters from pure method queries");
        setSelected(StaticAnalysisSettings.useHeapParameterRemoval());
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        StaticAnalysisSettings.setUseHeapParameterRemoval(b);
    }

}
