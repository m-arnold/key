package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.core.KeYSelectionEvent;
import de.uka.ilkd.key.core.KeYSelectionListener;
import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.OpalResultProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ShowCompileErrorAction extends MainWindowAction {

    public ShowCompileErrorAction(MainWindow mainWindow) {
        super(mainWindow);
        init();
        setName("Show Compile Errors");
        setEnabled(false);
    }

    private void init() {
        final KeYSelectionListener selListener = new KeYSelectionListener() {
            public void selectedNodeChanged(KeYSelectionEvent e) {
                setEnabled(OpalResultProvider.getINST().hasCompilationFailed());
            }
        };
        getMediator().addKeYSelectionListener(selListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        showResults();
    }

    public void showResults() {
        ArrayList<Object> formattedResults = new ArrayList<>();

        formattedResults.add("No Opal Analyses are executed, due to the following compile errors : \n");
        formattedResults.add("\n");

        for (String errorMsg: OpalResultProvider.getINST().getCompileErrors()) {
            formattedResults.add(errorMsg);
            formattedResults.add("\n");
        }

        Object[] formattedResult = formattedResults.toArray();
        JOptionPane.showMessageDialog(mainWindow, formattedResult, "Compilation Errors", JOptionPane.INFORMATION_MESSAGE);
    }

}
