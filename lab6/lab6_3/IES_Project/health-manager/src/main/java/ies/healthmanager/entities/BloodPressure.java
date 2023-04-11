package ies.healthmanager.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "BloodPressures")
@JsonPropertyOrder({ "id", "patient", "dateTime", "systolic", "diastolic" })
public class BloodPressure extends HealthAttribute {

    // mm Hg
    // usually <= 120/80
    private int systolic, diastolic;

    @Enumerated(EnumType.ORDINAL)
    private BloodPressureCategory category;

    public BloodPressure() {

    }

    @Override
    public String toString() {
        return systolic + "/" + diastolic + " mmHg";
    }

    public String toStringFormal() {
        return String.format("{id=%d, dateTime=%s, patientId=%d, diastolic=%d, systolic=%d, category=%s}",
                (int) getId(),
                getDateTime().toString().replace("T", " "), (int) getPatient().getId(), getDiastolic(), getSystolic(),
                getCategory().toString());
    }

    public BloodPressureCategory getCategory() {
        if (category != null)
            return category;

        if (systolic > 180 || diastolic > 120) {
            setCategory(BloodPressureCategory.HYPERTENSIVE_CRISIS);
            return category;
        }
        if (diastolic <= 80) {
            if (systolic <= 120) {
                setCategory(BloodPressureCategory.NORMAL);
                return category;
            } else if (systolic <= 129) {
                setCategory(BloodPressureCategory.ELEVATED);
                return category;
            }
        }
        if ((systolic >= 130 && systolic <= 139) || (diastolic > 80 && diastolic <= 89)) {
            setCategory(BloodPressureCategory.HIGH_STAGE_1);
            return category;
        }
        if (systolic >= 140 || diastolic >= 90) {
            setCategory(BloodPressureCategory.HIGH_STAGE_2);
            return category;
        }

        setCategory(null);
        return category;
    }

    public void setCategory(BloodPressureCategory newCat) {
        this.category = newCat;
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

    public void setBloodPressure(int systolic, int diastolic) {
        setSystolic(systolic);
        setDiastolic(diastolic);
    }

}
