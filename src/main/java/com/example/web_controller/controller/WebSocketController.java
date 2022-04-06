package com.example.web_controller.controller;

import com.example.web_controller.DTO.GamepadDTO;
import com.example.web_controller.service.GamepadListenerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    static private Logger logger = LogManager.getLogger(WebSocketController.class);

    private GamepadListenerService gamepadListenerService;

    @MessageMapping("/sendAxes")
    public void getGamepad(@Payload GamepadDTO gamepadDTO){
        gamepadListenerService.processMsg(gamepadDTO);
    }
}
