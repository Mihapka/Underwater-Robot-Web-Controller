package com.example.web_controller.service;

import com.example.web_controller.DTO.SensorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.underwater.robot.common.message.SensorsValuesMsg;
import org.underwater.robot.common.message.Topics;
import org.underwater.robot.connection.pubsub.Subscriber;
import org.zeromq.ZContext;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SensorListenerService {

    static private Logger logger = LogManager.getLogger(SensorListenerService.class);
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private ObjectMapper objectMapper = new ObjectMapper();
    private SensorDTO sensorDTO;
    private Subscriber subscriber;

    @Autowired
    private ZContext zContext;
    @Autowired
    private SimpMessagingTemplate template;


    @PostConstruct
    public void init() {

        subscriber = new Subscriber(Topics.SENSORS_TOPIC, "tcp://localhost:6001", zContext);
        logger.info("Started listening topic '{}', on address: {}", Topics.SENSORS_TOPIC, "tcp://localhost:6001");
        subscriber.setOnReceive(this::processMsg);
        executor.submit(subscriber);
    }

    private void processMsg(String msg) {

        try {
            SensorsValuesMsg sensorsValuesMsg = objectMapper.readValue(msg, SensorsValuesMsg.class);
            sensorDTO = new SensorDTO(
                    sensorsValuesMsg.getPitch(),
                    sensorsValuesMsg.getRoll(),
                    sensorsValuesMsg.getHeading(),
                    sensorsValuesMsg.getDepth(),
                    sensorsValuesMsg.getTemperature(),
                    sensorsValuesMsg.getCpuTemp());

            streamSensors(sensorDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void streamSensors(SensorDTO sensorDTO) {

        this.template.convertAndSend("/topic/sensors", sensorDTO);
    }
}