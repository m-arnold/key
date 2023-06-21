package de.uka.ilkd.key.staticanalysis;

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
            if (s[0].equals(className) && s[1].equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}