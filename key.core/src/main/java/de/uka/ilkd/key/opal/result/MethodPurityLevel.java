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
    // ToDO: DContextuallyPure(IntTrieSet(0) is missing! Think about the structure of this enum again!
}
