package de.uka.ilkd.key.gui.interactionlog.model.builtin;

import de.uka.ilkd.key.gui.interactionlog.algo.InteractionVisitor;
import de.uka.ilkd.key.proof.Node;
import de.uka.ilkd.key.rule.LoopContractInternalBuiltInRuleApp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alexander Weigl
 * @version 1 (09.12.18)
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LoopContractInternalBuiltInRuleInteraction extends BuiltInRuleInteraction{
    public LoopContractInternalBuiltInRuleInteraction() {
    }

    public LoopContractInternalBuiltInRuleInteraction(LoopContractInternalBuiltInRuleApp app, Node node) {
    }

    @Override
    public <T> T accept(InteractionVisitor<T> visitor) {
        return visitor.visit(this);
    }
}