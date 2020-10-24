/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dannymvp.javaduinoserver.tcpip;

import com.dannymvp.javaduinoserver.model.TemperatureHumidityMeasure;
import java.io.IOException;

/**
 *
 * @author ASUS
 */
public interface MessageSender {
    void sendTemperatureAsCelsiusDegrees(TemperatureHumidityMeasure temperatureHumidityMeasure) throws IOException;
    void sendTemperatureAsFahrenheitDegrees(TemperatureHumidityMeasure temperatureHumidityMeasure) throws IOException;
}
