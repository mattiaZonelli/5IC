package com.robot.server;

import com.robot.protocol.Protocol;
import com.robot.utils.Robot;
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
 * La classe dedicata alla comunicazione con uno ed un solo client; crea il
 * robot in base alla comunicazione con solo tale utente e lo memorizza.
 *
 * @author Nicola Pasqualetto
 * @version 1.1
 */
public class ConcurrentTasksTrigger implements Runnable {

    /**
     * L'endopoint di comunicazione.
     */
    private static Socket socket;
    /**
     * Stream di output.
     */

    public OutputStream outputStream;
    /**
     * Stream di input.
     */

    public InputStream inputStream;
    /**
     * L'istanza della macchina a stati finiti.
     */

    private static Robot robot;

    /**
     * Costruttore.
     *
     * @param socket L'endpoint accettato in ServerConnectionListener
     * @param os Lo stream di uscita creato in ServerConnectionListener
     * @param is Lo stream di entrata creato in ServerConnectionListener
     * @see com.robot.server.ServerConnectionListener
     */

    public ConcurrentTasksTrigger(Socket socket, OutputStream os, InputStream is) {
        robot = new Robot();
        this.socket = socket;
        outputStream = os;
        inputStream = is;

    }

    /**
     * Permette di codificare il messaggio ed inviare più righe
     * contemporaneamente.
     *
     * @param message il messaggio
     * @return messaggio codificato
     */
    public static synchronized String codeMessage(String message) {
        return message.replaceAll("\n", "" + Protocol.NEW_LINE);
    }

    /**
     * Viene ottenuto il token assicurandosi che sia univoco, in quanto la
     * probabilità che vengano assegnati due token da due istanze di questa
     * classe nello stesso millisecondo è bassissima.
     *
     * @return il token
     */
    public static synchronized long getToken() {
        return System.currentTimeMillis();
    }

    /**
     * Legge il token ed eventualmente lo consegna se quest'ultimo è uguale a 0.
     *
     * @param ps stream di output
     * @param br stream di input
     * @return token
     */
    public synchronized long readToken(PrintStream ps, BufferedReader br) {
        long token = Long.MIN_VALUE;
        try {
            token = Long.parseLong(br.readLine());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error occurred while reading token", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (token == 0) { // se == 0 allora non è presente nel file del client
            token = getToken();
            ps.println(Long.toString(token));
        }
        if (token < 0) {
            throw new IllegalArgumentException("Must be a non-negative integer");
        }
        return token;
    }

    /**
     * Ciò che questo thread deve fare.
     *
     * @see java.lang.Runnable
     */
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
                    String receive = protocol.interpreteMessage(br.readLine());
                    System.out.println("RISPOSTA: " + receive);
                    if (receive.contains("" + Protocol.END_OF_TRANSMISSION)) {
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
                message = codeMessage(message) + Protocol.NO_WAIT_FOR_ANSWER;
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

    /**
     * Termina la connessione brutalmente.
     */
    public static void abort() {
        try {
            robot.end();
            socket.close();
        } catch (IOException ex) {

        }
    }

}
