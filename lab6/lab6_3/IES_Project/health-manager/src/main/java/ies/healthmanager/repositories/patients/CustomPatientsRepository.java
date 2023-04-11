package ies.healthmanager.repositories.patients;

import java.util.List;
import java.util.Map;

import ies.healthmanager.entities.Patient;

public interface CustomPatientsRepository {
    List<Patient> getPatientsCustomQuery(Map<String, String> params);
}
