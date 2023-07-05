package de.uka.ilkd.key.gui.actions;

import de.uka.ilkd.key.core.KeYSelectionEvent;
import de.uka.ilkd.key.core.KeYSelectionListener;
import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.staticanalysis.result.FieldImmutabilityResult;
import de.uka.ilkd.key.staticanalysis.OpalResultProvider;
import de.uka.ilkd.key.staticanalysis.result.MethodPurityResult;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class OpalShowResultsAction extends MainWindowAction {

    public OpalShowResultsAction(MainWindow mainWindow) {
        super(mainWindow);
        init();
        setName("Shows Opal results");
        setEnabled(false);
    }

    private void init() {
        final KeYSelectionListener selListener = new KeYSelectionListener() {
            public void selectedNodeChanged(KeYSelectionEvent e) {
                setEnabled(OpalResultProvider.getINST().hasResult());
            }
        };
        getMediator().addKeYSelectionListener(selListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        showResults();
    }

    // TODO: "HACKY" Way... For the moment okay.
    public void showResults() {
        ArrayList<Object> formattedResults = new ArrayList<>();
        FieldImmutabilityResult fieldImmutabilityResult = OpalResultProvider.getINST().getFieldImmutabilityResult();
        if (fieldImmutabilityResult != null) {
            formattedResults.add("Field Immutability Analysis: \n");
            formattedResults.add("\n");

            for (String[] s : fieldImmutabilityResult.result) {
                formattedResults.add(s[0] + "." + s[1] + " : " + s[2]);
            }
        }
        formattedResults.add("\n");

        MethodPurityResult methodPurityResult = OpalResultProvider.getINST().getMethodPurityResult();
        if (methodPurityResult != null) {
            formattedResults.add("Method Purity Analysis:");
            formattedResults.add("\n");

            for (String[] s: methodPurityResult.result) {
                formattedResults.add((s[0] + "." + s[1] + "(...)" + " : " + s[2] ));
            }
        }
        Object[] formattedResult = formattedResults.toArray();
        JOptionPane.showMessageDialog(mainWindow, formattedResult, "Opal Results", JOptionPane.INFORMATION_MESSAGE);
    }


}
