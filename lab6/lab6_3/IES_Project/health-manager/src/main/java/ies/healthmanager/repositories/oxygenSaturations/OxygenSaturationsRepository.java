package ies.healthmanager.repositories.oxygenSaturations;

import org.springframework.data.jpa.repository.JpaRepository;
import ies.healthmanager.entities.OxygenSaturation;

public interface OxygenSaturationsRepository
        extends JpaRepository<OxygenSaturation, Long>, CustomOxygenSaturationsRepository {

}