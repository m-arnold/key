package de.uka.ilkd.key.staticanalysis.result;

import java.util.ArrayList;

public class FieldImmutabilityResult {

    public ArrayList<String[]> result;

    public FieldImmutabilityResult(ArrayList<String[]> result) {
        this.result = result;
    }

    public boolean isImmutable(String className, String fieldName) {
        if (className == null || fieldName == null) {
            return false;
        }
        for (String[] s : result) {
            if (s[0].equals(className) && s[1].equals(fieldName) && "TransitivelyImmutableField".equals(s[2])) {
                return true;
            }
        }
        return false;
    }
}