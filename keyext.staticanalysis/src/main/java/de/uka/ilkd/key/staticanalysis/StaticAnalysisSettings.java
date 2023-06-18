package de.uka.ilkd.key.staticanalysis;

public class StaticAnalysisSettings {
    private static StaticAnalysisSettings INST;
    private boolean useFieldImmutabilityAnalysis;
    private boolean useMethodPurityAnalysis;
    private boolean useCloseWorldAssumption;

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
        this.useMethodPurityAnalysis = false;
        this.useCloseWorldAssumption = false;
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

    public void setUseFieldImmutabilityAnalysis(boolean useFieldImmutabilityAnalysis) {
        this.useFieldImmutabilityAnalysis = useFieldImmutabilityAnalysis;
    }

    public void setUseMethodPurityAnalysis(boolean useMethodPurityAnalysis) {
        this.useMethodPurityAnalysis = useMethodPurityAnalysis;
    }

    public boolean anyAnalysisSelected() {
        return useFieldImmutabilityAnalysis || useMethodPurityAnalysis; // | ... Add new analyses here...
    }
}