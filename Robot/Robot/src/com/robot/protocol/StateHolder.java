package com.robot.protocol;

/**
 * Classe contentente le costanti utilizzate in Protocol
 *
 * @author Nicola Pasqualetto
 * @version 1.1
 * @see com.robot.protocol.Protocol
 */
public class StateHolder {

    /**
     * Stato dei saluti.
     */
    static final int GREETINGS = 0;
    /**
     * Stato di apprendimento argomento.
     */

    static final int LEARN = 14;
    /**
     * Stato di richiesta apprendimento.
     */
    static final int WANNA_LEARN = 15;
    /**
     * Stato per l'identificazione se già conosciuto o meno.
     */
    static final int OLD_FRIEND = 10;
    /**
     * Stato per complimenti.
     */
    static final int NICE_NAME = 1;
    /**
     * Stato per la richiesta dell'età
     */
    static final int AGE = 2;
    /**
     * Stato per commento sullo stato dell'utente (se stava bene o male la volta
     * prima).
     */
    static final int FEELSBAD = 11;
    /**
     * Stato per la discussione.
     */
    static final int TALK = 12;
    /**
     * Stato per la conferma di continuare la discussione.
     */
    static final int AGAIN = 13;
    /**
     * Stato per commento su età.
     */
    static final int COMMENT_ON_AGE = 3;
    /**
     * Stato per memorizzazione luogo di residenza.
     */
    static final int PLACE_OF_LIVING = 4;

    /**
     * Stato per memorizzazione nazione di residenza.
     */
    static final int STATE_OF_LIVING = 5;
    /**
     * Stato per memorizzazione argomenti preferiti.
     */
    static final int KNOWING_OFFER = 6;
    /**
     * Stato per saluti finali.
     */
    static final int GOODBYE = 9;
    /**
     * Stato per storiella.
     */
    static final int ABOUT_ME = 7;
    /**
     * Stato racconto storiella.
     */
    static final int STORY = 8;
}
