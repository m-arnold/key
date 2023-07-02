package de.uka.ilkd.key.staticanalysis.runner;

import de.uka.ilkd.key.staticanalysis.result.FieldImmutabilityResult;
import de.uka.ilkd.key.staticanalysis.OpalResultProvider;
import de.uka.ilkd.key.staticanalysis.result.MethodPurityResult;
import org.opalj.br.fpcf.PropertyStoreKey$;
import org.opalj.br.fpcf.analyses.EagerL0PurityAnalysis$;
import org.opalj.br.fpcf.properties.Purity;
import org.opalj.br.fpcf.properties.Purity$;
import org.opalj.br.fpcf.properties.SimpleContext;
import org.opalj.fpcf.EPS;
import org.opalj.fpcf.FinalEP;
import org.opalj.fpcf.Property;
import org.opalj.tac.fpcf.analyses.fieldassignability.LazyL0FieldAssignabilityAnalysis;

import java.util.ArrayList;

public class MethodPurityAnalysisRunner  extends AbstractAnalysisRunner {
    public MethodPurityAnalysisRunner(String pathToJar) {
        super(pathToJar);
    }

    @Override
    void startAnalysis() {
        store = p.get(PropertyStoreKey$.MODULE$);
        LazyL0FieldAssignabilityAnalysis.register(p, store, null);
        EagerL0PurityAnalysis$.MODULE$.start(p, store, null);
    }

    @Override
    void evaluateResult() {
        ArrayList<String[]> result = new ArrayList<>();
        scala.collection.Iterator<EPS<Object, Purity>> scalaIterator = store.entities(Purity$.MODULE$.key());
        while (scalaIterator.hasNext()) {
            FinalEP<Object, Purity> finalEP = scalaIterator.next().toFinalEP();
            Property finalProperty = finalEP.p();

            SimpleContext ctx = (SimpleContext) finalEP.e();
            String className = ctx.method().declaringClassType().fqn();
            String methodName = ctx.method().name();
            String purityLevel = finalProperty.toString();
            result.add(new String[]{className, methodName, purityLevel});
        }
        OpalResultProvider.getINST().setMethodPurityResult(new MethodPurityResult(result));
    }
}

