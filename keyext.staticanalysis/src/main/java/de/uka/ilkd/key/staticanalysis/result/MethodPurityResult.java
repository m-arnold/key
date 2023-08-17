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
            MethodPurityLevel level;
            try {
                level = MethodPurityLevel.valueOf(s[2]);
            } catch (IllegalArgumentException e) {
                System.out.println("Method Purity Level " + s[2] + " could not be found in corresponding enum");
                return false;
            }
            if (s[0].equals(className) && s[1].equals(methodName) && (
                    level == MethodPurityLevel.Pure ||
                    level == MethodPurityLevel.SideEffectFree ||
                    level == MethodPurityLevel.CompileTimePure)) {
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
            MethodPurityLevel level;
            try {
                level = MethodPurityLevel.valueOf(s[2]);
            } catch (IllegalArgumentException e) {
                System.out.println("Method Purity Level " + s[2] + " could not be found in corresponding enum");
                return false;
            }
            if (s[0].equals(className) && s[1].equals(methodName) && (
                    level == MethodPurityLevel.Pure || level == MethodPurityLevel.CompileTimePure)) {
                return true;
            }
        }
        return false;
    }
}
