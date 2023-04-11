package ies.healthmanager.repositories.bodyTemperatures;

import java.util.List;
import java.util.Map;

import ies.healthmanager.entities.BodyTemperature;

public interface CustomBodyTemperaturesRepository {
    List<BodyTemperature> getBodyTemperaturesCustomQuery(Map<String, String> params);
}
