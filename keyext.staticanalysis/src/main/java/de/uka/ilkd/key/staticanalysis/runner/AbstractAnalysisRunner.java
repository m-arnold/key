package de.uka.ilkd.key.staticanalysis.runner;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.opalj.br.analyses.Project;
import org.opalj.br.analyses.cg.AllEntryPointsFinder$;
import org.opalj.br.analyses.cg.InitialEntryPointsKey$;
import org.opalj.br.fpcf.FPCFAnalysesManagerKey$;
import org.opalj.br.fpcf.FPCFAnalysis;
import org.opalj.fpcf.ComputationSpecification;
import org.opalj.fpcf.PropertyStore;
import org.opalj.log.GlobalLogContext$;
import org.opalj.tac.cg.RTACallGraphKey$;
import scala.collection.immutable.Seq;

import java.io.File;
import java.net.URL;

public abstract class AbstractAnalysisRunner {

    protected Project<URL> p;
    protected PropertyStore store;

    public AbstractAnalysisRunner(String pathToJar) {
        Config config = ConfigFactory.load(this.getClass().getClassLoader())
                .withValue(InitialEntryPointsKey$.MODULE$.ConfigKey(), ConfigValueFactory.fromAnyRef("org.opalj.br.analyses.cg.AllEntryPointsFinder"))
                .withValue(AllEntryPointsFinder$.MODULE$.ConfigKey(), ConfigValueFactory.fromAnyRef(true));
        p = Project.apply(new File(pathToJar), GlobalLogContext$.MODULE$, config);
    }

    public final void run() {
        startAnalysis();
        evaluateResult();
    }

    abstract void evaluateResult();

    private void startAnalysis() {
        p.get(RTACallGraphKey$.MODULE$);
        store = p.get(FPCFAnalysesManagerKey$.MODULE$).runAll(
                determineAnalyses()
        )._1;
        store.waitOnPhaseCompletion();
    };

    abstract Seq<ComputationSpecification<FPCFAnalysis>> determineAnalyses();
}
