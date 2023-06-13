package de.uka.ilkd.key.staticanalysis;

import java.util.HashMap;

public class FieldImmutabilityResult {

    public HashMap<String, String> result;

    public FieldImmutabilityResult(HashMap<String, String> result) {
        this.result = result;
    }

    public boolean isImmutable(String className, String fieldName) {
        if (className == null || fieldName == null) {
            return false;
        }
        return fieldName.equals(result.get(className));
    }
}