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

    public String getJMLAccessibleExpr(String className, String methodName, List<String> paramNames) {
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
                    String res = "\\nothing";
                    for (String parameterName: paramNames) {
                        res = "\\set_union(" + res + "," + parameterName + ".*)";
                    }
                    return res;
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
                        if (!isPrimitive(var.sort().toString())) {
                            res = tb.union(res,tb.allFields(tb.var(var)));
                        }
                    }
                    return res;
            }
        }
        return tb.allLocs();
    }

    private boolean isPrimitive(String type) {
        return "int".equals(type)
                || "float".equals(type)
                || "double".equals(type)
                || "boolean".equals(type)
                || "char".equals(type)
                || "byte".equals(type)
                || "short".equals(type);
    }


    public Term getAccessibleTerm(String classname, String methodname, TermBuilder tb, ImmutableList<ProgramVariable> paramVars) {
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
                    Term res = tb.empty();
                    for (ProgramVariable var: paramVars) {
                        if (!isPrimitive(var.sort().toString())) {
                            res = tb.union(res,tb.allFields(tb.var(var)));
                        }
                    }
                    return res;
            }
        }
        return tb.allLocs();
    }

    public Term optimizeAssignable(Term userAssignable, Services services, IProgramMethod method, TermBuilder tb, ProgramVariable selfVar, ImmutableList<ProgramVariable> paramVars) {
        Term opalAssignable = getAssignableTerm(method.getContainerType().getName(), method.getName(), tb, selfVar, paramVars);
        determineProofNeeded(services, method, tb, opalAssignable, userAssignable);
        switch (StaticAnalysisSettings.getAssignableGenerationMode()) {
            case Intersect:
                return tb.intersect(userAssignable, opalAssignable);
            default:
                /* Todo: Mit Richard besprechen....
                if (opalAssignable.equals(tb.allLocs())) {
                    return userAssignable;
                }*/
                return opalAssignable;
        }
    }

    public Term optimizeAccessible(Term userAccessible, IProgramMethod method, TermBuilder tb, ImmutableList<ProgramVariable> paramVars) {
        return tb.intersect(userAccessible, getAccessibleTerm(method.getContainerType().getName(), method.getName(), tb, paramVars));
    }

    private void determineProofNeeded(Services services, IProgramMethod method, TermBuilder tb, Term opalAssignable, Term userAssignable) {
        if ((StaticAnalysisSettings.isModeIntersect() && !isSubset(services, tb, opalAssignable, userAssignable))
                || !StaticAnalysisSettings.trustOpal()
            /*|| (StaticAnalysisSettings.isModeReplace() && opalAssignable.equals(tb.allLocs()))*/) { // ToDo: Mit Richard besprechen.
            needsProof.add(method);
        }
    }

    public void addAssignableNeedsProof(IProgramMethod method) {
        needsProof.add(method);
    }

    public boolean assignableClauseRemovable(IProgramMethod method) {
        return StaticAnalysisSettings.trustOpal() && !needsProof.contains(method);
    }

    private boolean isSubset(Services services, TermBuilder tb, Term opalAssignable, Term userAssignable) {
        final LocSetLDT ldt = services.getTypeConverter().getLocSetLDT();

        if (opalAssignable == null || userAssignable == null) {
            return false;
        }

        // This could be done more accurately, but is sufficient for the assignable
        // clauses we generated based on OPIUM's results
        return userAssignable.op() == ldt.getAllLocs()                                                      // No specified assignable clause (implicit everything)
                || userAssignable.equals(tb.setMinus(tb.allLocs(), tb.freshLocs(tb.getBaseHeap())))         // User specified \everything
                || opalAssignable.op() == ldt.getEmpty();                                                   // Opal determined \nothing;
    }

}
