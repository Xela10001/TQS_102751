package ies.healthmanager.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.healthmanager.entities.OxygenSaturation;
import ies.healthmanager.repositories.oxygenSaturations.OxygenSaturationsRepository;

@Service
public class OxygenSaturationsService {

    @Autowired
    private OxygenSaturationsRepository repository;

    public List<OxygenSaturation> getOxygenSaturations() {
        List<OxygenSaturation> oxygenSaturations = repository.findAll();
        System.out.println("Got " + oxygenSaturations.size() + " oxygenSaturations");
        return oxygenSaturations;
    }

    public List<OxygenSaturation> getOxygenSaturationsWithParams(Map<String, String> params) {
        List<OxygenSaturation> oxygenSaturations = repository.getOxygenSaturationsCustomQuery(params);
        int size = 0;
        if (oxygenSaturations != null)
            size = oxygenSaturations.size();
        System.out.println("Got " + size + " oxygenSaturations");
        return oxygenSaturations;
    }

    public OxygenSaturation getOxygenSaturationById(long id) {
        OxygenSaturation p = repository.findById(id).orElse(null);
        if (p != null)
            System.out.println("Got oxygenSaturation " + p.toStringFormal());
        else
            System.out.println("OxygenSaturation id=" + id + " does not exist");
        return p;
    }

    public OxygenSaturation saveOxygenSaturation(OxygenSaturation oxygenSaturation) {
        OxygenSaturation oxygenSaturationAfterSave = repository.save(oxygenSaturation);
        System.out.println("Saved oxygenSaturation " + oxygenSaturationAfterSave.toStringFormal());
        return oxygenSaturationAfterSave;
    }

    /*
     * public List<OxygenSaturation> saveOxygenSaturations(List<OxygenSaturation>
     * oxygenSaturations) {
     * List<OxygenSaturation> oxygenSaturationsAfterSave =
     * repository.saveAll(oxygenSaturations);
     * System.out.println("Saved " + oxygenSaturationsAfterSave.size() +
     * " oxygenSaturations");
     * return oxygenSaturationsAfterSave;
     * }
     */

    public OxygenSaturation updateOxygenSaturation(OxygenSaturation oxygenSaturation) {
        OxygenSaturation existingOxygenSaturation = repository.findById(oxygenSaturation.getId()).orElse(null);
        if (existingOxygenSaturation == null) {
            System.out.println("Can't update oxygenSaturation id=" + oxygenSaturation.getId()
                    + " because it does not exist in database");
            return null;
        }
        existingOxygenSaturation.setPatient(oxygenSaturation.getPatient());
        existingOxygenSaturation.setOxygenSaturation(oxygenSaturation.getOxygenSaturation());
        existingOxygenSaturation.setDateTime(oxygenSaturation.getDateTime());
        OxygenSaturation p = repository.save(existingOxygenSaturation);
        System.out.println("Updated oxygenSaturation id=" + p.getId());
        return p;
    }

    public void deleteOxygenSaturation(long id) {
        repository.deleteById(id);
        System.out.println("Deleted oxygenSaturation id=" + id);
    }

}