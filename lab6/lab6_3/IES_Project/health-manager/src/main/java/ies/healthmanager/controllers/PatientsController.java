package ies.healthmanager.controllers;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ies.healthmanager.entities.HealthState;
import ies.healthmanager.entities.HealthStateEnum;
import ies.healthmanager.entities.Patient;
import ies.healthmanager.services.PatientsService;

@Controller
public class PatientsController {

    @Autowired
    private PatientsService service;

    @GetMapping("/patients")
    public String getPatients(Model model) {
        List<Patient> patients = service.getPatients();

        // Comparator of patients by their latest health state, then by datetime
        Comparator<Patient> comparator = (a, b) -> {
            HealthState aHealthState = a.getLatestHealthState();
            HealthState bHealthState = b.getLatestHealthState();
            if (aHealthState == null) {
                if (bHealthState == null)
                    return 0;
                return 1;
            }
            if (bHealthState == null)
                return -1;
            HealthStateEnum healthStateEnumValues[] = HealthStateEnum.values();
            List<HealthStateEnum> healthStateEnumList = Arrays.asList(healthStateEnumValues);
            int diff = healthStateEnumList.indexOf(bHealthState.getState())
                    - healthStateEnumList.indexOf(aHealthState.getState());
            if (diff != 0)
                return diff;
            // At this point, both have same health state enum
            // So compare them by datetime
            diff =  (int)Duration.between(aHealthState.getDateTime(), bHealthState.getDateTime()).toSeconds();
            if (diff != 0)
                return diff;
            // At this point, same health state enum and datetime
            // So compare by patientId 
            return aHealthState.getPatient().getId() - bHealthState.getPatient().getId();

        };

        Collections.sort(patients, comparator);
        model.addAttribute("patients", patients);
        return "patients";
    }

    @GetMapping("/patients/{id}")
    public String getPatientById(@PathVariable("id") int id, Model model) {
        Patient patient = service.getPatientById(id);
        model.addAttribute("patient", patient);
        return "patient";
    }

}