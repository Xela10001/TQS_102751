package ies.healthmanagerprototype.entities;

public class BloodPressure {
    private int systolic, diastolic; // mm Hg

    public BloodPressure() {

    }

    public BloodPressure(int systolic, int diastolic) {
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    @Override
    public String toString()
    {
        return systolic + "/" + diastolic + " mmHg";
    }

    private BloodPressureCategory category() {
        if (systolic > 180 || diastolic > 120)
            return BloodPressureCategory.HYPERTENSIVE_CRISIS;
        if (diastolic < 80) {
            if (systolic < 120)
                return BloodPressureCategory.NORMAL;
            else if (systolic <= 129)
                return BloodPressureCategory.ELEVATED;
        }
        if ((systolic >= 130 && systolic <= 139) || (diastolic >= 80 && diastolic <= 89))
            return BloodPressureCategory.HIGH_STAGE_1;
        if (systolic >= 140 || diastolic >= 90)
            return BloodPressureCategory.HIGH_STAGE_2;
        return null;
    }

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

}
