package de.uka.ilkd.key.opal.result;

public enum MethodPurityLevel {

    CompileTimePure,    // CompileTimePure > Pure!
    Pure,
    SideEffectFree,
    DPure,
    DSideEffectFree,
    ExternallyPure,
    ExternallySideEffectFree,
    ContextuallyPure,
    ContextuallySideEffectFree,
    DContextuallyPure,
    DExternallyPure,
    DContextuallySideEffectFree,
    ImpureByAnalysis
}