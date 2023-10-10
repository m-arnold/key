package de.uka.ilkd.key.opal;

import de.uka.ilkd.key.opal.runner.FieldImmutabilityAnalysisRunner;
import de.uka.ilkd.key.opal.runner.MethodPurityAnalysisRunner;

public class OpalRunner {

    // TODO: Do i need this one?
    private String outputPath;

    private String jarPath;

    private StaticAnalysisSettings settings;

    public OpalRunner() {
        settings = StaticAnalysisSettings.getINST();
        OpalResultProvider.getINST().resetResults();
    }

    public void run(String[] filesNames) {
        if (!settings.anyAnalysisSelected()) {
            System.out.println("No analyses selected!");
            return;
        }
        JarProcessor jarProcessor = new JarProcessor(filesNames);
        jarPath = jarProcessor.createForAnalysis();
        if (OpalResultProvider.getINST().hasCompilationFailed()) {
            System.out.println("Compile Error! No Analyses are executed!");
        } else {
            runAnalyses();
        }
        jarProcessor.deleteAnalysisJar();
    }

    private void runAnalyses() {
        if (settings.useFieldImmutabilityAnalysis()) {
            System.out.println("Runs Field ImmutabilityAnalysis!");
            FieldImmutabilityAnalysisRunner runner = new FieldImmutabilityAnalysisRunner(jarPath);
            runner.run();
        }
        if (settings.useMethodPurityAnalysis()) {
            System.out.println("Runs Method Purity Analysis!");
            MethodPurityAnalysisRunner runner = new MethodPurityAnalysisRunner(jarPath);
            runner.run();
        }
    }
}
