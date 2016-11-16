/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robot.client;

import com.robot.swing.ClientGUI;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Nicola
 */
public final class Client {

    private static Socket socket;

    private final String IP;

    private final int PORT;

    private final File TOKEN_FILE;

    private static ClientGUI clientGUI;

    private String raw;

    private String line;

    private boolean ended;

    private PrintStream output;

    private BufferedReader input;

    public Client(String IP, int PORT) {
        ended = false;
        this.IP = IP;
        this.PORT = PORT;
        TOKEN_FILE = new File("token.dat");
        if (!TOKEN_FILE.exists()) {
            try {
                Files.createFile(Paths.get("token.dat"));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "File token.dat corrupted or non existent", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        clientGUI = new ClientGUI(this);
        try {
            socket = new Socket(IP, PORT);
            System.out.println("Client avviato");
            output = new PrintStream(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            handleClosing();
            talk();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Connection with " + IP + " not possible", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void handleClosing() {
        clientGUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                output.println("END" + (char) 4);
                System.exit(0);

            }
        });
    }

    public static void main(String[] args) {
        String beginning = "";
        do {
            beginning = JOptionPane.showInputDialog("Write the IP address you want to connect with and the port in the form \"IP:PORT\"", "localhost:9090");
        } while (!beginning.contains(":"));
        String[] ipAndPort = beginning.split(":");
        String[] ip;
        if ("localhost".equals(ipAndPort[0])) {
            ip = new String[]{"localhost"};
        } else {
            ip = ipAndPort[0].split(".");
            if (ip.length != 0) {
                for (String ip1 : ip) {
                    System.out.println(ip1);
                    try {
                        Byte.parseByte(ip1);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Wrong IP address", "Wrong format", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Wrong IP address", "Wrong format", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        try {
            Integer.parseInt(ipAndPort[1]);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Wrong port", "Wrong format", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        Client client = new Client(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
    }
//problema

    public static void display(String str, boolean rightToLeft) {
        StyledDocument doc = clientGUI.getDocument();
        if (rightToLeft) {
            str = "[YOU]: " + str;
            SimpleAttributeSet answer = new SimpleAttributeSet();
            StyleConstants.setAlignment(answer, StyleConstants.ALIGN_RIGHT);
            StyleConstants.setForeground(answer, Color.red);
            doc.setParagraphAttributes(doc.getEndPosition().getOffset(), doc.getLength()-1, answer, false);
            doc.setCharacterAttributes(doc.getEndPosition().getOffset(), doc.getLength()-1, answer, false);
        } else {
            str = "[PANDORA]: " + str;
            SimpleAttributeSet question = new SimpleAttributeSet();
            StyleConstants.setBold(question, true);
            StyleConstants.setForeground(question, Color.BLACK);
            StyleConstants.setAlignment(question, StyleConstants.ALIGN_LEFT);
            doc.setCharacterAttributes(doc.getEndPosition().getOffset(), doc.getLength()-1, question, false);
            doc.setParagraphAttributes(doc.getEndPosition().getOffset(), doc.getLength()-1, question, false);
        }
        try {
            doc.insertString(doc.getLength(), "\n" + str + "\n", null);
        } catch (BadLocationException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendToken(BufferedReader input, PrintStream output) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(TOKEN_FILE));
            String fileToken = br.readLine();
            if (fileToken == null) { // se il file è vuoto leggi il token ed invia una stringa
                output.println(0);
                try {
                    //leggi il token e memorizzalo
                    String token = input.readLine();
                    new PrintStream(TOKEN_FILE).println(token); // scrivilo

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error occurred while sending token", "Error", JOptionPane.ERROR_MESSAGE);
                    socket.close();
                }
            } else {
                output.println(fileToken);
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error occurred while sending token", "Error", JOptionPane.ERROR_MESSAGE);
            try {
                socket.close();
            } catch (IOException ex1) {

            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error occurred while sending token", "Error", JOptionPane.ERROR_MESSAGE);
            try {
                socket.close();
            } catch (IOException ex1) {

            }
        }
    }

    private void talk() {

        // solo da riga di comando
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        sendToken(input, output);

        line = " ";
        while (!ended) {
            try {
                raw = input.readLine();
            } catch (IOException ex) {

            }
            line = interpreteCode(raw);
            System.out.println(line);
            display(line, false);
            if (raw.contains("" + (char) 4)) {
                ended = true;
            }

        }
        try {
            socket.close();
        } catch (IOException ex) {

        }
        clientGUI.getSendButton().setEnabled(false);
        System.out.println("Uscito");
    }

    public void sendText() {
        if (raw.contains("" + (char) 4) || !clientGUI.isDisplayable()) {
            ended = true;
        } else if (!line.contains("" + (char) 0) && !line.contains("" + (char) 4)) {
            String in = clientGUI.getInputField().getText();
            output.println(in);
            display(in, true);
            clientGUI.getInputField().setText("");

        }
    }

    public static synchronized String interpreteCode(String message) {
        if (message == null) {
            try {
                socket.close();
            } catch (IOException ex) {
            }
            display("*******************************DISCONNECTED********************************", false);
        }
        message = message.replaceAll("" + (char) 3, "\n");
        return message.replaceAll("" + (char) 4, "");
    }
}
