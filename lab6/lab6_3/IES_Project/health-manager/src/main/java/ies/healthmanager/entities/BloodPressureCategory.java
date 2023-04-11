package ies.healthmanager.entities;

public enum BloodPressureCategory {
    NORMAL, ELEVATED, HIGH_STAGE_1, HIGH_STAGE_2, HYPERTENSIVE_CRISIS;

    @Override
    public String toString() {
        switch (this) {
            case NORMAL:
                return "Normal";
            case ELEVATED:
                return "Elevated";
            case HIGH_STAGE_1:
                return "High Stage 1";
            case HIGH_STAGE_2:
                return "High Stage 2";
            case HYPERTENSIVE_CRISIS:
                return "Hypertensive crisis";
            default:
                throw new IllegalArgumentException();
        }
    }
}
