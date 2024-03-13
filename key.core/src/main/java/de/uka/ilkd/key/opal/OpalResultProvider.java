package de.uka.ilkd.key.opal;

import de.uka.ilkd.key.opal.result.FieldImmutabilityResult;
import de.uka.ilkd.key.opal.result.MethodPurityResult;
import de.uka.ilkd.key.opal.result.ThrownExceptionsResult;
import org.apache.commons.lang3.NotImplementedException;

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
        FrameOptimizer.INST().reset();
    }

    public boolean hasResult() {
        return fieldImmutabilityResult != null || methodPurityResult != null || thrownExceptionsResult != null;
    }

    public String hasUsableResult() {
        if (StaticAnalysisSettings.useRevisedHeapTheory()){
            if (fieldImmutabilityResult == null) {
                return "0";
            }
            if (fieldImmutabilityResult.hasUseableResult()) {
                return "1";
            }
        } else {
            if (methodPurityResult == null) {
                return "0";
            }
            if ((StaticAnalysisSettings.useHeapParameterRemoval() && methodPurityResult.containsPureMethod()) ||
                    (StaticAnalysisSettings.useAssignableClauseOptimization() && methodPurityResult.containsAtLeastContextSideeffectFree()) ||
                    (StaticAnalysisSettings.useAccessibleClauseOptimization() && methodPurityResult.containsAtLeastContextPure()) ){
                return "1";
            }
        }
        return "0";
    }
}
