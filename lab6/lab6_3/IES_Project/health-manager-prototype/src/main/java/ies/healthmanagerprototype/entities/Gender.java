package ies.healthmanagerprototype.entities;

public enum Gender {
    MALE, FEMALE, OTHER;

    @Override
    public String toString() {
        switch (this) {
            case MALE:
                return "Male";
            case FEMALE:
                return "Female";
            case OTHER:
                return "Other";
            default:
                throw new IllegalArgumentException();
        }
    }
}
