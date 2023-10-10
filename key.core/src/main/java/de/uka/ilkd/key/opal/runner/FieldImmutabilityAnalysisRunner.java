package de.uka.ilkd.key.opal.runner;

import de.uka.ilkd.key.opal.OpalResultProvider;
import de.uka.ilkd.key.opal.result.FieldImmutabilityLevel;
import de.uka.ilkd.key.opal.result.FieldImmutabilityResult;
import de.uka.ilkd.key.util.Pair;
import org.opalj.br.Field;
import org.opalj.br.fpcf.FPCFAnalysis;
import org.opalj.br.fpcf.analyses.LazyL0CompileTimeConstancyAnalysis$;
import org.opalj.br.fpcf.analyses.LazyStaticDataUsageAnalysis$;
import org.opalj.br.fpcf.analyses.immutability.LazyClassImmutabilityAnalysis$;
import org.opalj.br.fpcf.analyses.immutability.LazyTypeImmutabilityAnalysis$;
import org.opalj.br.fpcf.properties.immutability.FieldImmutability;
import org.opalj.br.fpcf.properties.immutability.FieldImmutability$;
import org.opalj.fpcf.ComputationSpecification;
import org.opalj.fpcf.EPS;
import org.opalj.fpcf.FinalEP;
import org.opalj.fpcf.Property;
import org.opalj.tac.fpcf.analyses.EagerFieldImmutabilityAnalysis$;
import org.opalj.tac.fpcf.analyses.LazyFieldLocalityAnalysis$;
import org.opalj.tac.fpcf.analyses.escape.LazyInterProceduralEscapeAnalysis$;
import org.opalj.tac.fpcf.analyses.escape.LazyReturnValueFreshnessAnalysis$;
import org.opalj.tac.fpcf.analyses.escape.LazySimpleEscapeAnalysis$;
import org.opalj.tac.fpcf.analyses.fieldassignability.LazyL2FieldAssignabilityAnalysis$;
import org.opalj.tac.fpcf.analyses.purity.LazyL2PurityAnalysis$;
import scala.collection.immutable.Seq;
import scala.jdk.javaapi.CollectionConverters;

import java.util.*;

public class FieldImmutabilityAnalysisRunner extends AbstractAnalysisRunner{

    private final List<ComputationSpecification<FPCFAnalysis>> L2_CONF = Arrays.asList(
            EagerFieldImmutabilityAnalysis$.MODULE$,
            LazyL2FieldAssignabilityAnalysis$.MODULE$,
            LazyClassImmutabilityAnalysis$.MODULE$,
            LazyTypeImmutabilityAnalysis$.MODULE$,
            LazyStaticDataUsageAnalysis$.MODULE$,
            LazyL0CompileTimeConstancyAnalysis$.MODULE$,
            LazySimpleEscapeAnalysis$.MODULE$
    );

    private final List<ComputationSpecification<FPCFAnalysis>> DEMO_CONF = Arrays.asList(
            LazyL2FieldAssignabilityAnalysis$.MODULE$,
            LazyL2PurityAnalysis$.MODULE$, //Delta zu L2
            EagerFieldImmutabilityAnalysis$.MODULE$,
            LazyClassImmutabilityAnalysis$.MODULE$,
            LazyTypeImmutabilityAnalysis$.MODULE$,
            LazyStaticDataUsageAnalysis$.MODULE$,
            LazyL0CompileTimeConstancyAnalysis$.MODULE$,
            LazyInterProceduralEscapeAnalysis$.MODULE$, // Delta zu L2
            LazyReturnValueFreshnessAnalysis$.MODULE$, // Delta zu L2
            LazyFieldLocalityAnalysis$.MODULE$
    );

    public FieldImmutabilityAnalysisRunner(String pathToJar) { super(pathToJar); }

    @Override
    void evaluateResult() {
        Map<Pair<String,String>, FieldImmutabilityLevel> result = new HashMap<>();
        scala.collection.Iterator<EPS<Object, FieldImmutability>> scalaIterator = store.entities(FieldImmutability$.MODULE$.key());
        while (scalaIterator.hasNext()) {
            FinalEP<Object, FieldImmutability> finalEP = scalaIterator.next().toFinalEP();
            Property finalProperty = finalEP.p();
            Field fieldEntity = (Field) finalEP.e();
            String className = fieldEntity.declaringClassFile().thisType().fqn();
            String fieldName = fieldEntity.name();
            String immutabilityLevel = finalProperty.toString();
            if (!isJDKFile(className)) {
                result.put(new Pair<>(className, fieldName), FieldImmutabilityLevel.valueOf(immutabilityLevel));
            }
        }
        OpalResultProvider.getINST().setFieldImmutabilityResult(new FieldImmutabilityResult(result));
    }

    // TODO: Das hier nochmal mit Richard besprechen.
    private boolean isJDKFile(String s) {
        return s.contains("/");
    }

    @Override
    Seq<ComputationSpecification<FPCFAnalysis>> determineAnalyses() {
        return CollectionConverters.asScala(L2_CONF).toSeq();
    }
}
