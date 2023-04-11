package ies.healthmanager.repositories.patients;

import org.springframework.data.jpa.repository.JpaRepository;
import ies.healthmanager.entities.Patient;


public interface PatientsRepository extends JpaRepository<Patient, Integer>, CustomPatientsRepository{

}