package ies.healthmanager.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.healthmanager.entities.BloodPressure;
import ies.healthmanager.repositories.bloodPressures.BloodPressuresRepository;

@Service
public class BloodPressuresService {

    @Autowired
    private BloodPressuresRepository repository;

    public List<BloodPressure> getBloodPressures() {
        List<BloodPressure> bloodPressures = repository.findAll();
        System.out.println("Got " + bloodPressures.size() + " bloodPressures");
        return bloodPressures;
    }

    public List<BloodPressure> getBloodPressuresWithParams(Map<String, String> params) {
        List<BloodPressure> bloodPressures = repository.getBloodPressuresCustomQuery(params);
        int size = 0;
        if (bloodPressures != null)
            size = bloodPressures.size();
        System.out.println("Got " + size + " bloodPressures");
        return bloodPressures;
    }

    public BloodPressure getBloodPressureById(long id) {
        BloodPressure p = repository.findById(id).orElse(null);
        if (p != null)
            System.out.println("Got bloodPressure " + p.toStringFormal());
        else
            System.out.println("BloodPressure id=" + id + " does not exist");
        return p;
    }

    public BloodPressure saveBloodPressure(BloodPressure bloodPressure) {
        BloodPressure bloodPressureAfterSave = repository.save(bloodPressure);
        System.out.println("Saved bloodPressure " + bloodPressureAfterSave.toStringFormal());
        return bloodPressureAfterSave;
    }

    /*
     * public List<BloodPressure> saveBloodPressures(List<BloodPressure>
     * bloodPressures) {
     * List<BloodPressure> bloodPressuresAfterSave =
     * repository.saveAll(bloodPressures);
     * System.out.println("Saved " + bloodPressuresAfterSave.size() +
     * " bloodPressures");
     * return bloodPressuresAfterSave;
     * }
     */

    public BloodPressure updateBloodPressure(BloodPressure bloodPressure) {
        BloodPressure existingBloodPressure = repository.findById(bloodPressure.getId()).orElse(null);
        if (existingBloodPressure == null) {
            System.out.println("Can't update bloodPressure id=" + bloodPressure.getId()
                    + " because it does not exist in database");
            return null;
        }
        existingBloodPressure.setPatient(bloodPressure.getPatient());
        existingBloodPressure.setSystolic(bloodPressure.getSystolic());
        existingBloodPressure.setDiastolic(bloodPressure.getDiastolic());
        existingBloodPressure.setDateTime(bloodPressure.getDateTime());
        BloodPressure p = repository.save(existingBloodPressure);
        System.out.println("Updated bloodPressure id=" + p.getId());
        return p;
    }

    public void deleteBloodPressure(long id) {
        repository.deleteById(id);
        System.out.println("Deleted bloodPressure id=" + id);
    }

}