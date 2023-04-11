package ies.healthmanager.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name="HeartRates")
@JsonPropertyOrder({ "id", "patient", "dateTime", "heartRate"})
public class HeartRate extends HealthAttribute 
{
    // heartbeats/min 
    // usually 60 to 100
    private int heartRate;

    public HeartRate()
    {
        
    }

    @Override
    public String toString()
    {
        return String.valueOf(this.heartRate) + " heartbeats/min";
    }

    public String toStringFormal() {
        return String.format("{id=%d, dateTime=%s, patientId=%d, heartRate=%d}", (int)getId(),
                getDateTime().toString().replace("T", " "), (int) getPatient().getId(), getHeartRate());
    }

    public int getHeartRate(){
        return this.heartRate;
    }
    public void setHeartRate(int heartRate){
        this.heartRate = heartRate;
    }

}
