package ies.healthmanager.repositories.heartRates;

import java.util.List;
import java.util.Map;

import ies.healthmanager.entities.HeartRate;

public interface CustomHeartRatesRepository {
    List<HeartRate> getHeartRatesCustomQuery(Map<String, String> params);
}
