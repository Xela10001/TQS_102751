package ies.healthmanager.repositories.oxygenSaturations;

import java.util.List;
import java.util.Map;

import ies.healthmanager.entities.OxygenSaturation;

public interface CustomOxygenSaturationsRepository {
    List<OxygenSaturation> getOxygenSaturationsCustomQuery(Map<String, String> params);
}
