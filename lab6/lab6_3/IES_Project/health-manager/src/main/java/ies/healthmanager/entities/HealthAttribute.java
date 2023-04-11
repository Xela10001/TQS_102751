package ies.healthmanager.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

//import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@MappedSuperclass
public class HealthAttribute implements Comparable<HealthAttribute> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne()
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("patientId")
    @JoinColumn(name = "patientId")
    private Patient patient;

    @Column(name = "dateTime")
    @CreationTimestamp
    private LocalDateTime dateTime;

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        HealthAttribute other = (HealthAttribute) obj;
        return this.getId() == other.getId();

    }

    @Override
    public int compareTo(HealthAttribute other) {
        return (int) (this.getId() - other.getId());
    }

    public static List<? extends HealthAttribute> filterByDateTime(List<? extends HealthAttribute> lst,
            String strStartDateTime,
            String strEndDateTime) {

        // strDateTime "2022-12-10T12:14:47"

        List<HealthAttribute> filtered = new ArrayList<HealthAttribute>();
        LocalDateTime startDateTime = null, endDateTime = null;
        try {
            startDateTime = LocalDateTime.parse(strStartDateTime);
        } catch (Exception e) {
            startDateTime = null;
        }
        try {
            endDateTime = LocalDateTime.parse(strEndDateTime);
        } catch (Exception e) {
            endDateTime = null;
        }

        for (HealthAttribute healthAttr : lst) {
            if (startDateTime != null && healthAttr.getDateTime().isBefore(startDateTime))
                continue;
            if (endDateTime != null && healthAttr.getDateTime().isAfter(endDateTime))
                continue;
            filtered.add(healthAttr);
        }

        return filtered;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

}