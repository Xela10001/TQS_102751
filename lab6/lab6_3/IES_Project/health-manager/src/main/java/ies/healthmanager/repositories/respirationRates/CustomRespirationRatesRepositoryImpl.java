package ies.healthmanager.repositories.respirationRates;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ies.healthmanager.Helper;
import ies.healthmanager.entities.RespirationRate;

public class CustomRespirationRatesRepositoryImpl implements CustomRespirationRatesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RespirationRate> getRespirationRatesCustomQuery(Map<String, String> params) {
        try {
            Query query = Helper.getQueryOfHealthAttributeWithParams(entityManager, RespirationRate.class, params);
            return query.getResultList(); // execute query

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            return null;
        }

    }

}
