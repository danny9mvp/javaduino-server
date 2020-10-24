/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dannymvp.javaduinoserver.arduino;

import com.dannymvp.javaduinoserver.model.TemperatureHumidityMeasure;
import com.dannymvp.javaduinoserver.tcpip.IMessageSender;
import com.dannymvp.javaduinoserver.tcpip.TcpIpServer;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.DateFormatter;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author ASUS
 */
public class MainThread {
    private TcpIpServer tcpIpServer;
    private SerialPortEventListener listener;        
    private IMessageSender iMessageSender;    
    public void runAndruino(int port, String readingRate, JTextArea area)
    {                        
        try {
            tcpIpServer = new TcpIpServer();
            tcpIpServer.startTcpIpServer(port);
        } catch ( IOException ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error en la inicialización del Socket Servidor", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        PanamaHitek_Arduino pha = new PanamaHitek_Arduino();        
        listener = new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent spe) {
                try {
                    if(pha.isMessageAvailable()){
                        String strMeasure = pha.printMessage();
                        pha.sendData(readingRate);
                        if(strMeasure.isEmpty() == false){                            
                            String[] strMeasureArray = strMeasure.split("\\|");
                            iMessageSender = new IMessageSender();
                            iMessageSender.setDataOutputStream(tcpIpServer.getOut());
                            String temperature = (strMeasureArray[0]);
                            String humidity = (strMeasureArray[1]);
                            String measureTimeStamp = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(LocalDateTime.now());
                            TemperatureHumidityMeasure temperatureHumidityMeasure = new TemperatureHumidityMeasure(temperature, humidity, measureTimeStamp);
                            area.append(temperatureHumidityMeasure.toString()+"\n");
                            iMessageSender.sendTemperatureAsCelsiusDegrees(temperatureHumidityMeasure);
                            System.out.println(temperatureHumidityMeasure.toString());
                        }
                        pha.flushSerialPort();
                    }
                } catch (SerialPortException | ArduinoException | IOException ex) {                    
                    Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        try {                      
            pha.arduinoRXTX("COM5", 9600, listener);            
        } catch (ArduinoException ex) {
            Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
