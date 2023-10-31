package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.core.KeYSelectionEvent;
import de.uka.ilkd.key.core.KeYSelectionListener;
import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.opal.result.*;
import de.uka.ilkd.key.opal.result.ThrownExceptionsResult.RuntimeException;
import de.uka.ilkd.key.opal.OpalResultProvider;
import de.uka.ilkd.key.util.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Set;

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
                setEnabled(OpalResultProvider.getINST().hasResult() &&
                        !OpalResultProvider.getINST().hasCompilationFailed());
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

            for (Pair<String,String> key : fieldImmutabilityResult.result.keySet()) {
                FieldImmutabilityLevel level = fieldImmutabilityResult.result.get(key);
                formattedResults.add(key.first + "." + key.second + " : " + level);
            }
        }
        formattedResults.add("\n");

        MethodPurityResult methodPurityResult = OpalResultProvider.getINST().getMethodPurityResult();
        if (methodPurityResult != null) {
            formattedResults.add("Method Purity Analysis:");
            formattedResults.add("\n");

            for (Pair<String,String> key: methodPurityResult.result.keySet()) {
                MethodPurityLevel level = methodPurityResult.result.get(key);
                formattedResults.add((key.first + "." + key.second + "(...)" + " : " + level ));
            }
        }

        ThrownExceptionsResult thrownExceptionsResult = OpalResultProvider.getINST().getThrownExceptionsResult();
        if (thrownExceptionsResult != null) {
            formattedResults.add("RuntimeException Analysis:");
            formattedResults.add("\n");

            for (Pair<String,String> key: thrownExceptionsResult.result.keySet()) {
                Set<RuntimeException> set = thrownExceptionsResult.result.get(key);
                formattedResults.add(key.first + "." + key.second + "(...)" + " throws:");
                for (RuntimeException e : set) {
                    formattedResults.add(""+ e.toString());
                }
                formattedResults.add("\n");
            }
        }


        Object[] formattedResult = formattedResults.toArray();
        JOptionPane.showMessageDialog(mainWindow, formattedResult, "Opal Results", JOptionPane.INFORMATION_MESSAGE);
    }
}
