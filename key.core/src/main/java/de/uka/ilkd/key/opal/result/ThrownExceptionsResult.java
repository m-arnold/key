package de.uka.ilkd.key.opal.result;

import de.uka.ilkd.key.util.Pair;

import java.util.Map;
import java.util.Set;

public class ThrownExceptionsResult {

    /**
     * Key: Pair of (className, methodName)
     * Value: Set containing RuntimeException enum values
     * */
    public Map<Pair<String, String>, Set<RuntimeException>> result;

    public ThrownExceptionsResult(Map<Pair<String, String>, Set<RuntimeException>> result) {
        this.result = result;
    }

    public enum RuntimeException {
        ArithmeticException,
        ArrayStoreException,
        ClassCastException,
        EnumConstantNotPresentException,
        IllegalArgumentException,
        IllegalThreadStateException,
        NumberFormatException,
        IllegalCallerException,
        IllegalMonitorStateException,
        llegalStateException,
        IndexOutOfBoundsException,
        ArrayIndexOutOfBoundsException,
        StringIndexOutOfBoundsException,
        LayerInstantiationException,
        NegativeArraySizeException,
        NullPointerException,
        SecurityException,
        TypeNotPresentException,
        UnsupportedOperationException
    }
}
