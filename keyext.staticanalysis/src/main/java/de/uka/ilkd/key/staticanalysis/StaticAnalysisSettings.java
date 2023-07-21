package de.uka.ilkd.key.staticanalysis;

import de.uka.ilkd.key.staticanalysis.runner.AnalysisLevel;

public class StaticAnalysisSettings {
    private static StaticAnalysisSettings INST;
    private boolean useFieldImmutabilityAnalysis;
    private boolean useMethodPurityAnalysis;

    private AnalysisLevel fieldImmutabilityLevel;
    private AnalysisLevel methodPurityLevel;

    private boolean useCloseWorldAssumption;
    private StaticAnalysisSettings staticAnalysisSettings;

    /**
     * Maybe delete this one, default should be enough
     *
     * @param useFieldImmutabilityAnalysis
     * @param useMethodPurityAnalysis
     * @param useCloseWorldAssumption
     */
    private StaticAnalysisSettings(boolean useFieldImmutabilityAnalysis,
                                   boolean useMethodPurityAnalysis,
                                   boolean useCloseWorldAssumption)
    {
        this.useFieldImmutabilityAnalysis = useFieldImmutabilityAnalysis;
        this.useMethodPurityAnalysis = useMethodPurityAnalysis;
        this.useCloseWorldAssumption = useCloseWorldAssumption;
    }

    /**
     * Default StaticAnalysisSettings...
     */
    private StaticAnalysisSettings() {
        this.useFieldImmutabilityAnalysis = true;
        this.useMethodPurityAnalysis = true;
        this.useCloseWorldAssumption = false;
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

    public boolean anyAnalysisSelected() {
        return useFieldImmutabilityAnalysis || useMethodPurityAnalysis; // | ... Add new analyses here...
    }
}