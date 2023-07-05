package de.uka.ilkd.key.staticanalysis.runner;

import de.uka.ilkd.key.staticanalysis.result.FieldImmutabilityResult;
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
import org.opalj.fpcf.*;
import org.opalj.tac.cg.RTACallGraphKey$;
import org.opalj.tac.fpcf.analyses.EagerFieldImmutabilityAnalysis$;
import org.opalj.tac.fpcf.analyses.LazyFieldImmutabilityAnalysis$;
import org.opalj.tac.fpcf.analyses.LazyFieldLocalityAnalysis$;
import org.opalj.tac.fpcf.analyses.escape.LazyInterProceduralEscapeAnalysis$;
import org.opalj.tac.fpcf.analyses.escape.LazyReturnValueFreshnessAnalysis$;
import org.opalj.tac.fpcf.analyses.escape.LazySimpleEscapeAnalysis$;
import org.opalj.tac.fpcf.analyses.fieldassignability.LazyL0FieldAssignabilityAnalysis;
import org.opalj.tac.fpcf.analyses.fieldassignability.LazyL0FieldAssignabilityAnalysis$;
import org.opalj.tac.fpcf.analyses.fieldassignability.LazyL1FieldAssignabilityAnalysis$;
import org.opalj.tac.fpcf.analyses.fieldassignability.LazyL2FieldAssignabilityAnalysis$;
import org.opalj.tac.fpcf.analyses.purity.EagerL1PurityAnalysis$;
import org.opalj.tac.fpcf.analyses.purity.EagerL2PurityAnalysis$;
import org.opalj.tac.fpcf.analyses.purity.LazyL1PurityAnalysis$;
import org.opalj.tac.fpcf.analyses.purity.LazyL2PurityAnalysis$;
import scala.jdk.javaapi.CollectionConverters;
import scala.reflect.internal.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodPurityAnalysisRunner  extends AbstractAnalysisRunner {
    public MethodPurityAnalysisRunner(String pathToJar) {
        super(pathToJar);
    }

    @Override
    void startAnalysis() {
        p.get(RTACallGraphKey$.MODULE$);
        store = p.get(PropertyStoreKey$.MODULE$);
        FPCFAnalysesManager manager = p.get(FPCFAnalysesManagerKey$.MODULE$);
//        startDemo();
//        startL2();
        store = manager.runAll(
                CollectionConverters.asScala(DEMO_CONF).toSeq()
        )._1;
        store.waitOnPhaseCompletion();
    }

    private void startDemo() {
        LazyL0FieldAssignabilityAnalysis.register(p, store, null);
        EagerL0PurityAnalysis$.MODULE$.start(p, store, null);
    }

//    private List<ComputationSpecification<FPCFAnalysis>> getL2Conf() {
//        FPCFAnalysesManager manager = p.get(FPCFAnalysesManagerKey$.MODULE$);
//        return Arrays.asList(
//                EagerL2PurityAnalysis$.MODULE$,
//                LazyFieldImmutabilityAnalysis$.MODULE$,
//                LazyL0CompileTimeConstancyAnalysis$.MODULE$,
//                LazyStaticDataUsageAnalysis$.MODULE$,
//                LazyReturnValueFreshnessAnalysis$.MODULE$,
//                LazyFieldLocalityAnalysis$.MODULE$,
//                LazyTypeImmutabilityAnalysis$.MODULE$,
//                LazyClassImmutabilityAnalysis$.MODULE$,
//                LazyInterProceduralEscapeAnalysis$.MODULE$,
//                LazyL2FieldAssignabilityAnalysis$.MODULE$
//        );
//        store = manager.runAll(
//                CollectionConverters.asScala(analyses).toSeq()
//        )._1;
//    }

//    private void startL1() {
//        FPCFAnalysesManager manager = p.get(FPCFAnalysesManagerKey$.MODULE$);
//        java.util.List<ComputationSpecification<FPCFAnalysis>> analyses = Arrays.asList(
//                LazyL1PurityAnalysis$.MODULE$,
//                LazyClassImmutabilityAnalysis$.MODULE$,
//                LazyTypeImmutabilityAnalysis$.MODULE$,
//                LazyInterProceduralEscapeAnalysis$.MODULE$,
//                LazyL1FieldAssignabilityAnalysis$.MODULE$
//                );
//        store = manager.runAll(
//                CollectionConverters.asScala(analyses).toSeq()
//        )._1;
//    }

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

