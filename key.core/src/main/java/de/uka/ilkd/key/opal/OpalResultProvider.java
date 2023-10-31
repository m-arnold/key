package de.uka.ilkd.key.opal;

import de.uka.ilkd.key.logic.Term;
import de.uka.ilkd.key.logic.TermBuilder;
import de.uka.ilkd.key.logic.op.ProgramVariable;
import de.uka.ilkd.key.opal.result.FieldImmutabilityResult;
import de.uka.ilkd.key.opal.result.MethodPurityResult;
import de.uka.ilkd.key.opal.result.ThrownExceptionsResult;
import org.apache.commons.lang3.NotImplementedException;
import org.key_project.util.collection.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides results of executed Static Analyses
 */
public class OpalResultProvider {

    private static OpalResultProvider INST;

    private FieldImmutabilityResult fieldImmutabilityResult;
    private MethodPurityResult methodPurityResult;
    private ThrownExceptionsResult thrownExceptionsResult;

    private boolean compilationFailed;

    private List<String> compileErrors;

    public static OpalResultProvider getINST() {
        if (INST == null) {
            INST = new OpalResultProvider();
        }
        return INST;
    }

    public void setFieldImmutabilityResult(FieldImmutabilityResult fieldImmutabilityResult) {
        this.fieldImmutabilityResult = fieldImmutabilityResult;
    }
    public FieldImmutabilityResult getFieldImmutabilityResult() {
        return fieldImmutabilityResult;
    }

    public void setMethodPurityResult(MethodPurityResult methodPurityResult) {
        this.methodPurityResult = methodPurityResult;
    }
    public MethodPurityResult getMethodPurityResult() {
        return methodPurityResult;
    }

    public void setThrownExceptionsResult(ThrownExceptionsResult thrownExceptionsResult) {
        this.thrownExceptionsResult = thrownExceptionsResult;
    }

    public ThrownExceptionsResult getThrownExceptionsResult() {
        return this.thrownExceptionsResult;
    }

    public boolean hasCompilationFailed() {
        return compilationFailed;
    }

    public void addCompileError(String errorMsg) {
        if (compileErrors == null) {
            compileErrors = new ArrayList<>();
            compilationFailed = true;
        }
        compileErrors.add(errorMsg);
    }

    public List<String> getCompileErrors() {
        return compileErrors;
    }

    public boolean isImmutableField(String className, String fieldName) {
        if (fieldImmutabilityResult == null) {
            return false;
        }
        return fieldImmutabilityResult.isImmutable(className, fieldName);
    }

    public boolean isSideEffectFree(String className, String methodName) {
        if (methodPurityResult == null) {
            return false;
        }
        return methodPurityResult.isSideEffectFree(className, methodName);
    }

    public boolean isPureMethod(String className, String methodName) {
        if (methodPurityResult == null) {
            return false;
        }
        return methodPurityResult.isPure(className, methodName);
    }

    public boolean isContextOrExternalPure(String classe, String methodenname) {
        // ToDO: Determine if needed. If so, implement!
        throw new NotImplementedException();
    }

    public void resetResults() {
        fieldImmutabilityResult = null;
        methodPurityResult = null;
        thrownExceptionsResult = null;
        compileErrors = null;
        compilationFailed = false;
    }

    public boolean hasResult() {
        return fieldImmutabilityResult != null || methodPurityResult != null || thrownExceptionsResult != null;
    }

    public String getJMLAssignableExpr(String className, String methodName, List<String> paramNames) {
        if (methodPurityResult == null) {
            return "";
        }
        return methodPurityResult.getJMLAssignableExpr(className, methodName, paramNames);
    }

    public Term getAssignableTerm(String classname, String methodname, TermBuilder tb, ProgramVariable selfvar, ImmutableList<ProgramVariable> parameters) {
        if (methodPurityResult == null) {
            return tb.allLocs();
        }
        return methodPurityResult.getAssignableTerm(classname, methodname, tb, selfvar, parameters);
    }
}
