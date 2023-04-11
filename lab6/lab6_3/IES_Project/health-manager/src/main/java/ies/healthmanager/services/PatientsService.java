package ies.healthmanager.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.healthmanager.entities.Patient;
import ies.healthmanager.repositories.patients.PatientsRepository;

@Service
public class PatientsService {

    @Autowired
    private PatientsRepository repository;

    public List<Patient> getPatients() {
        List<Patient> patients =  repository.findAll();
        System.out.println("Got " + patients.size() + " patients");
        return patients;
    }

    public List<Patient> getPatientsWithParams(Map<String, String> params) {
        List<Patient> patients = repository.getPatientsCustomQuery(params);
        int size = 0;
        if (patients != null)
            size = patients.size();
        System.out.println("Got " + size + " patients");
        return patients;
    }

    public Patient getPatientById(int id) {
        Patient p = repository.findById(id).orElse(null);
        if (p != null)
            System.out.println("Got patient with id=" + p.getId());
        else
            System.out.println("Patient with id=" + id + " does not exist");
        return p;
    }

    public Patient savePatient(Patient patient) {
        //patient.getHealthStates();
        System.out.println(patient.getHealthStates());
        Patient patientAfterSave = repository.save(patient);
        System.out.println("Saved patient id=" + patientAfterSave.getId() + " (" + patientAfterSave.getFullName() + ")");
        return patientAfterSave;
    }

    /*
    public List<Patient> savePatients(List<Patient> patients) {
        List<Patient> patientsAfterSave = repository.saveAll(patients);
        System.out.println("Saved " + patientsAfterSave.size() + " patients");
        return patientsAfterSave;
    }
    */

    public Patient updatePatient(Patient patient) {
        Patient existingPatient = repository.findById(patient.getId()).orElse(null);
        if (existingPatient == null)
        {
            System.out.println("Can't update patient id=" + patient.getId() + " because it does not exist in database");
            return null;
        }
        existingPatient = Patient.CopySkippingNulls(patient, existingPatient);
        Patient p = repository.save(existingPatient);
        System.out.println("Updated patient id=" + p.getId());
        return p;
    }  

    public void deletePatient(int id) {
        repository.deleteById(id);
        System.out.println("Deleted patient id=" + id);
    }



}