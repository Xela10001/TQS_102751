package ies.healthmanager.repositories.bloodPressures;

import org.springframework.data.jpa.repository.JpaRepository;
import ies.healthmanager.entities.BloodPressure;


public interface BloodPressuresRepository extends JpaRepository<BloodPressure, Long>, CustomBloodPressuresRepository{

}