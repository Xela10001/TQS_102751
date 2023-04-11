package ies.healthmanager.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ies.healthmanager.entities.HealthAttribute;
import ies.healthmanager.entities.HealthState;
import ies.healthmanager.entities.Patient;
import ies.healthmanager.services.PatientsService;

@RestController
@RequestMapping("/api/v1/patients")
public class HealthStatesRestController {
    @Autowired
    private PatientsService patientsService;

    @GetMapping("healthStates")
    public List<HealthState> getHealthStates(
            @RequestParam(name = "sortBy", defaultValue = "-dateTime", required = false) String sortBy,
            @RequestParam(name = "limit", defaultValue = "", required = false) String strLimit,
            @RequestParam(name = "startDateTime", defaultValue = "", required = false) String strStartDateTime,
            @RequestParam(name = "endDateTime", defaultValue = "", required = false) String strEndDateTime,
            @RequestParam(name = "latestOnly", defaultValue = "", required = false) String latestOnly) {

        List<HealthState> allHealthStates = new ArrayList<HealthState>();
        List<Patient> patients = patientsService.getPatients();

        if (latestOnly.trim().toLowerCase().equals("true")) {
            for (Patient patient : patients)
                if (patient.getLatestHealthState() != null)
                    allHealthStates.add(patient.getLatestHealthState());
        } else {
            for (Patient patient : patients)
                allHealthStates.addAll(patient.getHealthStates());
        }

        allHealthStates = (List<HealthState>) HealthAttribute.filterByDateTime(allHealthStates, strStartDateTime,
                strEndDateTime);

        String[] splitted = sortBy.split(",");

        Comparator<HealthState> comparator = HealthState.getComparator(splitted);

        Collections.sort(allHealthStates, comparator);

        if (!strLimit.isBlank()) {
            try {
                int limit = Integer.parseInt(strLimit);
                allHealthStates = allHealthStates.subList(0, limit);
            } catch (Exception e) {

            }
        }

        return allHealthStates;
    }

    @GetMapping("{patientId}/healthStates")
    public List<HealthState> getHealthStates(@PathVariable int patientId,
            @RequestParam(name = "sortBy", defaultValue = "-dateTime", required = false) String sortBy,
            @RequestParam(name = "limit", defaultValue = "", required = false) String strLimit,
            @RequestParam(name = "startDateTime", defaultValue = "", required = false) String strStartDateTime,
            @RequestParam(name = "endDateTime", defaultValue = "", required = false) String strEndDateTime) {

        Patient p = patientsService.getPatientById(patientId);
        if (p == null)
            return null;

        List<HealthState> allHealthStates = p.getHealthStates();

        allHealthStates = (List<HealthState>) HealthAttribute.filterByDateTime(allHealthStates, strStartDateTime,
                strEndDateTime);

        String[] splitted = sortBy.split(",");

        Comparator<HealthState> comparator = HealthState.getComparator(splitted);

        Collections.sort(allHealthStates, comparator);

        if (!strLimit.isBlank()) {
            try {
                int limit = Integer.parseInt(strLimit);
                allHealthStates = allHealthStates.subList(0, limit);
            } catch (Exception e) {

            }
        }

        return allHealthStates;
    }

    /*
     * 
     * @GetMapping("healthStates/{id}")
     * public HealthState getHealthStateById(@PathVariable int id) {
     * return service.getHealthStateById(id);
     * }
     * 
     * @PostMapping("healthStates")
     * public HealthState addHealthState(@RequestBody HealthState healthState) {
     * return service.saveHealthState(healthState);
     * }
     * 
     * @PostMapping("healthStates")
     * public List<HealthState> addHealthStates(@RequestBody List<HealthState>
     * healthStates) {
     * return service.saveHealthStates(healthStates);
     * }
     * 
     * @PutMapping("healthStates/{id}")
     * public HealthState updateHealthState(@RequestBody HealthState healthState) {
     * return service.updateHealthState(healthState);
     * }
     * 
     * @DeleteMapping("healthStates/{id}")
     * public void deleteHealthState(@PathVariable int id) {
     * service.deleteHealthState(id);
     * }
     * 
     */

}