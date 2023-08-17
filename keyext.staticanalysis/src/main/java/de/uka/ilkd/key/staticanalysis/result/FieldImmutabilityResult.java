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
            FieldImmutabilityLevel level;
            try {
                level = FieldImmutabilityLevel.valueOf(s[2]);
            } catch (IllegalArgumentException e){
                    System.out.println("Field immutability level " + s[2] + " could not be found in corresponding enum");
                    return false;
            }
            if (s[0].equals(className) && s[1].equals(fieldName) &&
                    level == FieldImmutabilityLevel.TransitivelyImmutableField) {
                return true;
            }
        }
        return false;
    }
}