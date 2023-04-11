package ies.healthmanager.controllers;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import ies.healthmanager.entities.Patient;
import ies.healthmanager.services.PatientsService;

@RestController
@RequestMapping("/api/v1/")
public class PatientsRestController {

    @Autowired
    private PatientsService service;

    @GetMapping("patients")
    public MappingJacksonValue getPatients(
            @RequestParam(name = "healthData", defaultValue = "none", required = false) String strHealthData,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "limit", defaultValue = "", required = false) String limit) {

        Map<String, String> params = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        params.put("sortBy", sortBy);
        params.put("limit", limit);

        List<Patient> patients = service.getPatientsWithParams(params);

        strHealthData = strHealthData.toLowerCase().trim();
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("healthStates",
                    "heartRates", "bloodPressures", "bodyTemperatures", "respirationRates", "oxygenSaturations",
                    "latestHealthState", "latestHeartRate", "latestBloodPressure", "latestBodyTemperature",
                    "latestRespirationRate", "latestOxygenSaturation");
        if (strHealthData.equals("all"))
            simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("latestHealthState",
                "latestHeartRate","latestBloodPressure","latestBodyTemperature","latestRespirationRate","latestOxygenSaturation");
        else if (strHealthData.equals("latest"))
            simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("healthStates",
                    "heartRates", "bloodPressures", "bodyTemperatures", "respirationRates", "oxygenSaturations");

        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("patientFilter", simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(patients);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;

    }

    @GetMapping("patients/{id}")
    public MappingJacksonValue getPatient(@PathVariable int id,
            @RequestParam(name = "healthData", defaultValue = "none", required = false) String strHealthData) {

        strHealthData = strHealthData.toLowerCase().trim();

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("healthStates",
                    "heartRates", "bloodPressures", "bodyTemperatures", "respirationRates", "oxygenSaturations",
                    "latestHealthState", "latestHeartRate", "latestBloodPressure", "latestBodyTemperature",
                    "latestRespirationRate", "latestOxygenSaturation");
        if (strHealthData.equals("all"))
            simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("latestHealthState",
                "latestHeartRate","latestBloodPressure","latestBodyTemperature","latestRespirationRate","latestOxygenSaturation");
        else if (strHealthData.equals("latest"))
            simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("healthStates",
                    "heartRates", "bloodPressures", "bodyTemperatures", "respirationRates", "oxygenSaturations");
        
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("patientFilter", simpleBeanPropertyFilter);

        Patient patient = service.getPatientById(id);
        if (patient == null)
            return null;

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(patient);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;

    }

    @PostMapping("patients")
    public Patient addPatient(@RequestBody Patient patient) {
        return service.savePatient(patient);
    }

    /*
     * @PostMapping("patients")
     * public List<Patient> addPatients(@RequestBody List<Patient> patients) {
     * return service.savePatients(patients);
     * }
     */

    @PutMapping("patients/{id}")
    public Patient updatePatient(@RequestBody Patient patient) {
        return service.updatePatient(patient);
    }

    @DeleteMapping("patients/{id}")
    public void deletePatient(@PathVariable int id) {
        service.deletePatient(id);
    }

    private long getETag(List<Patient> patients) {
        long tag = 0;
        long p = 31;
        long m = 10 ^ 9 + 7;
        for (Patient patient : patients)
            tag += (p * patient.getId()) % m;
        return tag;
    }

}