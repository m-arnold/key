package de.uka.ilkd.key.staticanalysis.runner;

import de.uka.ilkd.key.staticanalysis.OpalResultProvider;
import de.uka.ilkd.key.staticanalysis.result.MethodPurityResult;
import org.opalj.br.fpcf.FPCFAnalysesManager;
import org.opalj.br.fpcf.FPCFAnalysesManagerKey$;
import org.opalj.br.fpcf.FPCFAnalysis;
import org.opalj.br.fpcf.PropertyStoreKey$;
import org.opalj.br.fpcf.analyses.EagerL0PurityAnalysis$;
import org.opalj.br.fpcf.analyses.LazyL0CompileTimeConstancyAnalysis$;
import org.opalj.br.fpcf.analyses.LazyStaticDataUsageAnalysis$;
import org.opalj.br.fpcf.analyses.immutability.LazyClassImmutabilityAnalysis$;
import org.opalj.br.fpcf.analyses.immutability.LazyTypeImmutabilityAnalysis$;
import org.opalj.br.fpcf.properties.Purity;
import org.opalj.br.fpcf.properties.Purity$;
import org.opalj.br.fpcf.properties.SimpleContext;
import org.opalj.fpcf.ComputationSpecification;
import org.opalj.fpcf.EPS;
import org.opalj.fpcf.FinalEP;
import org.opalj.fpcf.Property;
import org.opalj.tac.cg.RTACallGraphKey$;
import org.opalj.tac.fpcf.analyses.LazyFieldImmutabilityAnalysis$;
import org.opalj.tac.fpcf.analyses.LazyFieldLocalityAnalysis$;
import org.opalj.tac.fpcf.analyses.escape.LazyInterProceduralEscapeAnalysis$;
import org.opalj.tac.fpcf.analyses.escape.LazyReturnValueFreshnessAnalysis$;
import org.opalj.tac.fpcf.analyses.escape.LazySimpleEscapeAnalysis$;
import org.opalj.tac.fpcf.analyses.fieldassignability.LazyL0FieldAssignabilityAnalysis$;
import org.opalj.tac.fpcf.analyses.fieldassignability.LazyL1FieldAssignabilityAnalysis$;
import org.opalj.tac.fpcf.analyses.fieldassignability.LazyL2FieldAssignabilityAnalysis$;
import org.opalj.tac.fpcf.analyses.purity.EagerL1PurityAnalysis$;
import org.opalj.tac.fpcf.analyses.purity.EagerL2PurityAnalysis$;
import scala.jdk.javaapi.CollectionConverters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodPurityAnalysisRunner  extends AbstractAnalysisRunner {

    private final List<ComputationSpecification<FPCFAnalysis>> L2_CONF = Arrays.asList(
            EagerL2PurityAnalysis$.MODULE$,
            LazyFieldImmutabilityAnalysis$.MODULE$,
            LazyL0CompileTimeConstancyAnalysis$.MODULE$,
            LazyStaticDataUsageAnalysis$.MODULE$,
            LazyReturnValueFreshnessAnalysis$.MODULE$,
            LazyFieldLocalityAnalysis$.MODULE$,
            LazyTypeImmutabilityAnalysis$.MODULE$,
            LazyClassImmutabilityAnalysis$.MODULE$,
            LazyInterProceduralEscapeAnalysis$.MODULE$,
            LazyL2FieldAssignabilityAnalysis$.MODULE$
    );

    private final List<ComputationSpecification<FPCFAnalysis>> L1_CONF = Arrays.asList(
            EagerL1PurityAnalysis$.MODULE$,
            LazyClassImmutabilityAnalysis$.MODULE$,
            LazyTypeImmutabilityAnalysis$.MODULE$,
            LazyInterProceduralEscapeAnalysis$.MODULE$,
            LazyL1FieldAssignabilityAnalysis$.MODULE$
    );

    private final List<ComputationSpecification<FPCFAnalysis>> L0_CONF = Arrays.asList(
            EagerL0PurityAnalysis$.MODULE$,
            LazyClassImmutabilityAnalysis$.MODULE$,
            LazyTypeImmutabilityAnalysis$.MODULE$,
            LazySimpleEscapeAnalysis$.MODULE$,
            LazyL0FieldAssignabilityAnalysis$.MODULE$
    );

    private final List<ComputationSpecification<FPCFAnalysis>> DEMO_CONF = Arrays.asList(
            EagerL0PurityAnalysis$.MODULE$,
            LazyL0FieldAssignabilityAnalysis$.MODULE$
    );


    public MethodPurityAnalysisRunner(String pathToJar) {
        super(pathToJar);
    }

    @Override
    void startAnalysis() {
        p.get(RTACallGraphKey$.MODULE$);
        store = p.get(PropertyStoreKey$.MODULE$);
        FPCFAnalysesManager manager = p.get(FPCFAnalysesManagerKey$.MODULE$);
        store = manager.runAll(
                CollectionConverters.asScala(DEMO_CONF).toSeq()
        )._1;
        store.waitOnPhaseCompletion();
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
