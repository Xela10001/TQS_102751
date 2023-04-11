package ies.healthmanager.repositories.bodyTemperatures;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ies.healthmanager.Helper;
import ies.healthmanager.entities.BodyTemperature;

public class CustomBodyTemperaturesRepositoryImpl implements CustomBodyTemperaturesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BodyTemperature> getBodyTemperaturesCustomQuery(Map<String, String> params) {
        try {
            Query query = Helper.getQueryOfHealthAttributeWithParams(entityManager, BodyTemperature.class, params);
            return query.getResultList(); // execute query

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            return null;
        }

    }

}
