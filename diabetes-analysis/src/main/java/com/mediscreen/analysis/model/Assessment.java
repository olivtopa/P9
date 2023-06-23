package com.mediscreen.analysis.model;

/**
  * Provide the 4 levels of risk categories:
 */
public enum Assessment {
    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("In danger"),
    EARLY_ONSET("Early onset");

    private final String value;

    Assessment(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
