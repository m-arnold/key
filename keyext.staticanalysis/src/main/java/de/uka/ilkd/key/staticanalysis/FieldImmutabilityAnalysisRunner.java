package de.uka.ilkd.key.staticanalysis;

import org.opalj.br.analyses.Project;
import org.opalj.br.fpcf.FPCFAnalysesManager;
import org.opalj.br.fpcf.FPCFAnalysesManagerKey$;
import org.opalj.br.fpcf.FPCFAnalysis;
import org.opalj.br.fpcf.analyses.LazyL0CompileTimeConstancyAnalysis$;
import org.opalj.br.fpcf.analyses.LazyStaticDataUsageAnalysis$;
import org.opalj.br.fpcf.analyses.immutability.LazyClassImmutabilityAnalysis$;
import org.opalj.br.fpcf.analyses.immutability.LazyTypeImmutabilityAnalysis$;
import org.opalj.br.fpcf.properties.immutability.FieldImmutability$;
import org.opalj.fpcf.ComputationSpecification;
import org.opalj.fpcf.PropertyStore;
import org.opalj.tac.cg.RTACallGraphKey$;
import org.opalj.tac.fpcf.analyses.EagerFieldImmutabilityAnalysis$;
import org.opalj.tac.fpcf.analyses.LazyFieldLocalityAnalysis$;
import org.opalj.tac.fpcf.analyses.escape.LazyInterProceduralEscapeAnalysis$;
import org.opalj.tac.fpcf.analyses.escape.LazyReturnValueFreshnessAnalysis$;
import org.opalj.tac.fpcf.analyses.fieldassignability.LazyL2FieldAssignabilityAnalysis$;
import org.opalj.tac.fpcf.analyses.purity.LazyL2PurityAnalysis$;
import scala.jdk.javaapi.CollectionConverters;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;

public class FieldImmutabilityAnalysisRunner {
    public static void run(String pathToJar) {
        Project<URL> p = Project.apply(new File(pathToJar));

        FPCFAnalysesManager manager = p.get(FPCFAnalysesManagerKey$.MODULE$);
        p.get(RTACallGraphKey$.MODULE$);

        java.util.List<ComputationSpecification<FPCFAnalysis>> analyses = Arrays.asList(
                LazyL2FieldAssignabilityAnalysis$.MODULE$,
                LazyL2PurityAnalysis$.MODULE$,
                EagerFieldImmutabilityAnalysis$.MODULE$,
                LazyClassImmutabilityAnalysis$.MODULE$,
                LazyTypeImmutabilityAnalysis$.MODULE$,
                LazyStaticDataUsageAnalysis$.MODULE$,
                LazyL0CompileTimeConstancyAnalysis$.MODULE$,
                LazyInterProceduralEscapeAnalysis$.MODULE$,
                LazyReturnValueFreshnessAnalysis$.MODULE$,
                LazyFieldLocalityAnalysis$.MODULE$
        );

        System.out.println("__________________________________________________________________________________________");
        System.out.println("RUN ANALYSIS:");

        PropertyStore store = manager.runAll(
                CollectionConverters.asScala(analyses).toSeq()
        )._1;
        store.waitOnPhaseCompletion();

        scala.collection.Iterator scalaIterator = store.entities(FieldImmutability$.MODULE$.key());

        Iterator javaIterator = CollectionConverters.asJava(scalaIterator);

        System.out.println("__________________________________________________________________________________________");
        System.out.println("ANALYSIS RESULT:");
        while (javaIterator.hasNext()) {
            System.out.println(javaIterator.next().toString());
        }
    }
}
