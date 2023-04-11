package ies.healthmanager.entities;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "RespirationRates")
@JsonPropertyOrder({ "id", "patient", "dateTime", "respirationRate" })
public class RespirationRate extends HealthAttribute {

    // breaths/min
    // usually 12 to 20
    private int respirationRate;

    public RespirationRate() {

    }

    @Override
    public String toString() {
        return String.valueOf(this.respirationRate) + " breaths/min";
    }

    public String toStringFormal() {
        return String.format("{id=%d, dateTime=%s, patientId=%d, respirationRate=%d}", (int)getId(),
                getDateTime().toString().replace("T", " "), (int) getPatient().getId(), getRespirationRate());
    }

    public static Comparator<RespirationRate> getRespirationRateComparator(boolean... optionalReverse) {
        boolean reverse = (optionalReverse.length >= 1) ? optionalReverse[0] : false;
        Comparator<RespirationRate> comparator = null;
        if (reverse)
            comparator = (a, b) -> (int) (b.getRespirationRate() - a.getRespirationRate());
        else
            comparator = (a, b) -> (int) (a.getRespirationRate() - b.getRespirationRate());
        return comparator;
    }

    public int getRespirationRate() {
        return this.respirationRate;
    }

    public void setRespirationRate(int respirationRate) {
        this.respirationRate = respirationRate;
    }

}