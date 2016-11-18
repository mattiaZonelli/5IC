package robot;

import graphicsClient.ClientController;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javafx.scene.control.*;

public class Client extends Thread {

    private static final int PORT = 9090;
    private BufferedReader in;
    private BufferedReader read;
    private PrintWriter out;
    private String serverAddress;
    private Socket socket;
    private String line;
    private int state;
    private boolean stopped;
    private boolean ipCorrect = true;
    private final TextArea textArea;
    private final ClientController controller;

    /**
     * Costruttore del programma.
     *
     * @param textArea
     * @param controller
     */
    public Client(TextArea textArea, ClientController controller) {
        this.textArea = textArea;
        this.controller = controller;
    }

    /**
     * Creazione di connessione tra client e server.
     */
    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            read = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ex) {
        }
        onConnect();
    }

    /**
     * Dopo avvenuta connessione tra client e server avviene il dialogo tra i
     * due.
     */
    public void onConnect() {
        stopped = false;
        while (!stopped) {
            try {
                line = in.readLine();
                if (line.equals("STOP")) {
                    controller.showPopUp(true);
                    textArea.clear();
                    stopClient();
                } else {
                    textArea.appendText(line + "\n");
                }
            } catch (IOException ex) {
            }
        }
    }
    
    /**
     * Ferma il ciclo loop per il client.
     */
    public void stopClient(){
        stopped = true;
    }

    /**
     * Invio messaggi al server.
     *
     * @param message è il messaggio da inviare.
     */
    public void sendMessage(String message) {
        textArea.appendText("IO: " + message + "\n");
        out.println(message);
    }
    
    /**
     * 
     * @param message
     */
    public void alert(String message){
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Richiesta all'utente dell'ip del server a cui connettersi.
     * 
     * @param ip
     * @return 
     */
    public boolean requestServerAddress(String ip) {
        
        getState(ip);
        
        if (state > 3) {
            try {
                socket = new Socket(serverAddress, PORT);
            } catch (IOException e) {
                ipCorrect = false;
                alert("Ops ! Il server non risponde, riprova.");
            }
        }
        
        return ipCorrect;
        
    }

    /**
     * Panello di dialogo per scrivere l'ip del server a cui connettersi.
     *
     * @return IP per connettersi al server
     * @throws UnknownHostException
     */
    private String getServerAddress() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
        }
        return (String) JOptionPane.showInputDialog(
                null, "IP del server:", "Benvenuto nel Robot",
                3, null, null, ip);
    }

    /**
     * Controllo dell'ip fornito dall'utente.
     *
     * @param ip stringa sotto forma di IP per connettersi al server.
     */
    private void getState(String ip) {
        if (ip == null) {
            state = 0;
            System.exit(0);
        }
        String[] ipArray = ip.split("\\.", -1);
        if (("".equals(ip))) {
            state = 1;
            alert("Compila questo campo.");
        } else if (!ip.matches("[0-9.]+") || ipArray.length != 4) {
            state = 2;
            alert("IP non corretto, riprova.");
        } else if (ip.indexOf(" ") > 0) {
            state = 3;
            alert("Spazi non ammessi qui.");
        } else {
            state = 4;
        }

    }

}
