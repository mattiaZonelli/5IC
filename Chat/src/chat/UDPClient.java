package chat;

import color.*;
import graphic.*;
import img.*;
import java.io.*;
import java.net.*;
import javafx.application.Platform;

/**
 * UDPClient implementa un applicazione progettata per gli utenti che vogliono
 * chattare con un gruppo di persone potendo inviare e scaricare immagini.
 *
 * @author Marco Tasca
 * @version 1.0
 * @since 2016-12-11
 */
public final class UDPClient extends Thread {

    BufferedReader inFromUser;
    DatagramSocket clientSocket;
    InetAddress IPAddress;

    int myID;
    boolean myImage;
    String myColor;

    DatagramPacket sendPacket, receivePacket;
    byte[] sendData, receiveData;
    String sentence;

    ClientController controller;
    ImageConverter converter;

    /**
     * Costruttore della classe UDPClient, inizializzazione di variabili e della
     * connessione UDP Server-Client.
     *
     * @param controller è un controller per la grafica dell'applicazione per
     * gli utenti.
     */
    public UDPClient(ClientController controller) {
        this.controller = controller;
        try {
            inFromUser = new BufferedReader(new InputStreamReader(System.in));
            clientSocket = new DatagramSocket();
            IPAddress = InetAddress.getByName("localhost");
            myColor = Colors.randomColors();
            converter = new ImageConverter();
            identify();
        } catch (SocketException | UnknownHostException ex) {
            alert("Impossibile connettersi", true);
        }
    }

    /**
     * Invio di una stringa per essere identificati dal Server.
     */
    public void identify() {
        send("ID ");
    }

    /**
     * Cuore della classe, pulisce i contenitori dait, riceve messaggi e li
     * analizza.
     */
    @Override
    public void run() {
        while (true) {
            clear();
            receive();
            analyze();
        }
    }

    /**
     * Pulisce i contenitori dati.
     */
    public void clear() {
        sendData = new byte[65500];
        receiveData = new byte[65500];
    }

    /**
     * Invia messaggio al Server.
     *
     * @param sentence messaggio da inviare.
     */
    public void send(String sentence) {
        if (!myImage) {
            sendData = sentence.getBytes();
        }
        sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        try {
            clientSocket.send(sendPacket);
        } catch (IOException ex) {
            alert("Impossibile inviare il pacchetto", true);
        }
    }

    /**
     * Riceve messaggi dal Server.
     */
    public void receive() {
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            clientSocket.receive(receivePacket);
        } catch (IOException ex) {
            alert("Impossibile ricevere il pacchetto", true);
        }

        sentence = new String(receivePacket.getData());
    }

    /**
     * Analizza il messaggio ricevuto.
     */
    public void analyze() {
        if (sentence.startsWith("ID")) {
            setMyId();
        } else if (sentence.startsWith("USER added")) {
            userAdded();
        } else if (sentence.startsWith("USER not added")) {
            userNotAdded();
        } else if (sentence.startsWith("USER log in")) {
            userLogIn();
        } else if (sentence.startsWith("USER not log in")) {
            userNotLogIn();
        } else if (sentence.startsWith("MSG")) {
            userSendMessage();
        } else {
            userSendImage();
        }
    }

    /**
     * Inizializza l' ID, ricevuto dal Server dopo essersi identificati.
     */
    public void setMyId() {
        myID = Integer.parseInt(sentence.substring(("ID").length()).trim());
    }

    /**
     * L'utente si registra e viene mostrata la schermata di login.
     */
    public void userAdded() {
        String data[] = sentence.substring("USER added".length()).split(" ");
        controller.loginSelected();
    }

    /**
     * L'utente si registra ma ha usato un username già in uso così viene
     * visualizzato l'errore.
     */
    public void userNotAdded() {
        Platform.runLater(() -> {
            controller.errorSignup("Username già in uso, riprovare.");
        });
    }

    /**
     * L'utente si logga e viene visualizzata la chat da parte dell'utente,
     * mentre gli altri utenti visualizeranno un messaggio che tale utente
     * si è collegato ed è entrato nella chat.
     */
    public void userLogIn() {
        controller.showChat();
        if (!sentence.contains("" + myID)) {
            controller.addMessage(sentence.substring(("USER log in " + myID).length()).trim(), false, true, false);
        }
    }
    
    /**
     * L'utente si logga ma i dati sono sbagliati così si visualizza un errore
     * nella schermata di login.
     */
    public void userNotLogIn() {
        Platform.runLater(() -> {
            controller.errorLogin("Username o password errati, riprovare.");
        });
    }

    public void userSendMessage() {
        if (!sentence.contains("" + myID)) {
            int sizeInfo = ("MSG " + myID).length();
            String color = sentence.substring(sizeInfo, sizeInfo + 8);
            controller.addMessage(color.trim() + "§" + sentence.substring(sizeInfo + color.length()), false, false, false);
        }
    }
    
    /**
     * L'utente visualizza l'immagine che è arrivata.
     */
    public void userSendImage() {
        controller.setImage(receivePacket.getData());
        if (!myImage) {
            controller.addMessage(null, false, false, true);
        } else {
            controller.addMessage(null, true, false, true);
        }
        myImage = false;
    }
    
    /**
     * L'utente si registra dalla schermata grafica e invia i dati al server
     * per essere salvato nel database.
     * 
     * @param name nome utente sarà visualizzato nei messaggi
     * @param surname cognome utente sarà visualizzato nei messaggi
     * @param username sarà utilizzato per il login
     * @param password sarà utilizzata per il login
     */
    public void addUser(String name, String surname, String username, String password) {
        password = password + " end";
        String newUser = "SIGNUP " + name + " " + surname + " " + username
                + " " + password;
        send(newUser);
    }
    
    /**
     * L'utente si logga dalla schermata grafica e invia i dati al server per
     * accertarsi che sia un utente registrato.
     * 
     * @param username utilizzato per il login
     * @param password utilizzato per il login
     */
    public void login(String username, String password) {
        password = password + " end";
        String login = "LOGIN " + username + " " + password;
        send(login);
    }
    
    /**
     * L'utente invia un messaggio al server che verrà propagato a tutti.
     * 
     * @param message da inviare al Server.
     */
    public void sendMessage(String message) {
        send("MSG " + myID + " " + myColor + " " + message);
    }
    
    /**
     * L'utente invia un'immagine che verrà propagata a tutti.
     * 
     * @param absolutePath percorso assoluto immagine.
     * @param nameFile nome immagine.
     */
    public void sendImage(String absolutePath, String nameFile) {
        myImage = true;
        String extension = nameFile.substring(nameFile.lastIndexOf(".") + 1, nameFile.length());
        sendData = converter.getByteImage(absolutePath, extension);
        send(converter.getStringImage(absolutePath, extension));
    }
    
    /**
     * Visualizza messaggi d'errore dell'applicazione.
     * 
     * @param s
     * @param exception 
     */
    public void alert(String s, boolean exception) {
        if (exception) {
            System.out.println(s);
        } else {
            System.out.print(s);
        }
    }

}
