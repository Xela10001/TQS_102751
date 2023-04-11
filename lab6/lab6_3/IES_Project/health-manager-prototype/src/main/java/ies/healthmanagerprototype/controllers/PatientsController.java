package ies.healthmanagerprototype.controllers;

import ies.healthmanagerprototype.services.PatientsService;
import ies.healthmanagerprototype.entities.Patient;
import ies.healthmanagerprototype.entities.HealthStateSorter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Collections;

@Controller
public class PatientsController {

    @Autowired
    private PatientsService service;

    @GetMapping("/patients")
    public String getPatients(Model model) {
        List<Patient> patients = service.getPatientsPrototype();
        Collections.sort(patients, new HealthStateSorter());
        model.addAttribute("patients", patients);
        return "patients";
    }

    @GetMapping("/patients/{id}")
    public String getPatientById(@PathVariable("id") int id, Model model) {
        Patient patient = service.getPatientById(id);
        model.addAttribute("patient", patient);
        model.addAttribute("patientHeartbeats", PatientsService.storedValues.get(patient));
        return "patient";
    }

}