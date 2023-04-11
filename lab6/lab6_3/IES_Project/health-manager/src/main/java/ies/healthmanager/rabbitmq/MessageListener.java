package ies.healthmanager.rabbitmq;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ies.healthmanager.websockets.MessageType;
import ies.healthmanager.websockets.NotificationMessage;
import ies.healthmanager.websockets.WebSocketsController;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ies.healthmanager.services.*;
import ies.healthmanager.entities.*;

@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_PATIENT_DATA)
public class MessageListener {

    @Autowired
    private HeartRatesService heartRatesService; 

    @Autowired
    private BloodPressuresService bloodPressuresService;

    @Autowired
    private BodyTemperaturesService bodyTemperaturesService;

    @Autowired
    private RespirationRatesService respirationRatesService;

    @Autowired
    private OxygenSaturationsService oxygenSaturationsService;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private PatientsService patientsService;


    @RabbitHandler
    public void processMessage(byte[] message) {
        String jsonString = new String(message, StandardCharsets.UTF_8); // {"patientId": 10, "oxygenSaturation": 0.95}
        System.out.println("Message received: " + jsonString);

        JSONObject jsonObject = new JSONObject(jsonString);
        int patientId;
        if (jsonObject.has("patientId"))
            patientId = jsonObject.getInt("patientId");
        else
        {
            System.out.println("[ERROR] [MessageListener.processMessage()] Received message without patientId.");
            return;
        }

        Patient patient = patientsService.getPatientById(patientId);
        if (patient == null)
            return;
        HealthState latestHealthState = patient.getLatestHealthState();

        LocalDateTime dateTime;
        if (jsonObject.has("dateTime"))
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime = LocalDateTime.parse((CharSequence) jsonObject.get("dateTime"), formatter);
        }
        else
        {
            System.out.println("[ERROR] [MessageListener.processMessage()] Received message without dateTime.");
            return;
        }

        if (jsonObject.has("heartRate"))
        {
            HeartRate heartRate = new HeartRate();
            heartRate.setPatient(patient);
            heartRate.setDateTime(dateTime);
            heartRate.setHeartRate(jsonObject.getInt("heartRate"));
            heartRatesService.saveHeartRate(heartRate);
        }

    
        if (jsonObject.has("bloodPressureSystolic") && jsonObject.has("bloodPressureDiastolic"))
        {
            BloodPressure bloodPressure = new BloodPressure();
            bloodPressure.setPatient(patient);
            bloodPressure.setDateTime(dateTime);
            bloodPressure.setBloodPressure(jsonObject.getInt("bloodPressureSystolic"), jsonObject.getInt("bloodPressureDiastolic"));
            bloodPressuresService.saveBloodPressure(bloodPressure);
        }

        if (jsonObject.has("bodyTemperature"))
        {
            BodyTemperature bodyTemperature = new BodyTemperature();
            bodyTemperature.setPatient(patient);
            bodyTemperature.setDateTime(dateTime);
            bodyTemperature.setBodyTemperature(jsonObject.getDouble("bodyTemperature"));
            bodyTemperaturesService.saveBodyTemperature(bodyTemperature);
        }

        if (jsonObject.has("respirationRate"))
        {
            RespirationRate respirationRate = new RespirationRate();
            respirationRate.setPatient(patient);
            respirationRate.setDateTime(dateTime);
            respirationRate.setRespirationRate(jsonObject.getInt("respirationRate"));
            respirationRatesService.saveRespirationRate(respirationRate);
        }

        if (jsonObject.has("oxygenSaturation"))
        {
            OxygenSaturation oxygenSaturation = new OxygenSaturation();
            oxygenSaturation.setPatient(patient);
            oxygenSaturation.setDateTime(dateTime);
            oxygenSaturation.setOxygenSaturation(jsonObject.getDouble("oxygenSaturation"));
            oxygenSaturationsService.saveOxygenSaturation(oxygenSaturation);
        }

        patient = patientsService.getPatientById(patientId);
        if (patient == null)
            return;
        HealthState newLatestHealthState = patient.getLatestHealthState();

        if (newLatestHealthState == null)
            return;

        //System.out.println("newLatestHealthState != null");

        if (latestHealthState == null)   
        {
            if (newLatestHealthState.getState() == HealthStateEnum.VERY_UNHEALTHY || newLatestHealthState.getState() == HealthStateEnum.UNHEALTHY)
                sendAlert(patient, dateTime);
            //System.out.println("latestHealthState = null");
        }
        else if (latestHealthState.getState() != HealthStateEnum.VERY_UNHEALTHY && latestHealthState.getState() != HealthStateEnum.UNHEALTHY)
        {
            if (newLatestHealthState.getState() == HealthStateEnum.VERY_UNHEALTHY || newLatestHealthState.getState() == HealthStateEnum.UNHEALTHY)
                sendAlert(patient, dateTime);
            //System.out.println("latestHealthState != null & latestHealthState was healthy");
        }
        
    }

    public void sendAlert(Patient patient, LocalDateTime dateTime)
    {
        System.out.println("!!! SENDING ALERT !!!");
        this.template.convertAndSend("/topic/alerts", NotificationMessage.builder()
                .type(MessageType.NOTIFICATION)
                .name(patient.getFullName())
                .id(patient.getId())
                .time(dateTime.format(DateTimeFormatter.ISO_DATE_TIME))
                .state("critical")
                .build()
        );
    }

}

