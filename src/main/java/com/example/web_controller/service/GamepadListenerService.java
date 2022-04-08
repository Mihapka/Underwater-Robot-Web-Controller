package com.example.web_controller.service;

import com.example.web_controller.DTO.GamepadDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.underwater.robot.common.message.ControlMsg;
import org.underwater.robot.common.message.Topics;
import org.underwater.robot.connection.pubsub.Publisher;
import org.zeromq.ZContext;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class GamepadListenerService {

    static private Logger logger = LogManager.getLogger(GamepadListenerService.class);
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private Publisher publisher;
    private GamepadDTO gamepadDTO;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ZContext zContext;

    @PostConstruct
    public void init() {

        publisher = new Publisher("tcp://localhost:6000", zContext);
        logger.info("Started publish on address: {}", "tcp://localhost:6000");

    }

    public void processMsg(GamepadDTO gamepadDTO) {
        ControlMsg controlMsg = new ControlMsg(
                gamepadDTO.getRightStickX(),
                gamepadDTO.getRightStickY(),
                gamepadDTO.getLeftStickY(), 0 );
        String msg = null;
        try {
            msg = objectMapper.writeValueAsString(controlMsg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        publisher.publish(Topics.CONTROL, msg);
    }
}
