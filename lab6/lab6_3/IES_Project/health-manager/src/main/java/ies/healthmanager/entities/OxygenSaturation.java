package ies.healthmanager.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ies.healthmanager.Helper;

@Entity
@Table(name="OxygenSaturations")
@JsonPropertyOrder({ "id", "patient", "dateTime", "oxygenSaturation"})
public class OxygenSaturation extends HealthAttribute
{
    // usually 0.95 (95%) to 1 (100%)
    private double oxygenSaturation;

    public OxygenSaturation()
    {
        
    }

    @Override
    public String toString()
    {
        return String.valueOf(this.oxygenSaturation);
    }

    public String toStringFormal() {
        return String.format("{id=%d, dateTime=%s, patientId=%d, oxygenSaturation=%.4f}", (int)getId(),
                getDateTime().toString().replace("T", " "),  (int) getPatient().getId(),getOxygenSaturation());
    }

    public double getOxygenSaturation(){
        return this.oxygenSaturation;
    }
    public void setOxygenSaturation(double oxygenSaturation){
        if (oxygenSaturation > 1)
            this.oxygenSaturation = 1;
        else if (oxygenSaturation < 0)
            this.oxygenSaturation = 0;
        else 
            this.oxygenSaturation = Helper.round(oxygenSaturation, 4);
    }
}