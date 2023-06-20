package de.uka.ilkd.key.staticanalysis;

/**
 * Provides results of executed Static Analyses
 */
public class OpalResultProvider {

    private static OpalResultProvider INST;

    private FieldImmutabilityResult fieldImmutabilityResult;

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

    public boolean isImmutableField(String className, String fieldName) {
        if (fieldImmutabilityResult == null) {
            return false;
        }
        return fieldImmutabilityResult.isImmutable(className, fieldName);
    }

    public void resetResults() {
        fieldImmutabilityResult = null;
    }

    public boolean hasResult() {
        return fieldImmutabilityResult != null;
    }
}
