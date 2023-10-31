package de.uka.ilkd.key.opal;

import de.uka.ilkd.key.opal.runner.ExceptionUsageAnalysisRunner;
import de.uka.ilkd.key.opal.runner.FieldImmutabilityAnalysisRunner;
import de.uka.ilkd.key.opal.runner.MethodPurityAnalysisRunner;

public class OpalRunner {

    // TODO: Do i need this one?
    private String outputPath;

    private String jarPath;

    public OpalRunner() {
        OpalResultProvider.getINST().resetResults();
    }

    public void run(String[] filesNames) {
        if (!StaticAnalysisSettings.anyAnalysisSelected()) {
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
        if (StaticAnalysisSettings.useFieldImmutabilityAnalysis) {
            System.out.println("Runs Field ImmutabilityAnalysis!");
            FieldImmutabilityAnalysisRunner runner = new FieldImmutabilityAnalysisRunner(jarPath);
            runner.run();
        }
        if (StaticAnalysisSettings.useMethodPurityAnalysis) {
            System.out.println("Runs Method Purity Analysis!");
            MethodPurityAnalysisRunner runner = new MethodPurityAnalysisRunner(jarPath);
            runner.run();
        }
        if (StaticAnalysisSettings.useExceptionUsageAnalysis) {
            System.out.println("Runs Exception Usage Analysis");
            ExceptionUsageAnalysisRunner runner = new ExceptionUsageAnalysisRunner(jarPath);
            runner.run();
        }
    }
}
