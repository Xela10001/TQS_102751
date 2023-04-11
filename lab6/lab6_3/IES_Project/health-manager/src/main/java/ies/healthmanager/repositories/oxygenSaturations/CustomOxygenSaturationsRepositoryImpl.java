package ies.healthmanager.repositories.oxygenSaturations;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ies.healthmanager.Helper;
import ies.healthmanager.entities.OxygenSaturation;

public class CustomOxygenSaturationsRepositoryImpl implements CustomOxygenSaturationsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OxygenSaturation> getOxygenSaturationsCustomQuery(Map<String, String> params) {
        try {
            Query query = Helper.getQueryOfHealthAttributeWithParams(entityManager, OxygenSaturation.class, params);
            return query.getResultList(); // execute query

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            return null;
        }

    }

}
