package ies.healthmanager.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ies.healthmanager.Helper;

@Entity
@Table(name="BodyTemperatures")
@JsonPropertyOrder({ "id", "patient", "dateTime", "bodyTemperature"})
public class BodyTemperature extends HealthAttribute 
{

    // ºC (celsius)
    // usually 36.1 ºC to 37.1 ºC
    private double bodyTemperature;

    public BodyTemperature()
    {
        
    }
    

    @Override
    public String toString()
    {
        return String.valueOf(this.bodyTemperature) + " ºC";
    }

    public String toStringFormal() {
        return String.format("{id=%d, dateTime=%s, patientId=%d, bodyTemperature=%.2f}", (int)getId(),
                getDateTime().toString().replace("T", " "),  (int) getPatient().getId(),getBodyTemperature());
    }

    public double getBodyTemperature(){
        return this.bodyTemperature;
    }
    public void setBodyTemperature(double bodyTemperature){
        this.bodyTemperature = Helper.round(bodyTemperature,1);
    }

}