package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;

import java.awt.event.ActionEvent;

public class ModeReplaceAction extends MainWindowAction {

    public ModeReplaceAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use Replace");
        setTooltip("If ticked, Opal's uses replace mode");
        setSelected(StaticAnalysisSettings.getAssignableGenerationMode() == StaticAnalysisSettings.AssignableClauseGenerateMode.Replace);
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StaticAnalysisSettings.setAssignableGenerationMode(StaticAnalysisSettings.AssignableClauseGenerateMode.Replace);
    }
}
