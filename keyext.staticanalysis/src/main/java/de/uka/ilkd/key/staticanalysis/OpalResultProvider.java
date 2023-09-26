package de.uka.ilkd.key.staticanalysis;

import de.uka.ilkd.key.staticanalysis.result.FieldImmutabilityResult;
import de.uka.ilkd.key.staticanalysis.result.MethodPurityResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides results of executed Static Analyses
 */
public class OpalResultProvider {

    private static OpalResultProvider INST;

    private FieldImmutabilityResult fieldImmutabilityResult;
    private MethodPurityResult methodPurityResult;

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

    public void resetResults() {
        fieldImmutabilityResult = null;
        methodPurityResult = null;
        compileErrors = null;
        compilationFailed = false;
    }

    public boolean hasResult() {
        return fieldImmutabilityResult != null || methodPurityResult != null;
    }
}
