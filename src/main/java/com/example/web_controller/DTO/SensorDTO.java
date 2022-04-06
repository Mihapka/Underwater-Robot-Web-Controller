package com.example.web_controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDTO {

    private double pitch;
    private double roll;
    private double heading;
    private double depth;
    private double temperature;
    private double cpuTemp;
}
