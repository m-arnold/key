package de.uka.ilkd.key.staticanalysis.runner;

import org.opalj.br.analyses.Project;
import org.opalj.fpcf.PropertyStore;
import org.opalj.tac.cg.RTACallGraphKey$;

import java.io.File;
import java.net.URL;

public abstract class AbstractAnalysisRunner {

    protected Project<URL> p;
    protected PropertyStore store;

    public AbstractAnalysisRunner(String pathToJar) {
        p = Project.apply(new File(pathToJar)); // Hier anderes Apply verwendetn! apply(file: File, logContext: LogContext, config: Config)
    }

    public final void run() {
        startAnalysis();
        // store has to be defined in startAnalysis()! Rethink this!

        evaluateResult();
    }

    abstract void evaluateResult();

    abstract void startAnalysis();
}
