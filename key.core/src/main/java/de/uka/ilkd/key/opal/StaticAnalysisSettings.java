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
    public static AnalysisLevel methodPurityLevel = AnalysisLevel.valueOf(readFromPropertyFiles("methodPurityLevel", "L1"));

    public static boolean useCloseWorldAssumption = Boolean.valueOf(readFromPropertyFiles("useCloseWorldAssumption", "false"));
    public static boolean analyzeJDKFiles = Boolean.valueOf(readFromPropertyFiles("analyzeJDKFiles", "true"));

    // Field Immutability Analysis Usescases:
    private static boolean useRevisedHeapTheory = Boolean.valueOf(readFromPropertyFiles("useRevisedHeapTheory", "true"));

    //Method Purity Analysis Usecases:
    private static boolean useAssignableClauseGeneration = Boolean.valueOf(readFromPropertyFiles("useAssignableClauseGeneration", "false"));
    private static boolean useHeapParameterRemoval = Boolean.valueOf(readFromPropertyFiles("useHeapParameterRemoval", "false"));
    private static boolean useAssignableClauseReduction = Boolean.valueOf(readFromPropertyFiles("useAssignableClauseReduction", "false"));

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