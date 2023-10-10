package de.uka.ilkd.key.opal.result;

public enum MethodPurityLevel {

    CompileTimePure,    // CompileTimePure > Pure!
    Pure,
    SideEffectFree,
    DPure,              // DPure > SideEffectFree??
    DSideEffectFree,
    ExternallyPure,
    ExternallySideEffectFree,
    ContextuallyPure,
    ContextuallySideEffectFree,
    DContextuallyPure,
    DContextuallySideEffectFree,
    ImpureByAnalysis
}
