package de.uka.ilkd.key.staticanalysis;

public class OpalRunner {

    private String outputPath;

    public void OpalRunner() {
        // ToDo!
    }

    public void run(StaticAnalysisSettings settings, String[] filesNames) {
        JarProcessor jarProcessor = new JarProcessor(filesNames);
        String jarPath = jarProcessor.createForAnalysis();
        FieldImmutabilityAnalysisRunner.run(jarPath);
        jarProcessor.deleteAnalysisJar();
    }

}
