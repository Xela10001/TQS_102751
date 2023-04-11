package ies.healthmanager.controllers;

//import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ies.healthmanager.entities.BloodPressure;
import ies.healthmanager.services.BloodPressuresService;

@RestController
@RequestMapping("/api/v1/patients")
public class BloodPressuresRestController {

    @Autowired
    private BloodPressuresService service;

    @GetMapping("bloodPressures")
    public List<BloodPressure> getBloodPressures(
            @RequestParam(name = "sortBy", defaultValue = "-dateTime", required = false) String sortBy,
            @RequestParam(name = "limit", defaultValue = "", required = false) String limit,
            @RequestParam(name = "startDateTime", defaultValue = "", required = false) String startDateTime,
            @RequestParam(name = "endDateTime", defaultValue = "", required = false) String endDateTime) {

        Map<String, String> params = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        sortBy = sortBy.replace("patientId", "patient");
        sortBy = sortBy.replace("category", "bloodPressure");
        params.put("sortBy", sortBy);
        params.put("limit", limit);
        params.put("startDateTime", startDateTime);
        params.put("endDateTime", endDateTime);

        return service.getBloodPressuresWithParams(params);
    }

    @GetMapping("{patientId}/bloodPressures")
    public List<BloodPressure> getBloodPressures(@PathVariable int patientId,
            @RequestParam(name = "sortBy", defaultValue = "-dateTime,id", required = false) String sortBy,
            @RequestParam(name = "limit", defaultValue = "", required = false) String limit,
            @RequestParam(name = "startDateTime", defaultValue = "", required = false) String startDateTime,
            @RequestParam(name = "endDateTime", defaultValue = "", required = false) String endDateTime) {

        Map<String, String> params = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        params.put("patientId", String.valueOf(patientId));
        params.put("sortBy", sortBy.replace("patientId", "patient"));
        params.put("limit", limit);
        params.put("startDateTime", startDateTime);
        params.put("endDateTime", endDateTime);

        return service.getBloodPressuresWithParams(params);
    }

    /*
     * 
     * * @GetMapping("bloodPressures/{id
     * * public BloodPressure getBloodPressureById(@PathVariable int id
     * eturn service.getBloodPressureById(id);
     * 
     * 
     * * @PostMapping("bloodPressure
     * * public BloodPressure addBloodPressure(@RequestBody BloodPress
     * re
     * loodPressure) {
     * 
     * 
     * 
     * *
     * * @PostMapping("/api/bloodPressures")
     * 
     * ublic List<BloodPressure> addBloodPressures(@Reque
     * 
     * 
     * 
     * *
     * *
     * 
     * PutMapping("bloodPressures/{id}")
     * 
     * 
     * * return service.updateBloodPressure
     * * }
     * 
     * 
     * 
     * service.deleteBloodPressure(id);
     * }
     * 
     */

}