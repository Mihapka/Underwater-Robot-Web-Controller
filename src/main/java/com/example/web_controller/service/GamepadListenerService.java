package com.example.web_controller.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.underwater.robot.common.message.Topics;
import org.underwater.robot.connection.pubsub.Publisher;
import org.underwater.robot.connection.pubsub.Subscriber;
import org.zeromq.ZContext;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GamepadListenerService {

    static private Logger logger = LogManager.getLogger(SensorListenerService.class);
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private Publisher publisher;

    @Autowired
    private ZContext zContext;

    @PostConstruct
    public void init() {

//        subscriber = new Subscriber(Topics.SENSORS_TOPIC, "tcp://localhost:6001", zContext);
//        logger.info("Started listening topic '{}', on address: {}", "tcp://localhost:6001");
//        subscriber.setOnReceive(this::processMsg);
//        executor.submit(subscriber);


    }

}
