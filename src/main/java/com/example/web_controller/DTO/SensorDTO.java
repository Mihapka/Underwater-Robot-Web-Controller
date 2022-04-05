package com.example.web_controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Component;

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

    public SensorDTO(Double pitch) {
        this.pitch = Precision.round(Math.random() * 100, 4);
    }

}
