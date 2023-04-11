package ies.healthmanager.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Data
@Table(name = "Patients")
@JsonFilter("patientFilter")
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName, lastName;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<HealthState> healthStates;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<HeartRate> heartRates;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<BodyTemperature> bodyTemperatures;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<BloodPressure> bloodPressures;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<RespirationRate> respirationRates;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<OxygenSaturation> oxygenSaturations;

    public Patient() {
    }

    public Patient(Patient p) {
        this.id = p.getId();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.age = p.getAge();
        this.gender = p.getGender();
        this.bodyTemperatures = p.getBodyTemperatures();
        this.heartRates = p.getHeartRates();
        this.respirationRates = p.getRespirationRates();
        this.bodyTemperatures = p.getBodyTemperatures();
        this.bloodPressures = p.getBloodPressures();
        this.oxygenSaturations = p.getOxygenSaturations();
        this.healthStates = getHealthStates();
    }

    public static Patient CopySkippingNulls(Patient from, Patient to) {
        if (from.getId() > 0)
            to.setId(from.getId());
        if (from.getFirstName() != null && !from.getFirstName().equals(""))
            to.setFirstName(from.getFirstName());
        if (from.getLastName() != null && !from.getLastName().equals(""))
            to.setLastName(from.getLastName());
        if (from.getAge() > 0)
            to.setAge(from.getAge());
        if (from.getGender() != null)
            to.setGender(from.getGender());
        return to;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Patient patient = (Patient) o;
        return id == patient.id;
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
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    @JsonIgnore
    public String getFullName() {
        return firstName + " " + lastName;
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

    @JsonIgnore
    @Transient // Dont persist in DB
    private int healthStateMaxAttrTimeDiffSeconds = 60 * 5;

    public List<HealthState> getHealthStates() {
        if (heartRates == null)
            return new ArrayList<HealthState>();
        else if (heartRates.size() == 0)
            return new ArrayList<HealthState>();
        if (bloodPressures == null)
            return new ArrayList<HealthState>();
        else if (bloodPressures.size() == 0)
            return new ArrayList<HealthState>();
        if (bodyTemperatures == null)
            return new ArrayList<HealthState>();
        else if (bodyTemperatures.size() == 0)
            return new ArrayList<HealthState>();
        if (respirationRates == null)
            return new ArrayList<HealthState>();
        else if (respirationRates.size() == 0)
            return new ArrayList<HealthState>();
        if (oxygenSaturations == null)
            return new ArrayList<HealthState>();
        else if (oxygenSaturations.size() == 0)
            return new ArrayList<HealthState>();

        List<HealthAttribute> healthAttributes = new ArrayList<HealthAttribute>();
        healthAttributes.addAll(heartRates);
        healthAttributes.addAll(bloodPressures);
        healthAttributes.addAll(bodyTemperatures);
        healthAttributes.addAll(respirationRates);
        healthAttributes.addAll(oxygenSaturations);

        Set<LocalDateTime> visitedDateTimes = new HashSet<LocalDateTime>();

        for (HealthAttribute healthAttr : healthAttributes) {
            LocalDateTime dateTime = healthAttr.getDateTime(); // "2022-12-25T15:34:42"
            if (visitedDateTimes.contains(dateTime))
                continue;

            LocalDateTime lowerBound = dateTime.minusSeconds(healthStateMaxAttrTimeDiffSeconds);
            LocalDateTime upperBound = dateTime.plusSeconds(healthStateMaxAttrTimeDiffSeconds);
            lowerBound = lowerBound.plusSeconds(60 - lowerBound.getSecond()); // "...:00"
            while (lowerBound.isBefore(upperBound) || lowerBound.isEqual(upperBound)) {
                if (lowerBound.isAfter(LocalDateTime.now()))
                    break;
                if (!visitedDateTimes.contains(lowerBound)) {
                    HealthState healthState = calculateHealthStateAtDateTime(lowerBound);
                    if (healthState != null)
                        healthStates.add(healthState);
                    visitedDateTimes.add(lowerBound);
                }
                lowerBound = lowerBound.plusMinutes(1);
            }
            visitedDateTimes.add(dateTime);
        }

        return healthStates;

    }

    public HealthState calculateHealthStateAtDateTime(LocalDateTime dateTime) {
        HeartRate closestHeartRate = null;
        for (HeartRate heartRate : heartRates) {
            long diffSeconds = Math.abs(ChronoUnit.SECONDS.between(heartRate.getDateTime(), dateTime));
            if (diffSeconds <= healthStateMaxAttrTimeDiffSeconds) { // If in time frame, consider it
                if (closestHeartRate == null)
                    closestHeartRate = heartRate;
                else {
                    // Is this heart rate closer to dateTime? If yes, use it instead
                    long diffClosest = Math.abs(ChronoUnit.SECONDS.between(closestHeartRate.getDateTime(), dateTime));
                    if (diffSeconds < diffClosest)
                        closestHeartRate = heartRate;
                }
            }
        }
        if (closestHeartRate == null)
            return null;

        BloodPressure closestBloodPressure = null;
        for (BloodPressure bloodPressure : bloodPressures) {
            long diffSeconds = Math.abs(ChronoUnit.SECONDS.between(bloodPressure.getDateTime(), dateTime));
            if (diffSeconds <= healthStateMaxAttrTimeDiffSeconds) { // If in time frame, consider it
                if (closestBloodPressure == null)
                    closestBloodPressure = bloodPressure;
                else {
                    // Is this heart rate closer to dateTime? If yes, use it instead
                    long diffClosest = Math
                            .abs(ChronoUnit.SECONDS.between(closestBloodPressure.getDateTime(), dateTime));
                    if (diffSeconds < diffClosest)
                        closestBloodPressure = bloodPressure;
                }
            }
        }
        if (closestBloodPressure == null)
            return null;

        BodyTemperature closestBodyTemperature = null;
        for (BodyTemperature bodyTemperature : bodyTemperatures) {
            long diffSeconds = Math.abs(ChronoUnit.SECONDS.between(bodyTemperature.getDateTime(), dateTime));
            if (diffSeconds <= healthStateMaxAttrTimeDiffSeconds) { // If in time frame, consider it
                if (closestBodyTemperature == null)
                    closestBodyTemperature = bodyTemperature;
                else {
                    // Is this heart rate closer to dateTime? If yes, use it instead
                    long diffClosest = Math
                            .abs(ChronoUnit.SECONDS.between(closestBodyTemperature.getDateTime(), dateTime));
                    if (diffSeconds < diffClosest)
                        closestBodyTemperature = bodyTemperature;
                }
            }
        }
        if (closestBodyTemperature == null)
            return null;

        RespirationRate closestRespirationRate = null;
        for (RespirationRate respirationRate : respirationRates) {
            long diffSeconds = Math.abs(ChronoUnit.SECONDS.between(respirationRate.getDateTime(), dateTime));
            if (diffSeconds <= healthStateMaxAttrTimeDiffSeconds) { // If in time frame, consider it
                if (closestRespirationRate == null)
                    closestRespirationRate = respirationRate;
                else {
                    // Is this heart rate closer to dateTime? If yes, use it instead
                    long diffClosest = Math
                            .abs(ChronoUnit.SECONDS.between(closestRespirationRate.getDateTime(), dateTime));
                    if (diffSeconds < diffClosest)
                        closestRespirationRate = respirationRate;
                }
            }
        }
        if (closestRespirationRate == null)
            return null;

        OxygenSaturation closestOxygenSaturation = null;
        for (OxygenSaturation oxygenSaturation : oxygenSaturations) {
            long diffSeconds = Math.abs(ChronoUnit.SECONDS.between(oxygenSaturation.getDateTime(), dateTime));
            if (diffSeconds <= healthStateMaxAttrTimeDiffSeconds) { // If in time frame, consider it
                if (closestOxygenSaturation == null)
                    closestOxygenSaturation = oxygenSaturation;
                else {
                    // Is this heart rate closer to dateTime? If yes, use it instead
                    long diffClosest = Math
                            .abs(ChronoUnit.SECONDS.between(closestOxygenSaturation.getDateTime(), dateTime));
                    if (diffSeconds < diffClosest)
                        closestOxygenSaturation = oxygenSaturation;
                }
            }
        }
        if (closestOxygenSaturation == null)
            return null;

        HealthStateEnum healthStateEnum = calculateHealthStateEnum(closestHeartRate,
                closestBloodPressure,
                closestBodyTemperature,
                closestRespirationRate,
                closestOxygenSaturation);
        HealthState healthState = new HealthState();
        healthState.setId(-1);
        healthState.setPatient(this);
        healthState.setState(healthStateEnum);
        healthState.setDateTime(dateTime);
        return healthState;
    }

    public static HealthStateEnum calculateHealthStateEnum(HeartRate HR, BloodPressure BP, BodyTemperature BT,
            RespirationRate RR, OxygenSaturation OS) {
        int heartRate = HR.getHeartRate();
        BloodPressureCategory BPCategory = BP.getCategory();
        double bodyTemperature = BT.getBodyTemperature();
        int respirationRate = RR.getRespirationRate();
        double oxygenSaturation = OS.getOxygenSaturation();

        if (heartRate <= 30 || heartRate >= 155)
            return HealthStateEnum.VERY_UNHEALTHY;
        if (BPCategory == BloodPressureCategory.HIGH_STAGE_2 || BPCategory == BloodPressureCategory.HYPERTENSIVE_CRISIS)
            return HealthStateEnum.VERY_UNHEALTHY;
        if (bodyTemperature < 35 || bodyTemperature > 39.5)
            return HealthStateEnum.VERY_UNHEALTHY;
        if (respirationRate < 4 || respirationRate > 30)
            return HealthStateEnum.VERY_UNHEALTHY;
        if (oxygenSaturation < 0.9)
            return HealthStateEnum.VERY_UNHEALTHY;

        double bad = 0.0;
        if (heartRate <= 40)
            bad += 3;
        else if (heartRate <= 50)
            bad += 2;
        else if (heartRate < 60)
            bad += 1;
        else if (heartRate >= 140)
            bad += 3;
        else if (heartRate >= 120)
            bad += 2;
        else if (heartRate > 100)
            bad += 1;
        if (BPCategory == BloodPressureCategory.ELEVATED)
            bad += 3;
        else if (BPCategory == BloodPressureCategory.HIGH_STAGE_1)
            bad += 5;
        if (respirationRate < 9)
            bad += 3;
        else if (respirationRate < 12)
            bad += 1.5;
        else if (respirationRate > 25)
            bad += 3;
        else if (respirationRate > 20)
            bad += 1.5;
        // Healthy body temp: 36.1 to 37.2
        if (bodyTemperature < 35.5)
            bad += 3.5;
        else if (bodyTemperature < 36.1)
            bad += 1.5;
        else if (bodyTemperature > 39)
            bad += 6;
        else if (bodyTemperature > 38)
            bad += 3;
        else if (bodyTemperature > 37.2)
            bad += 1.5;
        if (oxygenSaturation < 0.925)
            bad += 4;
        else if (oxygenSaturation < 0.95)
            bad += 2;

        double max = 3 + 5 + 3 + 6 + 4;
        if (bad >= 0.8 * max)
            return HealthStateEnum.VERY_UNHEALTHY;
        else if (bad >= 0.6 * max)
            return HealthStateEnum.UNHEALTHY;
        else if (bad >= 0.4 * max)
            return HealthStateEnum.NEUTRAL;
        else if (bad >= 0.2 * max)
            return HealthStateEnum.HEALTHY;
        else
            return HealthStateEnum.VERY_HEALTHY;
    }

    public HealthState getLatestHealthState() {
        getHealthStates();
        if (this.healthStates == null || this.healthStates.size() == 0)
            return null;

        HealthState latestHealthState = healthStates.get(0);
        for (HealthState healthState : healthStates)
            if (healthState != null)
                if (healthState.getDateTime().isAfter(latestHealthState.getDateTime()))
                    latestHealthState = healthState;

        return latestHealthState;
    }

    public void setHealthStates(List<HealthState> newHealthStates) {
        this.healthStates = newHealthStates;
    }

    public void addHealthState(HealthState newHealthState) {
        this.healthStates.add(newHealthState);
    }

    public void addHealthStates(List<HealthState> newHealthStates) {
        for (HealthState healthState : newHealthStates)
            this.healthStates.add(healthState);
    }

    public List<HeartRate> getHeartRates() {
        return this.heartRates;
    }

    public HeartRate getLatestHeartRate() {
        if (this.heartRates.size() == 0)
            return null;

        HeartRate latestHeartRate = heartRates.get(0);
        for (HeartRate heartRate : heartRates)
            if (heartRate.getDateTime().isAfter(latestHeartRate.getDateTime()))
                latestHeartRate = heartRate;

        return latestHeartRate;

    }

    public void setHeartRates(List<HeartRate> newHeartRates) {
        this.heartRates = newHeartRates;
    }

    public void addHeartRate(HeartRate newHeartRate) {
        this.heartRates.add(newHeartRate);
    }

    public void addHeartRates(List<HeartRate> newHeartRates) {
        for (HeartRate heartRate : newHeartRates)
            this.heartRates.add(heartRate);
    }

    public List<BodyTemperature> getBodyTemperatures() {
        return this.bodyTemperatures;
    }

    public BodyTemperature getLatestBodyTemperature() {
        if (this.bodyTemperatures.size() == 0)
            return null;

        BodyTemperature latestBodyTemperature = bodyTemperatures.get(0);
        for (BodyTemperature bodyTemperature : bodyTemperatures)
            if (bodyTemperature.getDateTime().isAfter(latestBodyTemperature.getDateTime()))
                latestBodyTemperature = bodyTemperature;

        return latestBodyTemperature;
    }

    public void setBodyTemperatures(List<BodyTemperature> newBodyTemperatures) {
        this.bodyTemperatures = newBodyTemperatures;
    }

    public void addBodyTemperature(BodyTemperature newBodyTemperature) {
        this.bodyTemperatures.add(newBodyTemperature);
    }

    public void addBodyTemperatures(List<BodyTemperature> newBodyTemperatures) {
        for (BodyTemperature bodyTemperature : newBodyTemperatures)
            this.bodyTemperatures.add(bodyTemperature);
    }

    public List<BloodPressure> getBloodPressures() {
        return this.bloodPressures;
    }

    public BloodPressure getLatestBloodPressure() {
        if (this.bloodPressures.size() == 0)
            return null;

        BloodPressure latestBloodPressure = bloodPressures.get(0);
        for (BloodPressure bloodPressure : bloodPressures)
            if (bloodPressure.getDateTime().isAfter(latestBloodPressure.getDateTime()))
                latestBloodPressure = bloodPressure;

        return latestBloodPressure;
    }

    public void setBloodPressures(List<BloodPressure> newBloodPressures) {
        this.bloodPressures = newBloodPressures;
    }

    public void addBloodPressure(BloodPressure newBloodPressure) {
        this.bloodPressures.add(newBloodPressure);
    }

    public void addBloodPressures(List<BloodPressure> newBloodPressures) {
        for (BloodPressure bloodPressure : newBloodPressures)
            this.bloodPressures.add(bloodPressure);
    }

    public List<RespirationRate> getRespirationRates() {
        return this.respirationRates;
    }

    public RespirationRate getLatestRespirationRate() {
        if (this.respirationRates.size() == 0)
            return null;

        RespirationRate latestRespirationRate = respirationRates.get(0);
        for (RespirationRate respirationRate : respirationRates)
            if (respirationRate.getDateTime().isAfter(latestRespirationRate.getDateTime()))
                latestRespirationRate = respirationRate;

        return latestRespirationRate;
    }

    public void setRespirationRates(List<RespirationRate> newRespirationRates) {
        this.respirationRates = newRespirationRates;
    }

    public void addRespirationRate(RespirationRate newRespirationRate) {
        this.respirationRates.add(newRespirationRate);
    }

    public void addRespirationRates(List<RespirationRate> newRespirationRates) {
        for (RespirationRate respirationRate : newRespirationRates)
            this.respirationRates.add(respirationRate);
    }

    public List<OxygenSaturation> getOxygenSaturations() {
        return this.oxygenSaturations;
    }

    public OxygenSaturation getLatestOxygenSaturation() {
        if (this.oxygenSaturations.size() == 0)
            return null;

        OxygenSaturation latestOxygenSaturation = oxygenSaturations.get(0);
        for (OxygenSaturation oxygenSaturation : oxygenSaturations)
            if (oxygenSaturation.getDateTime().isAfter(latestOxygenSaturation.getDateTime()))
                latestOxygenSaturation = oxygenSaturation;

        return latestOxygenSaturation;
    }

    public void setOxygenSaturations(List<OxygenSaturation> newOxygenSaturations) {
        this.oxygenSaturations = newOxygenSaturations;
    }

    public void addOxygenSaturation(OxygenSaturation newOxygenSaturation) {
        this.oxygenSaturations.add(newOxygenSaturation);
    }

    public void addOxygenSaturations(List<OxygenSaturation> newOxygenSaturations) {
        for (OxygenSaturation oxygenSaturation : newOxygenSaturations)
            this.oxygenSaturations.add(oxygenSaturation);
    }

}