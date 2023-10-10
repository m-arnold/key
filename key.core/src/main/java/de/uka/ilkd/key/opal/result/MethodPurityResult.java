package de.uka.ilkd.key.opal.result;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getJMLAssignableExprs(String className, String methodName, List<String> paramNames) {
        List<String> res = new ArrayList<>();
        String[] match = null;
        for (String[] arr: result) {
            // TODO: Use a different data structure for result!!
            if (arr[0].equals(className) && arr[1].equals(methodName)) {
                match = arr;
                break;
            }
        }
        if (match != null) {
            switch (MethodPurityLevel.valueOf(match[2])) {
                case CompileTimePure:
                case Pure:
                case SideEffectFree:
                    res.add("\\nothing");
                    break;
                case ExternallyPure:
                case ExternallySideEffectFree:
                    res.add("this.*");
                    break;
                case ContextuallyPure:
                case ContextuallySideEffectFree:
                    for (String parameterName: paramNames) {
                        res.add(parameterName + ".*");
                    }
                    res.add("this.*");
            }
        }
        return res;
    }
}
