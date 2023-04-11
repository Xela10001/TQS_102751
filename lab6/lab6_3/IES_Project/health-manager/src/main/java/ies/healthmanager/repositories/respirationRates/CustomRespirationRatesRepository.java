package ies.healthmanager.repositories.respirationRates;

import java.util.List;
import java.util.Map;

import ies.healthmanager.entities.RespirationRate;

public interface CustomRespirationRatesRepository {
    List<RespirationRate> getRespirationRatesCustomQuery(Map<String, String> params);
}
