package de.uka.ilkd.key.opal;

import de.uka.ilkd.key.java.Services;
import de.uka.ilkd.key.ldt.LocSetLDT;
import de.uka.ilkd.key.logic.Term;
import de.uka.ilkd.key.logic.TermBuilder;
import de.uka.ilkd.key.logic.op.IProgramMethod;
import de.uka.ilkd.key.logic.op.ProgramVariable;
import de.uka.ilkd.key.opal.result.MethodPurityLevel;
import de.uka.ilkd.key.opal.result.MethodPurityResult;
import org.key_project.util.collection.ImmutableList;

import java.util.HashSet;
import java.util.List;

public class FrameOptimizer {

    private static FrameOptimizer INST;

    private OpalResultProvider resProvider = OpalResultProvider.getINST();

    private HashSet<IProgramMethod> needsProof = new HashSet<>();

    public static FrameOptimizer INST() {
        if (INST == null) {
            INST = new FrameOptimizer();
        }
        return INST;
    }

    public void reset() {
        needsProof = new HashSet<>();
    }

    public String getJMLAssignableExpr(String className, String methodName, List<String> paramNames) {
        MethodPurityResult result = resProvider.getMethodPurityResult();
        if (resProvider.getMethodPurityResult() == null) {
            return "";
        }
        MethodPurityLevel level = result.getMethodPurity(className, methodName);
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

    public String getJMLAccessibleExpr(String className, String methodName) {
        MethodPurityResult result = resProvider.getMethodPurityResult();
        if (result == null) {
            return "";
        }
        MethodPurityLevel level = result.getMethodPurity(className, methodName);
        if (level != null) {
            switch(level) {
                case CompileTimePure:
                case Pure:
                case ExternallyPure:
                case ContextuallyPure:
                case DPure:
                case DExternallyPure:
                case DContextuallyPure:
                    return "\\nothing";
            }
        }
        return "";
    }

    private Term getAssignableTerm(String classname, String methodname, TermBuilder tb, ProgramVariable selfvar, ImmutableList<ProgramVariable> parameters) {
        MethodPurityResult result = resProvider.getMethodPurityResult();
        if (result == null) {
            return tb.allLocs();
        }
        MethodPurityLevel level = result.getMethodPurity(classname, methodname);
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

    public Term getAccessibleTerm(String classname, String methodname, TermBuilder tb) {
        MethodPurityResult result = resProvider.getMethodPurityResult();
        if (result == null) {
            return tb.allLocs();
        }
        MethodPurityLevel level = result.getMethodPurity(classname, methodname);
        if (level != null) {
            switch(level) {
                case CompileTimePure:
                case Pure:
                case ExternallyPure:
                case ContextuallyPure:
                case DPure:
                case DExternallyPure:
                case DContextuallyPure:
                    return tb.empty();
            }
        }
        return tb.allLocs();
    }

    public Term optimizeAssignable(Term userAssignable, Services services, IProgramMethod method, TermBuilder tb, ProgramVariable selfVar, ImmutableList<ProgramVariable> paramVars) {
        Term opalAssignable = getAssignableTerm(method.getContainerType().getName(), method.getName(), tb, selfVar, paramVars);
        determineProofNeeded(services, method, opalAssignable, userAssignable);
        switch (StaticAnalysisSettings.getAssignableGenerationMode()) {
            case Intersect:
                return tb.intersect(userAssignable, opalAssignable);
            default:
                return opalAssignable;
        }
    }

    public Term optimizeAccessible(Term userAccessible, IProgramMethod method, TermBuilder tb) {
        return tb.intersect(userAccessible, getAccessibleTerm(method.getContainerType().getName(), method.getName(), tb));
    }

    private void determineProofNeeded(Services services, IProgramMethod method, Term opalAssignable, Term userAssignable) {
        if (StaticAnalysisSettings.isModeIntersect() && !isSubset(services, opalAssignable, userAssignable)) {
            needsProof.add(method);
        }
    }

    public boolean assignableClauseRemovable(IProgramMethod method) {
        return StaticAnalysisSettings.trustOpal() && !needsProof.contains(method);
    }

    private boolean isSubset(Services services, Term opalAssignable, Term userAssignable) {
        final LocSetLDT ldt = services.getTypeConverter().getLocSetLDT();
        if (opalAssignable == null || userAssignable == null) {
            return false;
        }
        // ToDo: Make this smarter!
        if ( userAssignable.op() == ldt.getAllLocs() ||
                opalAssignable.op() == ldt.getEmpty()) {
            return true;
        }

        return false;
    }

}
