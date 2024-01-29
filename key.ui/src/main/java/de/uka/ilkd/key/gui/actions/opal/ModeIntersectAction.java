package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.StaticAnalysisSettings;

import java.awt.event.ActionEvent;

public class ModeIntersectAction extends MainWindowAction {

    public ModeIntersectAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Use intersect");
        setTooltip("If ticked, Opal's uses intersect mode");
        setSelected(StaticAnalysisSettings.getAssignableGenerationMode() == StaticAnalysisSettings.AssignableClauseGenerateMode.Intersect);
        setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StaticAnalysisSettings.setAssignableGenerationMode(StaticAnalysisSettings.AssignableClauseGenerateMode.Intersect);
    }
}
