package de.uka.ilkd.key.staticanalysis.result;

public enum FieldImmutabilityLevel {
    NonTransitivelyImmutableField,
    TransitivelyImmutableField,
    MutableField
}
