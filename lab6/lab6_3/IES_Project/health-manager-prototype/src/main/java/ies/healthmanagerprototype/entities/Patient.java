package ies.healthmanagerprototype.entities;

import java.io.Serializable;

public class Patient implements Serializable {

    private int id;
    private String firstName, lastName;
    private int age;
    private Gender gender;
    private HealthState healthState;
    private int heartRate; // heartbeats/min (usually 60 to 100)
    private int respirationRate; // breaths/min (usually 12 to 20)
    private double bodyTemperature; // average is around 37 celsius
    private BloodPressure bloodPressure;
    private double oxygenSaturation; // usually 0.95 (95%) to 1 (100%)

    public Patient() {

    }

    public Patient(int id) {
        this.id = id;
    }

    public Patient(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Patient patient = (Patient) o;
    
        return id == patient.id;
    }


    
    @Override
    public int hashCode() {
        return id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public HealthState getHealthState() {
        return healthState;
    }

    public void setHealthState(HealthState healthState) {
        this.healthState = healthState;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getRespirationRate() {
        return respirationRate;
    }

    public void setRespirationRate(int respirationRate) {
        this.respirationRate = respirationRate;
    }

    public double getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(double bodyTemperature) {
        this.bodyTemperature = round(bodyTemperature, 1);
    }

    public BloodPressure getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(BloodPressure bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public double getOxygenSaturation() {
        return oxygenSaturation;
    }

    public void setOxygenSaturation(double oxygenSaturation) {
        if (oxygenSaturation > 1f)
            this.oxygenSaturation = 1f;
        else if (oxygenSaturation < 0f)
            this.oxygenSaturation = 0f;
        else
            this.oxygenSaturation = round(oxygenSaturation, 4);
    }

    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
    

}