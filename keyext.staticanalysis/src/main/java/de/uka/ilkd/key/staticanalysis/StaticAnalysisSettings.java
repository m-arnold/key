package de.uka.ilkd.key.staticanalysis;

import de.uka.ilkd.key.staticanalysis.runner.AnalysisLevel;

public class StaticAnalysisSettings {
    private static StaticAnalysisSettings INST;
    private boolean useFieldImmutabilityAnalysis;
    private AnalysisLevel fieldImmutabilityLevel;
    private boolean useMethodPurityAnalysis;
    private AnalysisLevel methodPurityLevel;

    private boolean useCloseWorldAssumption;
    private boolean analyzeJDKFiles;

    /**
     * Maybe delete this one, default should be enough
     *
     * @param useFieldImmutabilityAnalysis
     * @param useMethodPurityAnalysis
     * @param useCloseWorldAssumption
     * @param analyzeJDKFiles
     */
    private StaticAnalysisSettings(boolean useFieldImmutabilityAnalysis,
                                   boolean useMethodPurityAnalysis,
                                   boolean useCloseWorldAssumption,
                                   boolean analyzeJDKFiles)
    {
        this.useFieldImmutabilityAnalysis = useFieldImmutabilityAnalysis;
        this.useMethodPurityAnalysis = useMethodPurityAnalysis;
        this.useCloseWorldAssumption = useCloseWorldAssumption;
        this.analyzeJDKFiles = analyzeJDKFiles;
    }

    /**
     * Default StaticAnalysisSettings...
     */
    private StaticAnalysisSettings() {
        this.useFieldImmutabilityAnalysis = true;
        this.useMethodPurityAnalysis = true;
        this.useCloseWorldAssumption = false;
        this.analyzeJDKFiles = true;
        this.fieldImmutabilityLevel = AnalysisLevel.L1;
        this.methodPurityLevel = AnalysisLevel.L1;
    }

    public static StaticAnalysisSettings getINST() {
        if (INST == null) {
            INST = new StaticAnalysisSettings();
        }
        return INST;
    }

    public boolean useFieldImmutabilityAnalysis() {
        return useFieldImmutabilityAnalysis;
    }

    public boolean useMethodPurityAnalysis() {
        return useMethodPurityAnalysis;
    }

    public boolean useCloseWorldAssumption() {
        return useCloseWorldAssumption;
    }

    public boolean analyzeJDKFiles() { return analyzeJDKFiles;}

    public AnalysisLevel getFieldImmutabilityLevel() {
        return this.fieldImmutabilityLevel;
    }

    public AnalysisLevel getMethodPurityLevel() {
        return this.methodPurityLevel;
    }

    public void setUseFieldImmutabilityAnalysis(boolean useFieldImmutabilityAnalysis) {
        this.useFieldImmutabilityAnalysis = useFieldImmutabilityAnalysis;
    }

    public void setUseMethodPurityAnalysis(boolean useMethodPurityAnalysis) {
        this.useMethodPurityAnalysis = useMethodPurityAnalysis;
    }

    public void setFieldImmutabilityLevel(AnalysisLevel level) {
        this.fieldImmutabilityLevel = level;
    }

    public void setMethodPurityLevel(AnalysisLevel level) {
        this.methodPurityLevel = level;
    }

    public void setUseCloseWorldAssumption(boolean useCloseWorldAssumption) {
        this.useCloseWorldAssumption = useCloseWorldAssumption;
    }

    public void setAnalyzeJDKFiles(boolean analyzeJDKFiles) {
        this.analyzeJDKFiles = analyzeJDKFiles;
    }

    public boolean anyAnalysisSelected() {
        return useFieldImmutabilityAnalysis || useMethodPurityAnalysis; // | ... Add new analyses here...
    }
}