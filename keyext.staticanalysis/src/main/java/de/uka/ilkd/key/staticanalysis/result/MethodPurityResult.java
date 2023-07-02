package de.uka.ilkd.key.staticanalysis.result;

import java.util.ArrayList;

public class MethodPurityResult {

    public ArrayList<String[]> result;

    public MethodPurityResult(ArrayList<String[]> result) {
        this.result = result;
    }

    public boolean isPure(String className, String methodName) {
        if (className == null || methodName == null) {
            return false;
        }
        for (String[] s : result) {
            // Possibly use Enum for the last &&
            if (s[0].equals(className) && s[1].equals(methodName) && "Pure".equals(s[2])) {
                return true;
            }
        }
        return false;
    }

}
