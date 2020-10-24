/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dannymvp.javaduinoserver.tcpip;

import com.dannymvp.javaduinoserver.model.TemperatureHumidityMeasure;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author ASUS
 */
public class IMessageSender implements MessageSender{
    
    private DataOutputStream dataOutputStream;
    
    @Override
    public void sendTemperatureAsCelsiusDegrees(TemperatureHumidityMeasure temperatureHumidityMeasure) throws IOException{
        String tempHumMeasure = temperatureHumidityMeasure.toString();
        this.dataOutputStream.writeUTF(tempHumMeasure);               
    }

    @Override
    public void sendTemperatureAsFahrenheitDegrees(TemperatureHumidityMeasure temperatureHumidityMeasure) throws IOException{
        String temperatureCelsius = temperatureHumidityMeasure.getTemperature();
        float temperatureAsFahrenheit = (Float.parseFloat(temperatureCelsius)*(9/5) + 32);
        temperatureHumidityMeasure.setTemperature(String.valueOf(temperatureAsFahrenheit));
        String tempHumMeasure = temperatureHumidityMeasure.toString();
        this.dataOutputStream.writeUTF(tempHumMeasure);        
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }
    
    
    
}
