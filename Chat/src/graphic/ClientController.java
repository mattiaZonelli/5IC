package graphic;

import com.jfoenix.controls.JFXButton;

import chat.*;
import de.jensd.fx.glyphs.fontawesome.*;
import img.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.net.*;
import java.text.*;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.beans.value.*;
import javafx.embed.swing.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.stage.FileChooser.*;
import javax.imageio.*;

/**
 * Controller per la gestione grafica del programma.
 *
 * @author Marco Tasca
 */
public class ClientController implements Initializable {
    
    //Font awesome icon
    private FontAwesomeIconView eye;
    private FontAwesomeIconView eyeSlash;

    //Generico
    private VBox messageBody;
    private AnchorPane messagePanel, spacePanel;
    private StackPane messageContainer;
    private Label message, messageName, messageHour;
    private final EventHandler<ActionEvent> myHandler;

    //La foto deve avere un estensione .png
    private final ArrayList<String> nameImage = new ArrayList<>(Arrays.asList(
            "image"
    ));

    UDPClient CLIENT;
    
    //Elementi per immagine
    ImageConverter converter;
    BufferedImage bufferedImage;
    int i;
    ArrayList<BufferedImage> images;

    //Chat Room
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox VBox;
    @FXML
    private TextField inputField;
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private VBox VBoxButton;

    //Log In
    @FXML
    private Pane loginPanel;
    @FXML
    private TextField usernameLogin;
    @FXML
    private PasswordField passwordLogin;
    @FXML
    private Button buttonLogin;
    @FXML
    private Label errorLabelLogin;
    @FXML
    private ToggleButton toggleLogin;
    @FXML
    private TextField passwordTextLogin;  
    @FXML
    private ToggleButton showHideLogin;
    @FXML
    private FontAwesomeIconView eyeLogin;
    @FXML
    private FontAwesomeIconView eyeSlashLogin;
    @FXML
    private AnchorPane panelSignupLogin;

    //Sign Up
    @FXML
    private Pane signupPanel;
    @FXML
    private TextField usernameSignup;
    @FXML
    private Button buttonSignup;
    @FXML
    private TextField nameSignup;
    @FXML
    private TextField surnameSignup;
    @FXML
    private Label errorLabelSignup;
    @FXML
    private ToggleButton toggleSignup; 
    @FXML
    private PasswordField passwordSignup;
    @FXML
    private TextField passwordTextSignup;
    @FXML
    private ToggleButton showHideSignup;
    @FXML
    private FontAwesomeIconView eyeSignup;
    @FXML
    private FontAwesomeIconView eyeSlashSignup;

    /**
     * Inizializza il programma.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        converter = new ImageConverter();
        i = 0;
        images = new ArrayList<>();
        
        CLIENT = new UDPClient(this);
        CLIENT.start();

        mainPanel.getStyleClass().add("mainPanel");

        VBox.getStyleClass().add("VBox");
        VBox.setAlignment(Pos.BOTTOM_CENTER);

        scrollPane.getStyleClass().add("scrollPane");
        scrollPane.setContent(VBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBoxButton.setSpacing(20);
        VBoxButton.getStyleClass().add("VBoxButton");
        VBoxButton.setAlignment(Pos.BOTTOM_CENTER);

        toggleSignup.setSelected(true);
        eyeSlashSignup.setVisible(false);
        eyeSlashLogin.setVisible(false);
        passwordTextSignup.setVisible(false);
        passwordTextLogin.setVisible(false);

        createButton();
    }

    /**
     * Crea la grafica dei bottoni per inviare documenti e foto.
     */
    public void createButton() {
        for (String image : nameImage) {
            JFXButton button = new JFXButton();
            button.getStyleClass().add("circle-button");
            button.setStyle("-fx-background-image: url('img/" + image + ".png')");
            button.setText(image);
            button.setOnAction(myHandler);
            VBoxButton.getChildren().add(button);
        }
    }

    /**
     * Rilevando che tipo di bottone si tratta, mi fa partire funzioni diverse.
     */
    public ClientController() {
        this.myHandler = (ActionEvent event) -> {
            JFXButton button = (JFXButton) event.getSource();
            if (button.getText().equals(nameImage.get(0))) {
                chooseImage();
            }
        };
    }
    
    /**
     * Selezione immagine da parte dell'utente.
     */
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            CLIENT.sendImage(selectedFile.getAbsolutePath(), selectedFile.getName());
        }
    }
          
    /**
     * Crea pannello per l'immagine.
     * 
     * @param msg stringa di byte che formano un immagine.
     * @return 
     */
    public VBox createImageView(String msg) {
        VBox v = new VBox();
        v.setSpacing(10);

        Image image = SwingFXUtils.toFXImage(bufferedImage, null);

        ImageView myImageView = new ImageView();
        myImageView.setImage(image);
        myImageView.setFitWidth(250.0);

        v.getChildren().add(myImageView);

        HBox h = new HBox();
        h.setSpacing(15);
        h.setAlignment(Pos.CENTER_RIGHT);

        FontAwesomeIconView downloadImage = new FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD);
        downloadImage.setSize("20.0");
        downloadImage.setStyle("-fx-fill: #707070");

        JFXButton b = new JFXButton();
        b.getStyleClass().add("circle-button-download");
        b.setGraphic(downloadImage);

        int indice = i;
        b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveImage(images.get(indice));
                event.consume();
            }
        });

        h.getChildren().add(b);
        h.getChildren().add(downloadImage);
        h.setStyle("-fx-padding: 0 3 0 0");

        v.getChildren().add(h);

        images.add(bufferedImage);
        i++;

        return v;
    }
    
    /**
     * Salva le immagini con un nome che vuole l'utente.
     * @param c 
     */
    public void saveImage(BufferedImage c) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        ExtensionFilter extensionFilter=new ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File destination = fileChooser.showSaveDialog(null);
        
        if (destination != null) {
            String nameFile = destination.getName();
            String extension = nameFile.substring(nameFile.lastIndexOf(".")+1, nameFile.length());
            try {
                ImageIO.write(c, extension, destination);
            } catch (IOException ex) {}
        }
    }

    /**
     * Dall'input form quando viene cliccato il tasto enter, mi crea il box del
     * messaggio inviandolo.
     *
     * @param event è l'evento del bottone cliccato.
     */
    @FXML
    private void sendMessage(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (inputField.getText().length() != 0) {
                addMessage(inputField.getText(), true, false, false);
                CLIENT.sendMessage(inputField.getText());
            }
        }
    }

    /**
     * Aggiunge messaggio a scrollPanel.
     *
     * @param msg
     * @param myMessage per collocare il messaggio a destra o sinistra
     * @param newUser per dare un grafica differente al messaggio
     * @param myImage per creare il pannello grafico per l'immagine
     */
    public void addMessage(String msg, boolean myMessage, boolean newUser, boolean myImage) {
        //Uso Platform per interfacciare i thread che non arrivano da JAVAFX
        Platform.runLater(() -> {
            createPanel();
            createMessage(msg, myMessage, newUser, myImage);

            VBox.getChildren().addAll(messageContainer);
            scrollPane.setVvalue(1.0);
            inputField.clear();
        });
    }

    /**
     * Crea la grafica del pannello del messaggio.
     */
    private void createPanel() {
        messagePanel = new AnchorPane();
        messageContainer = new StackPane();
        messagePanel.getStyleClass().add("panelMessage");
        messageContainer.getStyleClass().add("stackPanelMessage");
    }

    /**
     * Crea il messaggio tagliandolo se troppo lungo e componendo la grafica con
     * gli elementi creati precedentemente, come ad esempio il pannello.
     *
     * @param msg
     * @param myMessage mi permette di capire se il messaggio da visualizzare
     * deve stare a destra o a sinistra e quindi capire se il messaggio è stato
     * inviato da me o no.
     */
    private void createMessage(String msg, boolean myMessage, boolean newUser, boolean isImage) {
        messageBody = new VBox();
        messageBody.getStyleClass().add("messageBody");

        createLabelMessage();
        createLabelMessageName();
        createLabelMessageHour();

        //ADD TO MESSAGE BODY
        if (!newUser) {
            if (!myMessage) {
                if (!isImage) {
                    String data[] = msg.split("§");
                    messageName.setStyle("-fx-text-fill: " + data[0]);
                    messageName.setText(data[1].trim());
                    message.setText(data[2].trim());
                    messageBody.getChildren().add(messageName);
                    messageBody.getChildren().add(message);
                    messageBody.getChildren().add(messageHour);
                } else {
                    messageBody.getChildren().add(createImageView(msg));
                }
            } else if (!isImage) {
                message.setText(msg);
                messageBody.getChildren().add(message);
                messageBody.getChildren().add(messageHour);
            } else {
                messageBody.getChildren().add(createImageView(msg));
            }
        } else {
            messagePanel.getStyleClass().add("panelNewUser");
            message.getStyleClass().add("labelNewUser");
            message.setText(msg + " entra nella chat");
            messageBody.getChildren().add(message);
        }

        //SIZE MESSAGE PANEL
        messagePanel.setMaxWidth(message.getWidth());
        messagePanel.getChildren().addAll(messageBody);

        if (!newUser) {
            //LEFT OR RIGHT
            spacePanel = new AnchorPane();
            spacePanel.setMaxWidth(VBox.getWidth() - messagePanel.getWidth());

            messageContainer.getChildren().addAll(spacePanel, messagePanel);

            if (myMessage) {
                StackPane.setAlignment(spacePanel, Pos.CENTER_LEFT);
                StackPane.setAlignment(messagePanel, Pos.CENTER_RIGHT);
            } else {
                StackPane.setAlignment(spacePanel, Pos.CENTER_RIGHT);
                StackPane.setAlignment(messagePanel, Pos.CENTER_LEFT);
            }
        } else {
            messageContainer.getChildren().addAll(messagePanel);
            StackPane.setAlignment(messagePanel, Pos.CENTER);
        }
    }
    
    /**
     * Crea label del messaggio.
     */
    public void createLabelMessage() {
        message = new Label();
        message.getStyleClass().add("labelMessage");
        message.setMaxWidth(250.0);
        message.setWrapText(true);
        message.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                messageHour.setMaxWidth(Double.parseDouble(newVal.toString()));
            }
        });
    }
    
    /**
     * Crea label con nome di chi ha mandato il messaggio.
     */
    public void createLabelMessageName() {
        messageName = new Label();
        messageName.getStyleClass().add("labelMessageName");
    }
    
    /**
     * Crea label dell'ora attuale.
     */
    public void createLabelMessageHour() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        messageHour = new Label(dateFormat.format(date));
        messageHour.setAlignment(Pos.CENTER_RIGHT);
        messageHour.getStyleClass().add("labelMessageHour");
    }

    /**
     * Cambia schermata a: login.
     *
     * @param event
     */
    @FXML
    private void loginSelected(MouseEvent event) {
        loginSelected();
    }

    /**
     * Cambia schermata a: signup.
     *
     * @param event
     */
    @FXML
    private void signupSelected(MouseEvent event) {
        signupSelected();
    }
    
    /**
     * Recupera password dai campi password.
     * 
     * @param PF è PasswordField
     * @param TF è TextField
     * @return 
     */
    private String getPasswordFromInput(PasswordField PF, TextField TF) {
        String temp = "";
        if (PF.getText().length() > TF.getText().length()) {
            temp = PF.getText();
        } else {
            temp = TF.getText();
        }
        return temp;
    }

    /**
     * Visualizza chatroom a login avvenuto.
     *
     * @param event
     */
    @FXML
    private void login(MouseEvent event) {
        login();
    }
    
    /**
     * Invia dati al client che poi saranno inviati al server.
     */
    private void login(){
        String username = usernameLogin.getText();
        String password = getPasswordFromInput(passwordLogin, passwordTextLogin);

        if (username.isEmpty() || password.isEmpty()) {
            errorLabelLogin.setText("Compila tutti i campi !");
        } else {
            CLIENT.login(username, password);
        }
    }

    /**
     * Invia i dati al database per salvare il nuovo utente.
     *
     * @param event
     */
    @FXML
    private void signup(MouseEvent event) {
        signup();
    }
    
    /**
     * Invia dati al client che poi saranno inviati al server.
     */
    private void signup(){
        String username = usernameSignup.getText();
        String name = nameSignup.getText();
        String surname = surnameSignup.getText();
        String password = getPasswordFromInput(passwordSignup, passwordTextSignup);

        if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            errorLabelSignup.setText("Compila tutti i campi !");
        } else {
            CLIENT.addUser(name, surname, username, password);
        }
    }

    /**
     * Visualizza password nell'area signup tramite il bottone.
     *
     * @param event
     */
    @FXML
    private void showHideSignup(MouseEvent event) {
        if (eyeSignup.isVisible()) {
            eyeSignup.setVisible(false);
            eyeSlashSignup.setVisible(true);
            passwordSignup.setVisible(false);
            passwordTextSignup.setVisible(true);
            passwordTextSignup.setText(passwordSignup.getText());
        } else {
            eyeSignup.setVisible(true);
            eyeSlashSignup.setVisible(false);
            passwordSignup.setVisible(true);
            passwordTextSignup.setVisible(false);
            passwordSignup.setText(passwordTextSignup.getText());
        }
    }
    
    /**
     * Visualizza password nell'area login tramite il bottone.
     *
     * @param event
     */
    @FXML
    private void showHideLogin(MouseEvent event) {
        if (eyeLogin.isVisible()) {
            eyeLogin.setVisible(false);
            eyeSlashLogin.setVisible(true);
            passwordLogin.setVisible(false);
            passwordTextLogin.setVisible(true);
            passwordTextLogin.setText(passwordLogin.getText());
        } else {
            eyeLogin.setVisible(true);
            eyeSlashLogin.setVisible(false);
            passwordLogin.setVisible(true);
            passwordTextLogin.setVisible(false);
            passwordLogin.setText(passwordTextLogin.getText());
        }
    }
    
    /**
     * Mostra schermata login se utente seleziona login.
     */
    public void loginSelected() {
        toggleLogin.setSelected(true);
        toggleSignup.setSelected(false);
        signupPanel.setVisible(false);
        loginPanel.setVisible(true);
    }
    
    /**
     * Mostra schermata signup se utente seleziona login.
     */
    public void signupSelected() {
        toggleSignup.setSelected(true);
        toggleLogin.setSelected(false);
        signupPanel.setVisible(true);
        loginPanel.setVisible(false);
    }

    /**
     * Visualizza chatroom.
     */
    public void showChat() {
        panelSignupLogin.setVisible(false);
    }
    
    /**
     * Visualizza errori schermata Signup.
     * @param error 
     */
    public void errorSignup(String error){
        errorLabelSignup.setText(error);
    }
    
    /**
     * Visualizza errori schermata login.
     * @param error 
     */
    public void errorLogin(String error){
        errorLabelLogin.setText(error);
    }
    
    /**
     * Setta istanza immagine.
     * @param image 
     */
    public void setImage(byte[] image) {
        bufferedImage = converter.convertByteToBufferedImage(image);
    }

    @FXML
    private void loginFromTextFieldLogin(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            login();
        }
    }

    @FXML
    private void loginFromPasswordFieldLogin(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            login();
        }
    }

    @FXML
    private void signupFromTextFieldSignup(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            signup();
        }
    }

    @FXML
    private void signupFromPasswordFieldSignup(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            signup();
        }
    }

}
