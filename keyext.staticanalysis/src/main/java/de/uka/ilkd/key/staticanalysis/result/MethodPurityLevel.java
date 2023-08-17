package de.uka.ilkd.key.staticanalysis.result;

public enum MethodPurityLevel {

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
    // ToDO: DContextuallyPure(IntTrieSet(0) is missing! Think about the structure of this enum again!
}
