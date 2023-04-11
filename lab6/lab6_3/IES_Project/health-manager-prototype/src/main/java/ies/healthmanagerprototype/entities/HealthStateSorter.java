package ies.healthmanagerprototype.entities;
import ies.healthmanagerprototype.entities.HealthState;
import ies.healthmanagerprototype.entities.Patient;
import java.util.Comparator;
import java.util.List;

public class HealthStateSorter implements Comparator<Patient> {

    public int compare(Patient a, Patient b)
    {
       HealthState healthStates[] = HealthState.values();
       List<HealthState> healthStatesList = java.util.Arrays.asList(healthStates);
       return healthStatesList.indexOf(b.getHealthState()) -  healthStatesList.indexOf(a.getHealthState());

    }
}