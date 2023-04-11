package ies.healthmanagerprototype.services;

import org.springframework.stereotype.Service;
import ies.healthmanagerprototype.entities.*;
import org.springframework.core.io.ClassPathResource;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;
import java.time.LocalDateTime;
import java.io.File;
import java.nio.file.Files;

@Service
public class PatientsService {
    
    public static HashMap<Patient, HashMap<LocalDateTime, ArrayList<Double>>> storedValues = new HashMap<>();
    
    private void generateFakeValues(Patient patient) {
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
        HashMap<LocalDateTime, ArrayList<Double>> hashValues = new HashMap<>();
        
        for(int hours = 0; hours < 24; hours++) {
            hashValues.put(dateTime.plusHours(hours), createFakeValuesArray());
        }
        storedValues.put(patient, hashValues);
    }
    
    /*
    private void storePatientValues(Patient patient) {
        generateFakeValues(patient);
        
        ArrayList<Double> values = new ArrayList<>();
    
        values.add((double) patient.getHeartRate());
        values.add((double) patient.getRespirationRate());
        values.add(patient.getBodyTemperature());
        values.add((double) patient.getBloodPressure().getDiastolic());
        values.add((double) patient.getBloodPressure().getSystolic());
        values.add(patient.getOxygenSaturation());
    
        if (!storedValues.containsKey(patient)) {
            storedValues.put(patient, new HashMap<>());
            
        }
            storedValues.get(patient).put(LocalDateTime.now(), values);
    }
     */

    public List<Patient> getPatientsPrototype() {
        // return List<Patient> with patients in patients.json, fields not in it are
        // randomly generated, like bodyTemperature

        ArrayList<Patient> patients = new ArrayList<Patient>();

        JSONArray patientsJsonArray = null;
        try {
            File jsonFile = new ClassPathResource("static/patients.json").getFile();
            String patientsString = Files.readString(jsonFile.toPath());
            patientsJsonArray = new JSONArray(patientsString);
        } catch (Exception e) {
            e.printStackTrace();
            return patients;
        }

        for (int i = 0; i < patientsJsonArray.length(); i++) {
            JSONObject patientJson = patientsJsonArray.getJSONObject(i);
            Patient patient = new Patient();
            patient.setId(patientJson.getInt("id"));
            patient.setFirstName(patientJson.getString("firstName"));
            patient.setLastName(patientJson.getString("lastName"));
            patient.setAge(patientJson.getInt("age"));
            String gender = patientJson.getString("gender").toLowerCase();
            if (gender.equals("male"))
                patient.setGender(Gender.MALE);
            else if (gender.equals("female"))
                patient.setGender(Gender.FEMALE);
            else
                patient.setGender(Gender.OTHER);
            Random rand = new Random();
            HealthState healthState = HealthState.values()[rand.nextInt(HealthState.values().length)];
            patient.setHealthState(healthState);  
            patient.setHeartRate(rand.nextInt(60,101));
            patient.setRespirationRate(rand.nextInt(12,14));
            patient.setBodyTemperature(rand.nextDouble(36.5,36.8));
            patient.setBloodPressure(new BloodPressure(rand.nextInt(100,120),rand.nextInt(70,80)));
            double oxygenSaturation = rand.nextDouble(0.95,0.97);
            patient.setOxygenSaturation(oxygenSaturation);
            patients.add(patient);
        }

        return patients;

    }

    public Patient getPatientById(int id)
    {
        ArrayList<Patient> patients = new ArrayList<Patient>();

        JSONArray patientsJsonArray = null;
        try {
            File jsonFile = new ClassPathResource("static/patients.json").getFile();
            String patientsString = Files.readString(jsonFile.toPath());
            patientsJsonArray = new JSONArray(patientsString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        for (int i = 0; i < patientsJsonArray.length(); i++) 
            if (patientsJsonArray.getJSONObject(i).getInt("id") == id)
            {
                JSONObject patientJson = patientsJsonArray.getJSONObject(i);
                Patient patient = new Patient();
                patient.setId(patientJson.getInt("id"));
                patient.setFirstName(patientJson.getString("firstName"));
                patient.setLastName(patientJson.getString("lastName"));
                patient.setAge(patientJson.getInt("age"));
                String gender = patientJson.getString("gender").toLowerCase();
                if (gender.equals("male"))
                    patient.setGender(Gender.MALE);
                else if (gender.equals("female"))
                    patient.setGender(Gender.FEMALE);
                else
                    patient.setGender(Gender.OTHER);
                
                Random rand = new Random();
                HealthState healthState = HealthState.values()[rand.nextInt(HealthState.values().length)];
                patient.setHealthState(healthState);                
                patient.setHeartRate(rand.nextInt(60,101));
                patient.setRespirationRate(rand.nextInt(12,14));
                patient.setBodyTemperature(rand.nextDouble(36.5,36.8));
                patient.setBloodPressure(new BloodPressure(rand.nextInt(100,120),rand.nextInt(70,80)));
                double oxygenSaturation = rand.nextDouble(0.95,0.97);
                patient.setOxygenSaturation(oxygenSaturation);
    
                generateFakeValues(patient);
                
                return patient;

            }

        return null;

    }
    
    private static ArrayList<Double> createFakeValuesArray() {
        ArrayList<Double> values = new ArrayList<>();
        Random rand = new Random();
        
        // Heart Rate
        values.add((double) rand.nextInt(60,101));
        // Respiration Rate
        values.add((double) rand.nextInt(12,14));
        // Body Temperature
        values.add(rand.nextDouble(36.5,36.8));
        // Blood Pressure
        values.add((double) rand.nextInt(100,120)); // Diastolic
        values.add((double) rand.nextInt(70,80)); // Systolic
        // Oxygen Saturation
        values.add(rand.nextDouble(0.95,0.97));
        
        return values;
    }


}