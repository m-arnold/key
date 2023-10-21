package de.uka.ilkd.key.opal.result;

import de.uka.ilkd.key.logic.Term;
import de.uka.ilkd.key.logic.TermBuilder;
import de.uka.ilkd.key.logic.op.ProgramVariable;
import de.uka.ilkd.key.util.Pair;
import org.key_project.util.collection.ImmutableList;

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

    public String getJMLAssignableExpr(String className, String methodName, List<String> paramNames) {
        MethodPurityLevel level = result.get(new Pair<>(className, methodName));
        if (level != null) {
            switch (level) {
                case CompileTimePure:
                case Pure:
                case SideEffectFree:
                    return "\\nothing";
                case ExternallyPure:
                case ExternallySideEffectFree:
                    return  "this.*";
                case ContextuallyPure:
                case ContextuallySideEffectFree:
                    String res = "this.*";
                    for (String parameterName: paramNames) {
                        res = "\\set_union(" + res + "," + parameterName + ".*)";
                    }
                    return res;
            }
        }
        return "";
    }

    public Term getAssignableTerm(String className, String methodName, TermBuilder tb, ProgramVariable selfvar, ImmutableList<ProgramVariable> parameters) {
        MethodPurityLevel level = result.get(new Pair<>(className, methodName));
        if (level != null) {
            switch(level) {
                case CompileTimePure:
                case Pure:
                case SideEffectFree:
                    return tb.empty();
                case ExternallyPure:
                case ExternallySideEffectFree:
                    return tb.allFields(tb.var(selfvar));
                case ContextuallyPure:
                case ContextuallySideEffectFree:
                    Term res = tb.allFields(tb.var(selfvar));
                    for (ProgramVariable var: parameters) {
                        res = tb.union(res,tb.allFields(tb.var(var)));
                    }
                    return res;
            }
        }
        return tb.allLocs();
    }
}
