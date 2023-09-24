package de.uka.ilkd.key.staticanalysis;

public class StaticAnalysisSettings {

    private boolean useFieldImmutabilityAnalysis;
    private boolean useCloseWorldAssumption;

    public StaticAnalysisSettings(boolean useFieldImmutabilityAnalysis, boolean useCloseWorldAssumption) {
        this.useFieldImmutabilityAnalysis = useFieldImmutabilityAnalysis;
        this.useCloseWorldAssumption = useCloseWorldAssumption;
    }

    public boolean useFieldImmutabilityAnalysis() {
        return true; //useFieldImmutabilityAnalysis;
    }

    public boolean useCloseWorldAssumption() {
        return false; // Ich nehme derzeit an das das hier der Default bei Opal ist!
    }

}
