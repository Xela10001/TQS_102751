package ies.healthmanager.repositories.bloodPressures;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ies.healthmanager.Helper;
import ies.healthmanager.entities.BloodPressure;

public class CustomBloodPressuresRepositoryImpl implements CustomBloodPressuresRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BloodPressure> getBloodPressuresCustomQuery(Map<String, String> params) {
        try {
            Query query = Helper.getQueryOfHealthAttributeWithParams(entityManager, BloodPressure.class, params);
            return query.getResultList(); // execute query

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            return null;
        }

    }

}
