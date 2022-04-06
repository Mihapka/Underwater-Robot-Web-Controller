package com.example.web_controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GamepadDTO {

    private float leftStickX;
    private float leftStickY;
    private float rightStickX;
    private float rightStickY;

}
