package ies.healthmanager.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.healthmanager.entities.RespirationRate;
import ies.healthmanager.repositories.respirationRates.RespirationRatesRepository;

@Service
public class RespirationRatesService {

    @Autowired
    private RespirationRatesRepository repository;

    public List<RespirationRate> getRespirationRates() {
        List<RespirationRate> respirationRates =  repository.findAll();
        System.out.println("Got " + respirationRates.size() + " respirationRates");
        return respirationRates;
    }

    public List<RespirationRate> getRespirationRatesWithParams(Map<String, String> params) {
        List<RespirationRate> respirationRates = repository.getRespirationRatesCustomQuery(params);
        int size = 0;
        if (respirationRates != null)
            size = respirationRates.size();
        System.out.println("Got " + size + " respirationRates");
        return respirationRates;
    }

    public RespirationRate getRespirationRateById(long id) {
        RespirationRate p = repository.findById(id).orElse(null);
        if (p != null)
            System.out.println("Got respirationRate " + p.toStringFormal());
        else
            System.out.println("RespirationRate id=" + id + " does not exist");
        return p;
    }

    public RespirationRate saveRespirationRate(RespirationRate respirationRate) {
        RespirationRate respirationRateAfterSave = repository.save(respirationRate);
        System.out.println("Saved respirationRate " + respirationRateAfterSave.toStringFormal());
        return respirationRateAfterSave;
    }

    /*
    public List<RespirationRate> saveRespirationRates(List<RespirationRate> respirationRates) {
        List<RespirationRate> respirationRatesAfterSave = repository.saveAll(respirationRates);
        System.out.println("Saved " + respirationRatesAfterSave.size() + " respirationRates");
        return respirationRatesAfterSave;
    }
    */

    public RespirationRate updateRespirationRate(RespirationRate respirationRate) {
        RespirationRate existingRespirationRate = repository.findById(respirationRate.getId()).orElse(null);
        if (existingRespirationRate == null)
        {
            System.out.println("Can't update respirationRate id=" + respirationRate.getId() + " because it does not exist in database");
            return null;
        }
        existingRespirationRate.setPatient(respirationRate.getPatient());
        existingRespirationRate.setRespirationRate(respirationRate.getRespirationRate());
        existingRespirationRate.setDateTime(respirationRate.getDateTime());
        RespirationRate p = repository.save(existingRespirationRate);
        System.out.println("Updated respirationRate id=" + p.getId());
        return p;
    }  

    public void deleteRespirationRate(long id) {
        repository.deleteById(id);
        System.out.println("Deleted respirationRate id=" + id);
    }



}