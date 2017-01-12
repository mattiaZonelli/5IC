package com.robot.client;

import com.robot.protocol.Protocol;
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
 * Il client che interpreta il protocollo e permette all'utente di comunicare
 * con il server<br>
 * Al momento della connessione viene fornito un token per il riconoscimento e
 * poi viene avviata la vera e propria comunicazione.
 *
 * @author Nicola Pasqualetto
 * @version 1.1
 */
public final class Client {

    /**
     * L'endpoint di comunicaizione.
     */
    private static Socket socket;

    /**
     * L'ip del client.
     */
    private final String IP;
    /**
     * La porta di comunicazione.
     */

    private final int PORT;
    /**
     * Il file dove è memorizzato il token.
     */

    private final File TOKEN_FILE;

    /**
     * Interfaccia grafica.
     */
    private static ClientGUI clientGUI;

    /**
     * Il messaggio come fornito dall'endpoint.
     */
    private String raw;

    /**
     * Il messaggio interpretato correttamente.
     */
    private String line;

    /**
     * Indica se la connessione è terminata o meno.
     */
    private boolean ended;

    /**
     * Stream per l'invio dei dati.
     *
     * @see java.io.PrintStream
     */
    private PrintStream output;

    /**
     * Stream in input per la ricezione dei dati.
     *
     * @see java.io.BufferedReader
     */
    private BufferedReader input;

    /**
     * Costruttore<br>
     * Legge dal file il token, istanzia l'interfaccia grafica e gli stream I/O;
     * predispone la chiusura della finestra senza crash del server e avvia la
     * comunicazione.
     *
     * @param IP L'IP al quale ci si deve connettere.
     * @param PORT La porta di connessione
     * @see Socket
     */
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

    /**
     * Consente la chiusura dell'istanza di comunicazione del server con questo
     * particolare client, tramite l'invio del carattere 4 ASCII utilizzato dal
     * protocollo come END OF TRANSMISSION.
     *
     * @see com.robot.protocol.Protocol
     */
    public void handleClosing() {
        clientGUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                output.println("END" + Protocol.END_OF_TRANSMISSION);
                System.exit(0);

            }
        });
    }

    /**
     * Metodo main.
     *
     * @param args Non usato
     */
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

    /**
     * Permette la visualizzazione sulla finestra della GUI delle stringhe
     * formattate tramite uno StyledDocument<br>
     * Vengono definiti per l'utente un allineamento a destra, caratteri normali
     * e rossi, mentre per la ricezione dal server allineamento a sinistra,
     * grassetto e colore nero.
     *
     * @see javax.swing.text.StyledDocument
     * @param str La stringa da scrivere a video.
     * @param rightToLeft Indica se la stringa è ricevuta o inviata.
     */
    public static void display(String str, boolean rightToLeft) {
        StyledDocument doc = clientGUI.getDocument();
        if (rightToLeft) {
            str = "[YOU]: " + str;
            SimpleAttributeSet answer = new SimpleAttributeSet();
            StyleConstants.setAlignment(answer, StyleConstants.ALIGN_RIGHT);
            StyleConstants.setForeground(answer, Color.red);
            StyleConstants.setBold(answer, false);
            doc.setParagraphAttributes(doc.getLength(), str.length(), answer, false);
        } else {
            str = "[PANDORA]: " + str;
            SimpleAttributeSet question = new SimpleAttributeSet();
            StyleConstants.setBold(question, true);
            StyleConstants.setForeground(question, Color.BLACK);
            StyleConstants.setAlignment(question, StyleConstants.ALIGN_LEFT);
            doc.setParagraphAttributes(doc.getLength(), str.length(), question, false);
        }
        try {
            doc.insertString(doc.getLength(), "\n" + str + "\n", null);
        } catch (BadLocationException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo che invia un token memorizzato in un apposito file al server per
     * il riconoscimento dell'utente.
     *
     * @param input stream di lettura.
     * @param output stream di scrittura.
     */
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

    /**
     * Permette la comunicazione tra client e server fino alla ricezione di un
     * END_OF_TRANSMISSION o alla chiusura della finestra GUI.
     */

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
            if (raw.contains("" + Protocol.END_OF_TRANSMISSION)) {
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

    /**
     * Permette di inviare una riga di testo al server.
     */
    public void sendText() {
        if (raw.contains("" + Protocol.END_OF_TRANSMISSION) || !clientGUI.isDisplayable()) {
            ended = true;
        } else if (!line.contains("" + Protocol.NO_WAIT_FOR_ANSWER) && !line.contains("" + Protocol.END_OF_TRANSMISSION)) {
            String in = clientGUI.getInputField().getText();
            output.println(in);
            display(in, true);
            clientGUI.getInputField().setText("");

        }
    }

    /**
     * Interpreta la riga appena ricevuta e la decodifica.
     *
     * @param message Il messaggio ricevuto
     * @return message
     */
    public static synchronized String interpreteCode(String message) {
        if (message == null) {
            try {
                socket.close();
            } catch (IOException ex) {
            }
            display("*******************************DISCONNECTED********************************", false);
        }
        message = message.replaceAll("" + Protocol.NEW_LINE, "\n");
        return message.replaceAll("" + Protocol.END_OF_TRANSMISSION, "");
    }
}
