package de.uka.ilkd.key.opal.result;

import de.uka.ilkd.key.util.Pair;

import java.util.Map;

import static de.uka.ilkd.key.opal.result.FieldImmutabilityLevel.TransitivelyImmutableField;

public class FieldImmutabilityResult {

    public Map<Pair<String,String>, FieldImmutabilityLevel> result;

    public FieldImmutabilityResult(Map<Pair<String,String>, FieldImmutabilityLevel> result) {
        this.result = result;
    }

    public boolean isImmutable(String className, String fieldName) {
        if (className == null || fieldName == null) {
            return false;
        }
        FieldImmutabilityLevel level = result.get(new Pair<>(className, fieldName));
        return (level != null && level == TransitivelyImmutableField);
    }
}