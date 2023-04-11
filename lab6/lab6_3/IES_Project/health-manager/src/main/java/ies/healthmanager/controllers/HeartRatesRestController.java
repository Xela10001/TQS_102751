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

import ies.healthmanager.entities.HeartRate;
import ies.healthmanager.services.HeartRatesService;

@RestController
@RequestMapping("/api/v1/patients")
public class HeartRatesRestController {

    @Autowired
    private HeartRatesService service;

    @GetMapping("heartRates")
    public List<HeartRate> getHeartRates(
            @RequestParam(name = "sortBy", defaultValue = "-dateTime", required = false) String sortBy,
            @RequestParam(name = "limit", defaultValue = "", required = false) String limit,
            @RequestParam(name = "startDateTime", defaultValue = "", required = false) String startDateTime,
            @RequestParam(name = "endDateTime", defaultValue = "", required = false) String endDateTime) {

        Map<String, String> params = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        params.put("sortBy", sortBy.replace("patientId", "patient"));
        params.put("limit", limit);
        params.put("startDateTime", startDateTime);
        params.put("endDateTime", endDateTime);

        return service.getHeartRatesWithParams(params);
    }

    @GetMapping("{patientId}/heartRates")
    public List<HeartRate> getHeartRates(@PathVariable int patientId,
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

        return service.getHeartRatesWithParams(params);
    }

    /*
     * 
     * @GetMapping("heartRates/{id}")
     * public HeartRate getHeartRateById(@PathVariable int id) {
     * return service.getHeartRateById(id);
     * }
     * 
     * @PostMapping("heartRates")
     * public HeartRate addHeartRate(@RequestBody HeartRate heartRate) {
     * return service.saveHeartRate(heartRate);
     * }
     * 
     * @PostMapping("heartRates")
     * public List<HeartRate> addHeartRates(@RequestBody List<HeartRate> heartRates)
     * {
     * return service.saveHeartRates(heartRates);
     * }
     * 
     * @PutMapping("heartRates/{id}")
     * public HeartRate updateHeartRate(@RequestBody HeartRate heartRate) {
     * return service.updateHeartRate(heartRate);
     * }
     * 
     * @DeleteMapping("heartRates/{id}")
     * public void deleteHeartRate(@PathVariable int id) {
     * service.deleteHeartRate(id);
     * }
     * 
     */

}