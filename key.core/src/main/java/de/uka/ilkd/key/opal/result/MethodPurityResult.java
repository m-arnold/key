package de.uka.ilkd.key.opal.result;

import de.uka.ilkd.key.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.uka.ilkd.key.opal.result.MethodPurityLevel.*;

public class MethodPurityResult {

    /**
     * Key: Pair of (className, methodName)
     * Value: Corresponding Method Purity Level
     * */
    public Map<Pair<String, String>, MethodPurityLevel> result;


    public MethodPurityResult(Map<Pair<String, String>, MethodPurityLevel> result) {
        this.result = result;
    }

    public boolean isSideEffectFree(String className, String methodName) {
        if (className == null || methodName == null) {
            return false;
        }
        MethodPurityLevel level = result.get(new Pair<>(className, methodName));
        return level != null && (level == Pure || level == SideEffectFree || level == CompileTimePure);
    }

    public boolean isPure(String className, String methodName) {
        if (className == null || methodName == null) {
            return false;
        }
        MethodPurityLevel level = result.get(new Pair<>(className, methodName));
        return level != null && (level == Pure || level == CompileTimePure);
    }

    public List<String> getJMLAssignableExprs(String className, String methodName, List<String> paramNames) {
        List<String> res = new ArrayList<>();
        MethodPurityLevel level = result.get(new Pair<>(className, methodName));
        if (level != null) {
            switch (level) {
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
