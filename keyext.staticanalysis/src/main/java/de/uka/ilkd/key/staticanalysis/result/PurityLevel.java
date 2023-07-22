package de.uka.ilkd.key.staticanalysis.result;

public enum PurityLevel {

    CompileTimePure,    // CompileTimePure > Pure!
    Pure,
    SideEffectFree,
    DPure,              // DPure > SideEffectFree??
    DSideEffectFree,
    ContextuallyPure,
    ContextuallySideEffectFree,
    DContextuallyPure,
    DContextuallySideEffectFree,
    ImpureByAnalysis
}
