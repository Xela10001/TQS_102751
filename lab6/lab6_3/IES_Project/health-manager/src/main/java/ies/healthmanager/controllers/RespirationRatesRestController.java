package ies.healthmanager.controllers;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ies.healthmanager.entities.RespirationRate;
import ies.healthmanager.services.RespirationRatesService;

@RestController
@RequestMapping("/api/v1/patients")
public class RespirationRatesRestController {

    @Autowired
    private RespirationRatesService service;

    @GetMapping("respirationRates")
    public List<RespirationRate> getRespirationRates(
            @RequestParam(name = "sortBy", defaultValue = "-dateTime,id", required = false) String sortBy,
            @RequestParam(name = "limit", defaultValue = "", required = false) String limit,
            @RequestParam(name = "startDateTime", defaultValue = "", required = false) String startDateTime,
            @RequestParam(name = "endDateTime", defaultValue = "", required = false) String endDateTime) {

        Map<String, String> params = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        params.put("sortBy", sortBy.replace("patientId", "patient"));
        params.put("limit", limit);
        params.put("startDateTime", startDateTime);
        params.put("endDateTime", endDateTime);

        return service.getRespirationRatesWithParams(params);
    }

    @GetMapping("{patientId}/respirationRates")
    public List<RespirationRate> getRespirationRates(@PathVariable int patientId,
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

        return service.getRespirationRatesWithParams(params);
    }

    /*
     * @PostMapping("respirationRates")
     * public RespirationRate addRespirationRate(@RequestBody RespirationRate
     * respirationRate) {
     * return service.saveRespirationRate(respirationRate);
     * }
     * 
     * @PostMapping("respirationRates")
     * public List<RespirationRate> addRespirationRates(@RequestBody
     * List<RespirationRate> respirationRates) {
     * return service.saveRespirationRates(respirationRates);
     * }
     * 
     * @PutMapping("respirationRates/{id}")
     * public RespirationRate updateRespirationRate(@RequestBody RespirationRate
     * respirationRate) {
     * return service.updateRespirationRate(respirationRate);
     * }
     * 
     * @DeleteMapping("respirationRates/{id}")
     * public void deleteRespirationRate(@PathVariable int id) {
     * service.deleteRespirationRate(id);
     * }
     * 
     */

}