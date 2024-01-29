package de.uka.ilkd.key.opal;

import de.uka.ilkd.key.opal.runner.AnalysisLevel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class StaticAnalysisSettings {

    public static final String PROPERTY_PATH = "/home/marc/coding/KeYGithubFork/staticAnalysesConfig.properties";

    public static boolean useFieldImmutabilityAnalysis = Boolean.valueOf(readFromPropertyFiles("useFieldImmutabilityAnalysis", "false"));
    public static AnalysisLevel fieldImmutabilityLevel = AnalysisLevel.valueOf(readFromPropertyFiles("fieldImmutabilityLevel", "L1"));
    public static boolean useMethodPurityAnalysis = Boolean.valueOf(readFromPropertyFiles("useMethodPurityAnalysis", "false"));
    public static boolean useExceptionUsageAnalysis = Boolean.valueOf(readFromPropertyFiles("useExceptionUsageAnalysis", "false"));
    public static AnalysisLevel methodPurityLevel = AnalysisLevel.valueOf(readFromPropertyFiles("methodPurityLevel", "L2"));

    public static boolean useCloseWorldAssumption = Boolean.valueOf(readFromPropertyFiles("useCloseWorldAssumption", "false"));
    public static boolean analyzeJDKFiles = Boolean.valueOf(readFromPropertyFiles("analyzeJDKFiles", "true"));

    // Field Immutability Analysis Usescases:
    private static boolean useRevisedHeapTheory = Boolean.valueOf(readFromPropertyFiles("useRevisedHeapTheory", "true"));

    //Method Purity Analysis Usecases:
    private static boolean useAssignableClauseOptimization = Boolean.valueOf(readFromPropertyFiles("useAssignableClauseGeneration", "false"));
    private static boolean useAccessibleClauseOptimization = Boolean.valueOf(readFromPropertyFiles("useAccessibleClauseOptimization", "false"));
    private static boolean useHeapParameterRemoval = Boolean.valueOf(readFromPropertyFiles("useHeapParameterRemoval", "false"));

    public static long queryAxiomCosts = Long.valueOf(readFromPropertyFiles("queryAxiomCosts", "-3000"));

    private static boolean useAssignableClauseReduction = Boolean.valueOf(readFromPropertyFiles("useAssignableClauseReduction", "false"));

    private static AssignableClauseGenerateMode assignableClauseGenerateMode = AssignableClauseGenerateMode.valueOf(readFromPropertyFiles("assignableClauseGenerateMode", "Replace"));
    private static Boolean trustOpal = Boolean.valueOf(readFromPropertyFiles("trustOpal", "true"));

    private static String readFromPropertyFiles(String propKeY, String defaultValue) {
        try {
            Properties appProps = new Properties();
            appProps.load(new FileInputStream(PROPERTY_PATH));
            String val = (String) appProps.get(propKeY);
            if (val == null) {
                System.out.println("Property " + propKeY + " not found within config, default value is used.");
                return defaultValue;
            }
            return val;
        }
        catch (FileNotFoundException e) {
            // No output, usually occurs outside of benchmarking (No Property file is used in normal KeY usage).
            return defaultValue;
        }
        catch (Exception e) {
            System.out.println("Error while reading property from file: " + e.toString());
            System.out.println("Default value is used.");
            return defaultValue;
        }
    }


    public static boolean anyAnalysisSelected() {
        return useFieldImmutabilityAnalysis || useMethodPurityAnalysis || useExceptionUsageAnalysis; // | ... Add new analyses here...
    }

    public static boolean useAssignableClauseOptimization() {
        return useMethodPurityAnalysis && useAssignableClauseOptimization;
    }

    public static boolean useAccessibleClauseOptimization() {
        return useMethodPurityAnalysis && useAccessibleClauseOptimization;
    }

    public static boolean useHeapParameterRemoval() {
        return useMethodPurityAnalysis && useHeapParameterRemoval;
    }

    public static boolean useAssignableClauseReduction() {
        return useMethodPurityAnalysis && useAssignableClauseReduction;
    }

    public static AssignableClauseGenerateMode getAssignableGenerationMode() {
        return assignableClauseGenerateMode;
    }

    public static boolean isModeReplace() {
        return assignableClauseGenerateMode == AssignableClauseGenerateMode.Replace;
    }

    public static boolean isModeIntersect() {
        return assignableClauseGenerateMode == AssignableClauseGenerateMode.Intersect;
    }

    public static void setAssignableGenerationMode(AssignableClauseGenerateMode mode) {
        assignableClauseGenerateMode = mode;
    }

    public static void setTrustOpal(boolean b) {
        trustOpal = b;
    }

    public static boolean trustOpal() {
        return trustOpal;
    }

    // TODO: Refactor this! useAssignableClauseReduction() includes useMethodPurityAnalysis, this method does not!
    // Unintuitive!
    public static void setUseAssignableClauseOptimization(boolean b) {
        useAssignableClauseOptimization = b;
    }

    public static void setUseAssignableOptimization(boolean b) {
        useAssignableClauseOptimization = b;
    }
    public static void setUseAccessibleClauseOptimization(boolean b) {useAssignableClauseOptimization = b; }

    public static void setUseHeapParameterRemoval(boolean b) {
        useHeapParameterRemoval = b;
    }

    public static boolean useRevisedHeapTheory() {
        return useFieldImmutabilityAnalysis && useRevisedHeapTheory;
    }

    public enum AssignableClauseGenerateMode {
        Replace,
        Intersect,
    }
}