package ies.healthmanager.repositories.heartRates;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ies.healthmanager.Helper;
import ies.healthmanager.entities.HeartRate;

public class CustomHeartRatesRepositoryImpl implements CustomHeartRatesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<HeartRate> getHeartRatesCustomQuery(Map<String, String> params) {
        try {
            Query query = Helper.getQueryOfHealthAttributeWithParams(entityManager, HeartRate.class, params);
            return query.getResultList(); // execute query

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            return null;
        }

    }

}
