package com.example.web_controller.controller;

import com.example.web_controller.service.GamepadListenerService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSoketController {

    GamepadListenerService gamepadListenerService;

    @MessageMapping("/sendGamepad")
    public void getGamepad(){

    }
}
