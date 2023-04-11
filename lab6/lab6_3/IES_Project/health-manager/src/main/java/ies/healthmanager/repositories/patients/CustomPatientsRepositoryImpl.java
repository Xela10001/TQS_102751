package ies.healthmanager.repositories.patients;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ies.healthmanager.Helper;
import ies.healthmanager.entities.Patient;

public class CustomPatientsRepositoryImpl implements CustomPatientsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Patient> getPatientsCustomQuery(Map<String, String> params) {
        try {
            Query query = Helper.getQueryOfHealthAttributeWithParams(entityManager, Patient.class, params);
            return query.getResultList(); // execute query

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            return null;
        }

    }

}
