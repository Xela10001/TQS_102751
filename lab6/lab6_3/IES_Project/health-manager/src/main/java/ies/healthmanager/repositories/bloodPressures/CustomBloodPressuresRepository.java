package ies.healthmanager.repositories.bloodPressures;

import java.util.List;
import java.util.Map;

import ies.healthmanager.entities.BloodPressure;

public interface CustomBloodPressuresRepository {
    List<BloodPressure> getBloodPressuresCustomQuery(Map<String, String> params);
}
