package com.robot.protocol;

import com.robot.swing.ServerGUI;
import com.robot.utils.Robot;
import com.robot.utils.database.DBConnector;
import com.robot.utils.database.Field;
import java.util.Calendar;
import java.util.LinkedList;

/**
 * La classe rappresentante il protocollo e contentente tutti gli stati e i
 * sottostati.
 *
 * @author Nicola Pasqualetto
 * @version 1.1
 */
public class Protocol {

    /**
     * Lo stato corrente.
     */
    private int currentState;

    /**
     * Fine della trasmissione.
     */
    public static final char END_OF_TRANSMISSION = (char) 4;

    /**
     * Nuova riga di testo.
     */
    public static final char NEW_LINE = (char) 3;

    /**
     * Utitlizzato per costringere o meno il client a rispondere.
     */
    public static final char NO_WAIT_FOR_ANSWER = (char) 0;

    /**
     * L'array contenente i saluti.
     */
    private final String[] greetings = {"Hello", "Hi", "Good morning", "Good afternoon", "Good evening", "Goodnight"};

    /**
     * L'array contentente gli addii.
     */
    private final String[] farewells = new String[]{"Goodbye", "Have a nice day!", "See ya!"};

    /**
     * L'istanza dell'oggetto Robot.
     *
     * @see com.robot.utils.Robot
     */
    private final Robot robot;

    /**
     * Indica se lo stato corrente deve attendere una risposta o meno.
     */
    private boolean answerable;

    /**
     * Indica la scelta corrente dell'utente.
     */
    private String currentChoice;

    /**
     * Costruttore.
     *
     * @param robot Il robot contenente i dati dell'utente.
     */
    public Protocol(Robot robot) {
        answerable = true;
        this.robot = robot;
        if (robot.isAlreadyMemorized()) {
            currentState = StateHolder.OLD_FRIEND;
        } else {
            currentState = 0;
        }

    }

    /**
     * Prepara la stringa di invio in base allo stato corrente e predispone o
     * meno la risposta.
     *
     * @return message
     */
    public String sendMessage() {
        String message = "";
        switch (currentState) {
            case StateHolder.GREETINGS:
                message = greet() + "! I am " + robot.getROBOT_NAME() + ". What's your name? ";
                break;
            case StateHolder.NICE_NAME:
                message = "What a wonderful name! Nice to meet you, " + robot.getUserName();
                answerable = false;
                break;
            case StateHolder.AGE:
                message = "What's your age, " + robot.getUserName() + " ?";
                answerable = true;
                break;
            case StateHolder.COMMENT_ON_AGE:
                message = robot.getAge() > robot.ROBOT_AGE ? "Oh. You're older than me." : robot.getAge() == robot.ROBOT_AGE ? "We have he same age" : "Oh. You're younger than me.";
                answerable = false;
                break;
            case StateHolder.KNOWING_OFFER:
                message = knowingEachOther();
                break;
            case StateHolder.ABOUT_ME:
                message = "I'd tell you something about me either. Do you wanna listen to my story?";
                answerable = true;
                break;
            case StateHolder.OLD_FRIEND:
                message = greet() + " " + robot.getUserName() + ". How are you?";
                Field f = robot.readField("UserData", "ID=" + robot.getId(), "IFelt");
                if (f.getValue() != null) {
                    String feeling = f.getValue().toString();
                    if (feeling.contains("bad") || (feeling.contains("not") && (feeling.contains("good") || (feeling.contains("well"))))) {
                        message += " I hope better then last time.";
                    }
                }
                answerable = true;
                break; // risponde a seconda della riposta dell'utente.
            case StateHolder.PLACE_OF_LIVING:
                message = "Where do you live?";
                answerable = true;
                break;
            case StateHolder.FEELSBAD:
                message = "Oh poor thing. I'm so sorry";
                answerable = false;
                break;
            case StateHolder.TALK:

                if (robot.getTopicCounter() == 0) {
                    robot.setDatabaseConnector(DBConnector.change("topics"));
                    robot.setTopic();
                }

                message = robot.topicTalk();

                answerable = !(robot.getTopicCounter() - 1 == 0 || robot.getTopicCounter() - 1 == 2);
                break;
            case StateHolder.WANNA_LEARN:
                message = "I want to learn something more about it. Do you wanna teach me? ";
                answerable = true;
                break;
            case StateHolder.LEARN:
                message = robot.ask();
                answerable = true;
                //esce dallo stato se condizione boolean vera
                break;
            case StateHolder.AGAIN:
                message = "Do you wanna talk a little bit more?";
                answerable = true;
                break;
            case StateHolder.STATE_OF_LIVING:
                message = robot.getPlaceOfLiving() + "? In which country is it exactly?";
                answerable = true;
                break;
            case StateHolder.STORY:
                message = robot.PANDORASSTORY + "\n My fatal flaw  was curiosity. What is good in moderation is dangerous in abundance.";
                currentState = StateHolder.GOODBYE;
                answerable = false;
                break;
            case StateHolder.GOODBYE:
                message = endRobot();
                answerable = false;
                break;
            default:
                throw new AssertionError();
        }

        return message;
    }

    /**
     * Pone la macchina a stati finiti allo stato "Spegnimento".
     *
     * @return message
     */
    private String endRobot() {
        String farewell = farewells[(int) (Math.random() * 3)] + END_OF_TRANSMISSION;
        String message;
        if (robot.isAlreadyMemorized()) {
            message = "I've got to go know. ";
            Calendar c = Calendar.getInstance();
            if (c.get(Calendar.HOUR_OF_DAY) > 20) {
                message += "I'm kinda sleepy. ";
            }
        } else {
            message = "Flattered to have met you. ";
        }
        message += farewell;
        robot.end();
        return message;
    }

    /**
     * Interpreta il messaggio ricevuto e avanza di stato.
     *
     * @param message Il messaggio del client
     * @return il messaggio del client
     */
    public String interpreteMessage(String message) {
        System.out.println("STATO " + currentState + "\n");
        switch (currentState) {
            case StateHolder.GREETINGS:
                robot.setUserName(message);
                currentState++;
                break;
            case StateHolder.OLD_FRIEND:
                LinkedList<Field> list = new LinkedList<>();
                list.add(new Field("IFelt", message));
                robot.writeData("UserData", list, "ID = " + robot.getId());
                if (message.contains("bad") || (message.contains("not") && (message.contains("good") || (message.contains("well"))))) {
                    currentState = StateHolder.FEELSBAD;
                } else {
                    currentState = StateHolder.TALK;
                }
                break;
            case StateHolder.KNOWING_OFFER:
                if (isPositive(message)) {
                    robot.addPreference(currentChoice);
                    currentState = StateHolder.TALK;
                }
                robot.userDataWrite("UserData");
                break;
            case StateHolder.AGE:
                int age = getAge(message);
                robot.setAge(age);
                currentState++;
                break;
            case StateHolder.PLACE_OF_LIVING:
                String place = getPlaceOfLiving(message);
                robot.setPlaceOfLiving(place);
                currentState++;
                break;
            case StateHolder.STATE_OF_LIVING:
                String state = getPlaceOfLiving(message);
                robot.setStateOfLiving(state);
                currentState++;
                break;
            case StateHolder.ABOUT_ME:
                if (isPositive(message)) {
                    currentState++;
                } else {
                    currentState = StateHolder.GOODBYE;
                }
                break;
            case StateHolder.FEELSBAD:
                currentState = StateHolder.TALK;
                break;

            case StateHolder.WANNA_LEARN:
                if (isPositive(message)) {
                    currentState = StateHolder.LEARN;
                    robot.resetTopicCounter(); //porto il contatore a 0
                    robot.setDatabaseConnector(DBConnector.change("topics"));
                } else {
                    currentState = StateHolder.GOODBYE;
                }
                break;
            case StateHolder.AGAIN:
                if (isPositive(message)) {
                    currentState = StateHolder.TALK;
                } else {
                    currentState = StateHolder.GOODBYE;
                }
                break;
            case StateHolder.LEARN:
                boolean proceed = robot.learn(message);
                if (proceed) {
                    currentState = StateHolder.GOODBYE;
                    ServerGUI.alert();
                }
                break;
            case StateHolder.TALK:
                if (robot.getTopicCounter() == 4) {
                    robot.resetTopicCounter();
                    if (isPositive(message)) {
                        Field f = new Field("UserLikes", 1);
                        robot.writeData(robot.getTopicName(), f, "ID=" + robot.getId());
                    }
                    if (robot.getPreferences().isEmpty()) {
                        int random = (int) (Math.random() * 10);
                        if (random == 0) { //10% racconto storia
                            currentState = StateHolder.ABOUT_ME;
                        } else if (random == 1 || random == 2) { //20% richiesta stato "Learn"
                            currentState = StateHolder.WANNA_LEARN;
                        } else if ((int) (Math.random() * 2) == 0) { //35% richiesta di continuare
                            currentState = StateHolder.KNOWING_OFFER;
                        } else {
                            currentState = StateHolder.GOODBYE; // 35% richiesta di uscire
                        }
                    } else {
                        currentState = StateHolder.AGAIN;
                    }
                }
                break;

        }
        return message;
    }

    /**
     * Inteprete degli stati PLACE_OF_LIVING e STATE_OF_LIVING
     *
     * @param s stringa ricevuta
     * @return la stirnga estrapolata
     */
    private static String getPlaceOfLiving(String s) {
        String out = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
                while (i < s.length() && s.charAt(i) != ' ' && s.charAt(i) != ',' && s.charAt(i) != '.') {
                    out += s.charAt(i);
                    i++;
                }
            }
        }
        return out;
    }

    /**
     * Ottiene l'età da una stringa.
     *
     * @param s L'età in stringa
     * @return età
     */
    private static int getAge(String s) {
        String out = "";
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                out += s.charAt(i);
            }
        }
        return Integer.parseInt(out);
    }

    /**
     * Propone degli argomenti.
     *
     * @return messaggio
     */
    private String knowingEachOther() {
        String message = "";
        currentChoice = robot.randomTopics();
        if (robot.availableTopics() == 0) {
            currentState = StateHolder.GOODBYE - 1;
            answerable = false;
        } else {
            message = "Do you like " + currentChoice.toLowerCase() + "?";
            answerable = true;
        }
        return message;
    }

    /**
     * Saluta in base all'orario.
     *
     * @return saluto
     */
    private String greet() {
        int randomGreeting = (int) ((Math.random() * 1000) % 3);
        if (randomGreeting == 2) {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            if (hour > 13 && hour < 18) {
                randomGreeting = 3;
            } else if (hour >= 18 && hour <= 22) {
                randomGreeting = 4;
            } else {
                randomGreeting = 5;
            }
        }
        answerable = true;
        return greetings[randomGreeting];

    }

    /**
     * indica se il protocollo è rispondibile e avanza di stato.
     *
     * @return condizione
     */
    public boolean isAnswerable() {
        if ((!answerable) && (currentState != StateHolder.TALK)) {
            currentState++;
        }
        return answerable;
    }

    public int getCurrentState() {
        return currentState;
    }

    /**
     * Intepreta il messaggio se positivo o meno.
     *
     * @param message messaggio
     * @return se affermativo o meno
     */
    public static boolean isPositive(String message) {
        message = message.toLowerCase();
        return ((message.contains("yes")
                || message.contains("sure")
                || message.contains("positive")
                || message.contains("of course")
                || message.contains("indeed"))
                && !message.contains("but"));
    }

}
