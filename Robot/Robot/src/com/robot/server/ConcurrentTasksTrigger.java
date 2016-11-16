package com.robot.server;

import com.robot.protocol.Protocol;
import com.utils.Robot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import javax.swing.JOptionPane;

/**
 * Created by Nicola on 24/10/2016.
 */
public class ConcurrentTasksTrigger implements Runnable {

    private static Socket socket;

    public OutputStream outputStream;

    public InputStream inputStream;
    
    private static Robot robot;

    public ConcurrentTasksTrigger(Socket socket, OutputStream os, InputStream is) {
        robot = new Robot();
        this.socket = socket;
        outputStream = os;
        inputStream = is;

    }

    public static synchronized String codeMessage(String message) {
        return message.replaceAll("\n", "" + (char) 3);
    }

    public static synchronized long getToken() {
        return System.currentTimeMillis();
    }

    public synchronized long readToken(PrintStream ps, BufferedReader br) {
        long token = Long.MIN_VALUE;
        try {
            token = Long.parseLong(br.readLine());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error occurred while reading token", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (token == 0) {
            token = getToken();
            ps.println(Long.toString(token));
        }
        if (token < 0) {
            throw new IllegalArgumentException("Must be a non-negative integer");
        }
        return token;
    }

    @Override
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        PrintStream ps = new PrintStream(outputStream, true);
        robot.setId(readToken(ps, br));
        Server.log("Got " + socket.getInetAddress() + ":" + socket.getPort() + " token");
        robot.registerUser(robot.getId(), socket.getInetAddress().getHostAddress(), socket.getPort()); // registra l'user
        Protocol protocol = new Protocol(robot);
        while (!robot.isEnded()) {
            String message = protocol.sendMessage();
            if (protocol.isAnswerable()) {
                ps.println(codeMessage(message));
                try {
                    String receive=protocol.interpreteMessage(br.readLine());
                    System.out.println("RISPOSTA: " + receive);
                    if(receive.contains(""+(char)4)){
                        robot.end();
                    }
                } catch (SocketTimeoutException ex) {
                    try {
                        Server.log("Connection with: " + socket.getInetAddress() + " ended.");
                        robot.end();
                        socket.close();
                    } catch (IOException ex1) {
                        
                    }
                } catch (IOException ex) {
                   
                }

            } else {
                message = codeMessage(message) + (char) 0;
                System.out.println(message);
                ps.println(message);
            }
        }
        try {
            socket.close();
        } catch (IOException ex) {

        }
        Server.log("Connection with: " + socket.getInetAddress() + " ended.");
    }

    public static void abort() {
        try {
            robot.end();
            socket.close();
        } catch (IOException ex) {
            
        }
    }

}
