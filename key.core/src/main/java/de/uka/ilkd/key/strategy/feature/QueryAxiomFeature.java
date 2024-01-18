package de.uka.ilkd.key.strategy.feature;

import de.uka.ilkd.key.logic.PosInOccurrence;
import de.uka.ilkd.key.logic.Term;
import de.uka.ilkd.key.logic.op.ProgramMethod;
import de.uka.ilkd.key.proof.Goal;
import de.uka.ilkd.key.rule.RuleApp;
import de.uka.ilkd.key.strategy.NumberRuleAppCost;
import de.uka.ilkd.key.strategy.RuleAppCost;

public class QueryAxiomFeature implements Feature {

    // Costs, if PM contains heap param
    private final long costsNoHeapRemoval;
    // Costs, if PM does not contain heap param
    private final long costsHeapRemoval;

    public QueryAxiomFeature(long costsNoHeapRemoval, long costsHeapRemoval) {
        this.costsNoHeapRemoval = costsNoHeapRemoval;
        this.costsHeapRemoval = costsHeapRemoval;
    }

    @Override
    public RuleAppCost computeCost(RuleApp app, PosInOccurrence pos, Goal goal, MutableState mState) {
        Term focus = pos.subTerm();
        if (focus.op() instanceof ProgramMethod pm) {
            // Without heap removal: pm(heap, self, p1,...)
            // If arity - numParams == 1, then only self in signature -> No heap.
            if (pm.arity() - pm.getNumParams() == 1) {
                return NumberRuleAppCost.create(costsHeapRemoval);
            }
        }
        return NumberRuleAppCost.create(costsNoHeapRemoval);
    }
}
