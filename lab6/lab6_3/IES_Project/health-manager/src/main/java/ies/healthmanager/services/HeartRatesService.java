package ies.healthmanager.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.healthmanager.entities.HeartRate;
import ies.healthmanager.repositories.heartRates.HeartRatesRepository;

@Service
public class HeartRatesService {

    @Autowired
    private HeartRatesRepository repository;

    public List<HeartRate> getHeartRates() {
        List<HeartRate> heartRates = repository.findAll();
        System.out.println("Got " + heartRates.size() + " heartRates");
        return heartRates;
    }

    public List<HeartRate> getHeartRatesWithParams(Map<String, String> params) {
        List<HeartRate> heartRates = repository.getHeartRatesCustomQuery(params);
        int size = 0;
        if (heartRates != null)
            size = heartRates.size();
        System.out.println("Got " + size + " heartRates");
        return heartRates;
    }

    public HeartRate getHeartRateById(long id) {
        HeartRate p = repository.findById(id).orElse(null);
        if (p != null)
            System.out.println("Got heartRate " + p.toStringFormal());
        else
            System.out.println("HeartRate id=" + id + " does not exist");
        return p;
    }

    public HeartRate saveHeartRate(HeartRate heartRate) {
        HeartRate heartRateAfterSave = repository.save(heartRate);
        System.out.println("Saved heartRate " + heartRateAfterSave.toStringFormal());
        return heartRateAfterSave;
    }

    /*
     * public List<HeartRate> saveHeartRates(List<HeartRate> heartRates) {
     * List<HeartRate> heartRatesAfterSave = repository.saveAll(heartRates);
     * System.out.println("Saved " + heartRatesAfterSave.size() + " heartRates");
     * return heartRatesAfterSave;
     * }
     */

    public HeartRate updateHeartRate(HeartRate heartRate) {
        HeartRate existingHeartRate = repository.findById(heartRate.getId()).orElse(null);
        if (existingHeartRate == null) {
            System.out.println(
                    "Can't update heartRate id=" + heartRate.getId() + " because it does not exist in database");
            return null;
        }
        existingHeartRate.setPatient(heartRate.getPatient());
        existingHeartRate.setHeartRate(heartRate.getHeartRate());
        existingHeartRate.setDateTime(heartRate.getDateTime());
        HeartRate p = repository.save(existingHeartRate);
        System.out.println("Updated heartRate id=" + p.getId());
        return p;
    }

    public void deleteHeartRate(long id) {
        repository.deleteById(id);
        System.out.println("Deleted heartRate id=" + id);
    }

}