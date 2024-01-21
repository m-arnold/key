package de.uka.ilkd.key.gui.actions.opal;

import de.uka.ilkd.key.core.KeYSelectionEvent;
import de.uka.ilkd.key.core.KeYSelectionListener;
import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.gui.actions.MainWindowAction;
import de.uka.ilkd.key.logic.Choice;
import de.uka.ilkd.key.opal.OpalResultProvider;
import de.uka.ilkd.key.opal.result.ThrownExceptionsResult;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Set;

public class OpalShowTacletOptionSuggestionsAction extends MainWindowAction {

    private final String BAN = "ban";
    private final String IGNORE = "ignore";
    private final String ALLOW = "allow";

    public OpalShowTacletOptionSuggestionsAction(MainWindow mainWindow) {
        super(mainWindow);
        init();
        setName("Taclet Option Suggestions");
        setTooltip("Provides Taclet Option Suggestions based on Opal Analyses results.");
        setEnabled(false);
    }

    private void init() {
        final KeYSelectionListener selListener = new KeYSelectionListener() {
            public void selectedNodeChanged(KeYSelectionEvent e) {
                setEnabled(true);
            }
        };
        getMediator().addKeYSelectionListener(selListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        showSuggestions();
    }

    private String retrieveRuntimeExceptionOption(Set<Choice> set) {
        for (Choice c: set) {
            if (c.category().equals("runtimeExceptions")) {
               return c.toString().split(":")[1];
            }
        }
        return "";
    }

    private void showSuggestions() {
        ArrayList<Object> formattedResults = new ArrayList<>();

        ThrownExceptionsResult thrownExceptionsResult = OpalResultProvider.getINST().getThrownExceptionsResult();
        if (thrownExceptionsResult != null) {
            String tacletOption =
                    retrieveRuntimeExceptionOption(getMediator().getServices().getProof().getInitConfig().getActivatedChoices().toSet());
            if (thrownExceptionsResult.suggestRuntimeExceptionBan() && ALLOW.equals(tacletOption)) {
                formattedResults.add("No for KeY relevant runtime exceptions found by Opal.");
                formattedResults.add("Consider using runtimeExceptions:ban for an easier proof.");
                formattedResults.add("If you trust Opal's analysis results you may use runtimeExceptions:ignore (unsound Java modeling)");
                formattedResults.add("\n");
            }
        }

        if (formattedResults.isEmpty()) {
            formattedResults.add("No suggestions.");
        }

        Object[] formattedResult = formattedResults.toArray();
        JOptionPane.showMessageDialog(mainWindow, formattedResult, "Taclet Option Suggestions", JOptionPane.INFORMATION_MESSAGE);
    }



}
