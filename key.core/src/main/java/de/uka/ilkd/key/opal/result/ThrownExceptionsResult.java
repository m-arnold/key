package de.uka.ilkd.key.opal.result;

import de.uka.ilkd.key.util.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ThrownExceptionsResult {
    final static Set<RuntimeException> RELEVANT_FOR_KEY = new HashSet<>(Arrays.asList(
            RuntimeException.ArithmeticException,
            RuntimeException.ArrayStoreException,
            RuntimeException.ArrayIndexOutOfBoundsException,
            RuntimeException.NegativeArraySizeException,
            RuntimeException.NullPointerException));

    /**
     * Key: Pair of (className, methodName)
     * Value: Set containing RuntimeException enum values
     * */
    public Map<Pair<String, String>, Set<RuntimeException>> result;

    public ThrownExceptionsResult(Map<Pair<String, String>, Set<RuntimeException>> result) {
        this.result = result;
    }

    public boolean suggestRuntimeExceptionBan() {
        Set<RuntimeException> union = new HashSet<>();
        result.values().forEach(set -> union.addAll(set));
        union.retainAll(RELEVANT_FOR_KEY);
        return union.isEmpty();
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
