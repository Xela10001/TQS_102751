package ies.healthmanager.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.healthmanager.entities.BodyTemperature;
import ies.healthmanager.repositories.bodyTemperatures.BodyTemperaturesRepository;

@Service
public class BodyTemperaturesService {

    @Autowired
    private BodyTemperaturesRepository repository;

    public List<BodyTemperature> getBodyTemperatures() {
        List<BodyTemperature> bodyTemperatures =  repository.findAll();
        System.out.println("Got " + bodyTemperatures.size() + " bodyTemperatures");
        return bodyTemperatures;
    }

        public List<BodyTemperature> getBodyTemperaturesWithParams(Map<String, String> params) {
        List<BodyTemperature> bodyTemperatures = repository.getBodyTemperaturesCustomQuery(params);
        int size = 0;
        if (bodyTemperatures != null)
            size = bodyTemperatures.size();
        System.out.println("Got " + size + " bodyTemperatures");
        return bodyTemperatures;
    }

    public BodyTemperature getBodyTemperatureById(long id) {
        BodyTemperature p = repository.findById(id).orElse(null);
        if (p != null)
            System.out.println("Got bodyTemperature " + p.toStringFormal());
        else
            System.out.println("BodyTemperature id=" + id + " does not exist");
        return p;
    }

    public BodyTemperature saveBodyTemperature(BodyTemperature bodyTemperature) {
        BodyTemperature bodyTemperatureAfterSave = repository.save(bodyTemperature);
        System.out.println("Saved bodyTemperature " + bodyTemperatureAfterSave.toStringFormal());
        return bodyTemperatureAfterSave;
    }

    /*
    public List<BodyTemperature> saveBodyTemperatures(List<BodyTemperature> bodyTemperatures) {
        List<BodyTemperature> bodyTemperaturesAfterSave = repository.saveAll(bodyTemperatures);
        System.out.println("Saved " + bodyTemperaturesAfterSave.size() + " bodyTemperatures");
        return bodyTemperaturesAfterSave;
    }
    */

    public BodyTemperature updateBodyTemperature(BodyTemperature bodyTemperature) {
        BodyTemperature existingBodyTemperature = repository.findById(bodyTemperature.getId()).orElse(null);
        if (existingBodyTemperature == null)
        {
            System.out.println("Can't update bodyTemperature id=" + bodyTemperature.getId() + " because it does not exist in database");
            return null;
        }
        existingBodyTemperature.setPatient(bodyTemperature.getPatient());
        existingBodyTemperature.setBodyTemperature(bodyTemperature.getBodyTemperature());
        existingBodyTemperature.setDateTime(bodyTemperature.getDateTime());
        BodyTemperature p = repository.save(existingBodyTemperature);
        System.out.println("Updated bodyTemperature id=" + p.getId());
        return p;
    }  

    public void deleteBodyTemperature(long id) {
        repository.deleteById(id);
        System.out.println("Deleted bodyTemperature id=" + id);
    }



}