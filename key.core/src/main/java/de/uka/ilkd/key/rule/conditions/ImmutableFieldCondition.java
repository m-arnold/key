package de.uka.ilkd.key.rule.conditions;

import de.uka.ilkd.key.java.JavaTools;
import de.uka.ilkd.key.java.Services;
import de.uka.ilkd.key.java.StatementBlock;
import de.uka.ilkd.key.java.statement.MethodFrame;
import de.uka.ilkd.key.logic.JavaBlock;
import de.uka.ilkd.key.logic.Term;
import de.uka.ilkd.key.logic.TermImpl;
import de.uka.ilkd.key.logic.op.*;
import de.uka.ilkd.key.logic.sort.Sort;
import de.uka.ilkd.key.rule.VariableConditionAdapter;
import de.uka.ilkd.key.rule.inst.SVInstantiations;
import de.uka.ilkd.key.staticanalysis.OpalResultProvider;

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
        if (o instanceof LocationVariable) {
            // ToDo: Futher discuss this solution...
            if (duringPrepareEnter(instMap)) {
                return false != negated;
            }
            return (((LocationVariable)o).isImmutable()) != negated;
        }

        if (o instanceof Term) {
            Operator op = ((Term) o).op();
            if (op instanceof Function) {
                String[] splitted = op.name().toString().split("::[$]");
                if (splitted.length < 2) {
                    return false != negated;
                }
                ProgramVariable attribute = services.getJavaInfo().getAttribute(splitted[1], splitted[0]);
                return attribute.isImmutable() != negated;
            }
        }
        return false;
    }

    public String toString() {
        return (negated ? "\\not" : "") + "\\isImmutableField(" + field + ")";
    }
    private boolean duringPrepareEnter(SVInstantiations instMap) {
        return instMap.getExecutionContext() != null
                && instMap.getExecutionContext().getMethodContext() != null
                && instMap.getExecutionContext().getMethodContext().toString().contains("<prepareEnter>");
    }

}
