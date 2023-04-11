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

import ies.healthmanager.entities.OxygenSaturation;
import ies.healthmanager.services.OxygenSaturationsService;

@RestController
@RequestMapping("/api/v1/patients")
public class OxygenSaturationsRestController {

    @Autowired
    private OxygenSaturationsService service;

    @GetMapping("oxygenSaturations")
    public List<OxygenSaturation> getOxygenSaturations(
            @RequestParam(name = "sortBy", defaultValue = "-dateTime", required = false) String sortBy,
            @RequestParam(name = "limit", defaultValue = "", required = false) String limit,
            @RequestParam(name = "startDateTime", defaultValue = "", required = false) String startDateTime,
            @RequestParam(name = "endDateTime", defaultValue = "", required = false) String endDateTime) {

        Map<String, String> params = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        params.put("sortBy", sortBy.replace("patientId", "patient"));
        params.put("limit", limit);
        params.put("startDateTime", startDateTime);
        params.put("endDateTime", endDateTime);

        return service.getOxygenSaturationsWithParams(params);
    }

    @GetMapping("{patientId}/oxygenSaturations")
    public List<OxygenSaturation> getOxygenSaturations(@PathVariable int patientId,
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

        return service.getOxygenSaturationsWithParams(params);
    }

    /*
     * 
     * @GetMapping("oxygenSaturations/{id}")
     * public OxygenSaturation getOxygenSaturationById(@PathVariable int id) {
     * return service.getOxygenSaturationById(id);
     * }
     * 
     * @PostMapping("oxygenSaturations")
     * public OxygenSaturation addOxygenSaturation(@RequestBody OxygenSaturation
     * oxygenSaturation) {
     * return service.saveOxygenSaturation(oxygenSaturation);
     * }
     * 
     * 
     * @PostMapping("oxygenSaturations")
     * public List<OxygenSaturation> addOxygenSaturations(@RequestBody
     * List<OxygenSaturation> oxygenSaturations) {
     * return service.saveOxygenSaturations(oxygenSaturations);
     * }
     * 
     * 
     * @PutMapping("oxygenSaturations/{id}")
     * public OxygenSaturation updateOxygenSaturation(@RequestBody OxygenSaturation
     * oxygenSaturation) {
     * return service.updateOxygenSaturation(oxygenSaturation);
     * }
     * 
     * @DeleteMapping("oxygenSaturations/{id}")
     * public void deleteOxygenSaturation(@PathVariable int id) {
     * service.deleteOxygenSaturation(id);
     * }
     * 
     */

}