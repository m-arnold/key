package de.uka.ilkd.key.opal;

import de.uka.ilkd.key.opal.runner.AnalysisLevel;

public class StaticAnalysisSettings {
    public static boolean useFieldImmutabilityAnalysis = Boolean.valueOf(System.getProperty("useFieldImmutabilityAnalysis", "false"));
    public static AnalysisLevel fieldImmutabilityLevel = AnalysisLevel.valueOf(System.getProperty("fieldImmutabilityLevel", "L1"));
    public static boolean useMethodPurityAnalysis = Boolean.valueOf(System.getProperty("useMethodPurityAnalysis", "false"));
    public static AnalysisLevel methodPurityLevel = AnalysisLevel.valueOf(System.getProperty("methodPurityLevel", "L1"));

    public static boolean useCloseWorldAssumption = Boolean.valueOf(System.getProperty("useCloseWorldAssumption", "false"));
    public static boolean analyzeJDKFiles = Boolean.valueOf(System.getProperty("analyzeJDKFiles", "true"));

    // Field Immutability Analysis Usescases:
    private static boolean useRevisedHeapTheory = Boolean.valueOf(System.getProperty("useRevisedHeapTheory", "false"));

    //Method Purity Analysis Usecases:
    private static boolean useAssignableClauseGeneration = Boolean.valueOf(System.getProperty("useAssignableClauseGeneration", "false"));
    private static boolean useHeapParameterRemoval = Boolean.valueOf(System.getProperty("useHeapParameterRemoval", "false"));
    private static boolean useAssignableClauseReduction = Boolean.valueOf(System.getProperty("useAssignableClauseReduction", "false"));

    public static boolean anyAnalysisSelected() {
        return useFieldImmutabilityAnalysis || useMethodPurityAnalysis; // | ... Add new analyses here...
    }

    public static boolean useAssignableClauseGeneration() {
        return useMethodPurityAnalysis && useAssignableClauseGeneration;
    }

    public static boolean useHeapParameterRemoval() {
        return useMethodPurityAnalysis && useHeapParameterRemoval;
    }

    public static boolean useAssignableClauseReduction() {
        return useMethodPurityAnalysis && useAssignableClauseReduction;
    }

    // TODO: Refactor this! useAssignableClauseReduction() includes useMethodPurityAnalysis, this method does not!
    // Unintuitive!
    public static void setUseAssignableClauseReduction(boolean b) {
        useAssignableClauseGeneration = b;
    }

    public static void setUseAssignableClauseGeneration(boolean b) {
        useAssignableClauseGeneration = b;
    }

    public static void setUseHeapParameterRemoval(boolean b) {
        useHeapParameterRemoval = b;
    }

    public static boolean useRevisedHeapTheory() {
        return useFieldImmutabilityAnalysis && useRevisedHeapTheory;
    }
}