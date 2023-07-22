package de.uka.ilkd.key.staticanalysis.result;

import java.util.ArrayList;

public class MethodPurityResult {

    // TODO: Use something better than String[]
    public ArrayList<String[]> result;

    public MethodPurityResult(ArrayList<String[]> result) {
        this.result = result;
    }

    public boolean isSideEffectFree(String className, String methodName) {
        if (className == null || methodName == null) {
            return false;
        }
        for (String[] s : result) {
            PurityLevel level = PurityLevel.valueOf(s[2]);
            if (s[0].equals(className) && s[1].equals(methodName) && (
                    level == PurityLevel.Pure ||
                    level == PurityLevel.SideEffectFree ||
                    level == PurityLevel.CompileTimePure)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPure(String className, String methodName) {
        if (className == null || methodName == null) {
            return false;
        }
        for (String[] s : result) {
            PurityLevel level = PurityLevel.valueOf(s[3]);
            if (s[0].equals(className) && s[1].equals(methodName) && (
                    level == PurityLevel.Pure || level == PurityLevel.CompileTimePure)) {
                return true;
            }
        }
        return false;
    }
}
