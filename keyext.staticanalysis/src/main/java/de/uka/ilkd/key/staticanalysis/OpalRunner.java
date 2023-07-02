package de.uka.ilkd.key.staticanalysis;

import de.uka.ilkd.key.staticanalysis.runner.FieldImmutabilityAnalysisRunner;
import de.uka.ilkd.key.staticanalysis.runner.MethodPurityAnalysisRunner;

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
        runAnalyses();
        jarProcessor.deleteAnalysisJar();
    }

    private void runAnalyses() {
        if (settings.useFieldImmutabilityAnalysis()) {
            System.out.println("Runs Field ImmutabilityAnalysis!");
            FieldImmutabilityAnalysisRunner.run(jarPath);
        }
        if (settings.useMethodPurityAnalysis()) {
            System.out.println("Runs Method Purity Analysis!");
            MethodPurityAnalysisRunner runner = new MethodPurityAnalysisRunner(jarPath);
            runner.run();
        }
    }
}
