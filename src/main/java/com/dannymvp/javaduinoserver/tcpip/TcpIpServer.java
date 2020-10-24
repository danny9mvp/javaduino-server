/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dannymvp.javaduinoserver.tcpip;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class TcpIpServer{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;        
    
    public void startTcpIpServer(int port) throws IOException{
        ServerSocket ss = new ServerSocket(port);  
        JOptionPane.showMessageDialog(null, "Se ha inicializado correctamente el servidor de sockets, esperando la conexión de un cliente.", "Información", JOptionPane.INFORMATION_MESSAGE);
        Socket cs = ss.accept();
        JOptionPane.showMessageDialog(null, "Se ha conectado el cliente "+((InetSocketAddress)cs.getRemoteSocketAddress()).getAddress().getHostAddress()+".", "Información", JOptionPane.INFORMATION_MESSAGE);
        this.setClientSocket(cs);
        DataInputStream dis = new DataInputStream(this.getClientSocket().getInputStream());
        DataOutputStream dos = new DataOutputStream(this.getClientSocket().getOutputStream());
        this.setIn(dis);
        this.setOut(dos);
    }
    
    public ServerSocket getServerSocket()throws IOException{
        return this.serverSocket;
    }
    
    public Socket getClientSocket()throws IOException{
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket)throws IOException{
        this.clientSocket = clientSocket;
    }

    public DataInputStream getIn()throws IOException{
        return in;
    }

    public void setIn(DataInputStream in)throws IOException{
        this.in = in;
    }

    public DataOutputStream getOut() throws IOException{
        return out;
    }

    public void setOut(DataOutputStream out) throws IOException{
        this.out = out;
    }
}
