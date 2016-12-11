package chat;

import database.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

/**
 * UDPServer implementa un applicazione progettata per gli utenti che vogliono
 * chattare con un gruppo di persone potendo inviare e scaricare immagini.
 *
 * @author Marco Tasca
 * @version 1.0
 * @since 2016-12-11
 */
final class UDPServer extends Thread {

    DatagramSocket serverSocket;
    DatagramPacket receivePacket, sendPacket;

    String sentence;
    byte[] receiveData, sendData;
    InetAddress IPAddress;
    int portPacket;
    int PORT = 9876;
    String[] data;
    boolean image;

    JDBC DATABASE;
    
    /**
     * Main del programma.
     * @param args 
     */
    public static void main(String args[]) {
        UDPServer udpServer = new UDPServer();
    }
    
    /**
     * Costruttore della classe UDPServer, inizializzazione di variabili e della
     * connessione UDP Server-Client.
     */
    private UDPServer() {
        DATABASE = new JDBC();
        try {
            serverSocket = new DatagramSocket(PORT);
            start();
        } catch (SocketException ex) {
            alert("Impossibile connettersi alla porta [" + PORT + "]", true);
        }

        receivePacket = null;
        receiveData = new byte[65400];
        sendData = new byte[65400];
        image = false;
    }
    
    /**
     * Cuore della classe, pulisce i contenitori dait, riceve messaggi e li
     * invia.
     */
    @Override
    public void run() {
        alert("SERVER RUNNING", false);
        while (true) {
            clear();
            receive();
            send();
        }
    }
    
    /**
     * Pulisce i contenitori dati.
     */
    public void clear() {
        Arrays.fill(receiveData, (byte) 0);
        Arrays.fill(sendData, (byte) 0);
    }
    
    /**
     * Riceve messaggi dal Server.
     */
    public void receive() {
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            serverSocket.receive(receivePacket);
        } catch (IOException ex) {
            alert("Impossibile ricevere il pacchetto", true);
        }
        sentence = Normalizer.normalize(new String(receivePacket.getData()), Normalizer.Form.NFD);
    }
    
    /**
     * Invia messaggio al Server.
     *
     * @param sentence messaggio da inviare.
     */
    public void send() {
        IPAddress = receivePacket.getAddress();
        portPacket = receivePacket.getPort();

        if (sentence.startsWith("ID")) {
            identify();
        } else if (sentence.startsWith("SIGNUP")) {
            addUser();
        } else if (sentence.startsWith("LOGIN")) {
            login();
        } else if (sentence.startsWith("MSG")) {
            sendMessage();
        } else {
            image = true;
            sendData = receivePacket.getData();
            broadcast();
            image = false;
        }
    }
    
    /**
     * Identifica un utente che vuole essere identificato.
     */
    public void identify() {
        sentence = sentence.trim() + " " + portPacket;
        directMessage(sentence, portPacket);
    }
    
    /**
     * Inserisce se possibile un utente al database.
     */
    public void addUser() {
        try {
            data = sentence.substring(("SIGNUP").length()).split(" ");
            if (!DATABASE.checkUsername(data[3])) {
                DATABASE.addUser(capitalize(data[1]), capitalize(data[2]), data[3], data[4], receivePacket.getPort());
                sentence = "USER added " + data[1] + " " + data[2];
                broadcast();
            } else {
                directMessage("USER not added", receivePacket.getPort());
            }
        } catch (Exception ex) {
        }
    }
    
    /**
     * Logga un utente se possibile.
     */
    public void login() {
        data = sentence.substring(("LOGIN").length()).split(" ");

        if (DATABASE.login(data[1], data[2], receivePacket.getPort())) {
            int port = DATABASE.getPortByUsername(data[1]);
            String ns = DATABASE.getNameSurnameByPort(port);
            sentence = "USER log in " + port + " " + ns;
            broadcast();
        } else {
            directMessage("USER not log in ", receivePacket.getPort());
        }
    }
    
    /**
     * Invia un messaggio con certe informazioni importanti, il messaggio è 
     * così composto:
     * 
     * MSG + porta pacchetto + nome e cognom + § + messaggio
     * MSG 12345 nome cognome § messaggio
     */
    public void sendMessage() {
        sentence = sentence.substring(0, ("MSG " + portPacket).length()+8)
                + " " + DATABASE.getNameSurnameByPort(portPacket) + " § "
                + sentence.substring(("MSG " + portPacket).length()+8);
        broadcast();
    }
    
    /**
     * Invia un messaggio ad un solo utente.
     * 
     * @param sentence messaggio da inviare
     * @param port ID dell'utente
     */
    public void directMessage(String sentence, int port) {
        try {
            sendData = sentence.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        } catch (IOException ex) {
        }
    }
    
    /**
     * Invia un messaggio a tutti gli utenti all'interno del database.
     */
    public void broadcast() {
        ArrayList<String> ports = DATABASE.getAllPort();
        for (String port : ports) {
            if (!image) {
                sendData = sentence.getBytes();
            }
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Integer.parseInt(port));
            try {
                serverSocket.send(sendPacket);
            } catch (IOException ex) {
                alert("Impossibile inviare il pacchetto", true);
            }
        }
    }
    
    /**
     * Trasforma la prima lettera in maiuscolo e le altre in minuscolo.
     * 
     * @param s stringa da modificare
     * @return stringa modificata
     */
    public String capitalize(String s) {
        s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        return s;
    }
    
    /**
     * Visualizza messaggi d'errore dell'applicazione.
     * 
     * @param s
     * @param exception 
     */
    public void alert(String s, boolean exception) {
        System.out.println(s);
    }

}
