package ies.healthmanager.repositories.bodyTemperatures;

import org.springframework.data.jpa.repository.JpaRepository;
import ies.healthmanager.entities.BodyTemperature;


public interface BodyTemperaturesRepository extends JpaRepository<BodyTemperature, Long>, CustomBodyTemperaturesRepository{

}