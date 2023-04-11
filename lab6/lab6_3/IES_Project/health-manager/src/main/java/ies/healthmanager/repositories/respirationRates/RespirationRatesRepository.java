package ies.healthmanager.repositories.respirationRates;

import org.springframework.data.jpa.repository.JpaRepository;
import ies.healthmanager.entities.RespirationRate;

public interface RespirationRatesRepository
        extends JpaRepository<RespirationRate, Long>, CustomRespirationRatesRepository {

}