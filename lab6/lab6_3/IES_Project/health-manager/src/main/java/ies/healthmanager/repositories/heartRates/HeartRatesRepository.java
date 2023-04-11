package ies.healthmanager.repositories.heartRates;

import org.springframework.data.jpa.repository.JpaRepository;
import ies.healthmanager.entities.HeartRate;


public interface HeartRatesRepository extends JpaRepository<HeartRate, Long>, CustomHeartRatesRepository{

}