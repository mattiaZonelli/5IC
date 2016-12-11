package myknockknock;

import java.net.*;
import java.io.*;
import java.util.Random;

public class Protocolkk {

    private static final int waiting = 0;
    private static final int sent = 1;
    private int state = waiting;
    private final String[][] domande = {{"chi sei?", "come ti chiami?"}, {"come stai?", "come va?"}, {"...", " ", ""}, {"si", "va bene", "certo", "sicuramente", "certamente"}, {"no", "niente", "nulla"}, {"Che tempo farà domani?", "Secondo te pioverà?", "Pensi che ci sarà nebbia?" ,"quanti gradi ci sono?"}};
    private final String[][] risposte = {{"nessuno", "et", "non ho nome"}, {"bene", "male", "tutto bene"}, {"che c'è?", "non hai piu' nulla da dire?", "ti ho lasciato senza parole?"}, {"sono contento ", "buon per te"}, {"mi dispiace", "peggio per te", "forse non avrei dovuto chiedertelo.."}, {"Non lo so per sicurezza portati un ombrello", "Non lo so potresti guardare cosa prevedono", "Non lo so vestiti a strati per sicurezza"}};
    private String[] rds = {"non lo so", "bho", "non chiederlo a me", "...Parli con me?"}; //risposte a domande sconosciute
    private String[] frasirandom = {"non lo so", "bho", "Bello ma non mi interessa", "...Parli con me?", "meh", "scusa non sono informato"};

    public String processInput(String theInput) {
        String theOutput = null;

        Random random = new Random();

        if (state == waiting) {
            theOutput = "Ciao";
            state = sent;
        } else if (state == sent) {
            //int i =0;
            int pos;
            boolean found = false;

            for (int i = 0; i < domande.length && found == false; i++) {
                for (int j = 0; j < domande[i].length && found == false; j++) {
                    if (theInput.equalsIgnoreCase(domande[i][j])) {
                        found = true;
                        pos = i;
                        theOutput = risposte[i][random.nextInt(risposte[i].length)];
                    }
                }

            }
            if (!found) {
                if (theInput.contains("?")) {
                    theOutput = rds[random.nextInt(rds.length)];
                } else {
                    theOutput = frasirandom[random.nextInt(frasirandom.length)];
                }

            }

        }

        return theOutput;
    }
}
