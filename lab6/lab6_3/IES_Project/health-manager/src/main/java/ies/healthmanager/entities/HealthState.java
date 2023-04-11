package ies.healthmanager.entities;

import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "HealthStates")
@JsonPropertyOrder({ "id", "patient", "dateTime", "state" })
public class HealthState extends HealthAttribute {
    @Column(name = "state")
    @Enumerated(EnumType.ORDINAL)
    private HealthStateEnum state;

    public HealthState() {
    }

    @Override
    public String toString() {
        return this.state.toString();
    }

    public static Comparator<HealthState> getComparator(String[] fieldsOrdered) {
        // fieldsOrdered = ["id","-dateTime"] for example

        // Lower case every field
        for (int i = 0; i < fieldsOrdered.length; i++)
            fieldsOrdered[i] = fieldsOrdered[i].toLowerCase();

        Comparator<HealthState> comparator = (a, b) -> {
            int diff;
            for (String field : fieldsOrdered) {
                boolean reverse = false;
                if (field.charAt(0) == '-') {
                    field = field.substring(1, field.length());
                    reverse = true;
                }
                switch (field) {
                    case "patient":
                    case "patientid":
                        diff = (int) (a.getPatient().getId() - b.getPatient().getId());
                        if (reverse)
                            diff = -diff;
                        if (diff != 0)
                            return diff;
                    case "datetime":
                        diff = (int) Duration.between(b.getDateTime(), a.getDateTime()).toSeconds();
                        if (reverse)
                            diff = -diff;
                        if (diff != 0)
                            return diff;
                    case "state":
                    case "healthstate":
                        HealthStateEnum healthStateEnumValues[] = HealthStateEnum.values();
                        List<HealthStateEnum> healthStateEnumList = Arrays.asList(healthStateEnumValues);
                        diff = healthStateEnumList.indexOf(b.getState()) - healthStateEnumList.indexOf(a.getState());
                        if (reverse)
                            diff = -diff;
                        if (diff != 0)
                            return diff;
                    default:
                        break;
                }
            }
            return 0;

        };

        return comparator;
    }

    @Override
    @JsonIgnore
    public long getId() {
        return super.getId();
    }

    public HealthStateEnum getState() {
        return this.state;
    }

    public void setState(HealthStateEnum state) {
        this.state = state;
    }

}