package de.uka.ilkd.key.gui.actions;

import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.staticanalysis.FieldImmutabilityResult;
import de.uka.ilkd.key.staticanalysis.OpalResultProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpalShowResultsAction extends MainWindowAction {

    public OpalShowResultsAction(MainWindow mainWindow) {
        super(mainWindow);
        setName("Shows Opal results");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        showResults();
    }

    // TODO: "HACKY" Way... For the moment okay.
    public void showResults() {
        FieldImmutabilityResult result = OpalResultProvider.getINST().getFieldImmutabilityResult();
        Object[] formattedResult = new Object[result.result.size()+ 2];
        formattedResult[0] = "Transitively Immutable Field: \n";
        formattedResult[1] = "\n";
        int i = 2;
        for (String key: result.result.keySet()) {
            formattedResult[i] = key + "." + result.result.get(key) + "\n";
            i++;
        }
        JOptionPane.showMessageDialog(mainWindow, formattedResult, "Opal Results", JOptionPane.INFORMATION_MESSAGE);
    }


}
