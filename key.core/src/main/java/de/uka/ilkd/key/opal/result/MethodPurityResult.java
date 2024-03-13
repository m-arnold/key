package de.uka.ilkd.key.opal.result;

import de.uka.ilkd.key.util.Pair;

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

    public MethodPurityLevel getMethodPurity(String className, String methodName) {
        return result.get(new Pair<>(className, methodName));
    }

    // Wahrscheinlich muss ich die anderen beiden Hilfsmethoden auch noch so machen...
    public boolean containsPureMethod() {
        /*for (Pair<String, String> key : result.keySet()) {
            if (key.first.equals(className)) {
                MethodPurityLevel l  = result.get(key);
                if (l.equals(Pure) || l.equals(CompileTimePure)) {
                    return true;
                }
            }
        }
        return false;*/
        return result.values().contains(Pure)
                || result.values().contains(CompileTimePure);
    }

    public boolean containsAtLeastContextPure() {
        return result.values().contains(Pure)
                || result.values().contains(CompileTimePure)
                || result.values().contains(ExternallyPure)
                || result.values().contains(ContextuallyPure)
                || result.values().contains(DExternallyPure)
                || result.values().contains(DContextuallyPure);
    }

    public boolean containsAtLeastContextSideeffectFree() {
        return result.values().contains(Pure)
                || result.values().contains(CompileTimePure)
                || result.values().contains(SideEffectFree)
                || result.values().contains(ExternallyPure)
                || result.values().contains(ExternallySideEffectFree)
                || result.values().contains(ContextuallyPure)
                || result.values().contains(ContextuallySideEffectFree);
    }
}
