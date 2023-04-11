package ies.healthmanagerprototype.entities;

public enum HealthState {
    VERY_HEALTHY, HEALTHY, NEUTRAL, UNHEALTHY, VERY_UNHEALTHY;

    @Override
    public String toString() {
        switch (this) {
            case VERY_HEALTHY:
                return "Very healthy";
            case HEALTHY:
                return "Healthy";
            case NEUTRAL:
                return "Neutral";
            case UNHEALTHY:
                return "Unhealthy";
            case VERY_UNHEALTHY:
                return "Very unhealthy";
            default:
                throw new IllegalArgumentException();
        }
    }

}
