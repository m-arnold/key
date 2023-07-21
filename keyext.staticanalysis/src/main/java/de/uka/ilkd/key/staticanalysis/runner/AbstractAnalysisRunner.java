package de.uka.ilkd.key.staticanalysis.runner;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.opalj.br.analyses.Project;
import org.opalj.br.analyses.cg.AllEntryPointsFinder$;
import org.opalj.br.analyses.cg.InitialEntryPointsKey$;
import org.opalj.fpcf.PropertyStore;
import org.opalj.log.GlobalLogContext$;

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
        // store has to be defined in startAnalysis()! Rethink this!

        evaluateResult();
    }

    abstract void evaluateResult();

    abstract void startAnalysis();
}
