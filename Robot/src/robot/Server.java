package robot;

import graphicsServer.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.scene.control.TextArea;

public class Server extends Thread {

    private static final int PORT = 9090;
    private final ArrayList<User> USERS;
    private ServerSocket listener;
    private final boolean stopped = false;
    private final TextArea textArea;
    private final ServerController controller;
    private int ID = 1;

    private final String errorMessage[] = {
        "ERRORE: non è possibile collegarsi nella porta "
        + "selezionata PORT=[" + PORT + "].\n",
        "SERVER DISCONNESSO.\n",
        "Impossibile chiudere la connessione con un "
        + "un client.\n"
    };

    /**
     * Costruttore del programma, in cui
     *
     * @param textArea
     * @param controller
     */
    public Server(TextArea textArea, ServerController controller) {
        this.textArea = textArea;
        this.controller = controller;
        USERS = new ArrayList();
    }

    /**
     * Viene gestita la connessione di più client e gli eventuali errori che
     * verranno segnalati, interrompendo il programma, tramite la funzione
     * errorMessage().
     *
     */
    @Override
    public void run() {
        //Connessione del Server
        try {
            listener = new ServerSocket(PORT);
            textArea.appendText("SERVER CONNESSO.\n");
        } catch (IOException | IllegalArgumentException ex) {
            errorMessage(0);
        }

        //Gestione connessione dei Client
        try {
            while (!stopped) {
                User user = new User(listener.accept(), ID++);
                textArea.appendText("Client connesso.\n");
                user.start();
                USERS.add(user);
            }
        } catch (IOException ex) {
            errorMessage(1);
        } finally {
            try {
                listener.close();
            } catch (IOException ex) {
                errorMessage(2);
            }
        }
    }

    /**
     *
     * @return arraylist di tutti i clienti connessi.
     */
    public ArrayList getUsers() {
        return USERS;
    }

    /**
     * Mi permette di fermare la ricezione di client.
     */
    public void stopServer() {
        stopConnection();
        try {
            listener.close();
        } catch (IOException ex) {
        }
    }

    /**
     * Blocca la connessione dei client connessi.
     */
    private void stopConnection() {
        for (User user : USERS) {
            user.stopConnection();
        }
        USERS.clear();
    }

    /**
     * Scrive nel server il messaggio di errore nella posizione selezionata da
     * index in base alla tipologia di errore in cui si è andati incontro.
     *
     * @param index indica la posizione nell'array errorMessage.
     */
    protected void errorMessage(int index) {
        textArea.appendText(errorMessage[index]);
    }

    /**
     * Scrive nel server il messaggio che riceve da parametro
     *
     * @param message
     */
    protected void message(String message) {
        textArea.appendText(message);
    }

    /**
     * INNER CLASS.
     *
     * @author marco
     */
    public class User extends Thread {

        private final int ID;
        private final Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private final Protocol protocol;
        private String nameUser;

        private boolean stopped = false;
        private String line;

        public User(Socket socket, int ID) throws IOException {
            protocol = new Protocol(this);
            nameUser = "";
            this.socket = socket;
            this.ID = ID;
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        @Override
        public void run() {
            try {

                while (!stopped) {
                    line = "SERVER: " + protocol.state(in.readLine());
                    out.println(line);
                }

            } catch (IOException e) {
                message("Client disconnesso.\n");
            }
        }

        public void stopConnection() {
            out.println("STOP");
            stopped = true;
        }

        public int getID() {
            return ID;
        }

        public String getNameUser() {
            return nameUser;
        }

        public void setNameUser(String name) {
            nameUser = name;
        }
    }

}
