package de.uka.ilkd.key.rule.conditions;

import de.uka.ilkd.key.java.Services;
import de.uka.ilkd.key.logic.op.*;
import de.uka.ilkd.key.rule.VariableConditionAdapter;
import de.uka.ilkd.key.rule.inst.SVInstantiations;

public class ImmutableFieldCondition extends VariableConditionAdapter {

    private final SchemaVariable field;
    private final boolean negated;

    public ImmutableFieldCondition(SchemaVariable field, boolean negated) {
        this.field = field;
        this.negated = negated;
    }

    public boolean isNegated() {
        return negated;
    }

    public boolean check(SchemaVariable var, SVSubstitute instCandidate, SVInstantiations instMap,
                         Services services) {
        final Object o = instMap.getInstantiation(field);
        if (!(o instanceof LocationVariable)) {
            return false;
        }
        return ((LocationVariable)o).isImmutable();
    }

    public String toString() {
        return (negated ? "\\not" : "") + "\\isImmutableField(" + field + ")";
    }
}
