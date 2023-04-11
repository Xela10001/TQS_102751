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

import ies.healthmanager.entities.BodyTemperature;
import ies.healthmanager.services.BodyTemperaturesService;

@RestController
@RequestMapping("/api/v1/patients")
public class BodyTemperaturesRestController {

    @Autowired
    private BodyTemperaturesService service;

    @GetMapping("bodyTemperatures")
    public List<BodyTemperature> getBodyTemperatures(
            @RequestParam(name = "sortBy", defaultValue = "-dateTime", required = false) String sortBy,
            @RequestParam(name = "limit", defaultValue = "", required = false) String limit,
            @RequestParam(name = "startDateTime", defaultValue = "", required = false) String startDateTime,
            @RequestParam(name = "endDateTime", defaultValue = "", required = false) String endDateTime) {

        Map<String, String> params = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        params.put("sortBy", sortBy.replace("patientId", "patient"));
        params.put("limit", limit);
        params.put("startDateTime", startDateTime);
        params.put("endDateTime", endDateTime);

        return service.getBodyTemperaturesWithParams(params);
    }

    @GetMapping("{patientId}/bodyTemperatures")
    public List<BodyTemperature> getBodyTemperatures(@PathVariable int patientId,
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

        return service.getBodyTemperaturesWithParams(params);
    }

    /*
     * 
     * @GetMapping("bodyTemperatures/{id}")
     * public BodyTemperature getBodyTemperatureById(@PathVariable int id) {
     * return service.getBodyTemperatureById(id);
     * }
     * 
     * @PostMapping("bodyTemperatures")
     * public BodyTemperature addBodyTemperature(@RequestBody BodyTemperature
     * bodyTemperature) {
     * return service.saveBodyTemperature(bodyTemperature);
     * }
     * 
     * @PostMapping("bodyTemperatures")
     * public List<BodyTemperature> addBodyTemperatures(@RequestBody
     * List<BodyTemperature> bodyTemperatures) {
     * return service.saveBodyTemperatures(bodyTemperatures);
     * }
     * 
     * @PutMapping("bodyTemperatures/{id}")
     * public BodyTemperature updateBodyTemperature(@RequestBody BodyTemperature
     * bodyTemperature) {
     * return service.updateBodyTemperature(bodyTemperature);
     * }
     * 
     * @DeleteMapping("bodyTemperatures/{id}")
     * public void deleteBodyTemperature(@PathVariable int id) {
     * service.deleteBodyTemperature(id);
     * }
     * 
     */

}