package robot;

import java.util.Random;
import java.io.*;

public class Protocol {

    private static final int waiting = 0;
    private static final int sent = 1;
    private static final int doing = 2;
    private static int passion = 3;
    private static int exit = 4;
    private int state = waiting;
    private final String[][] chatClient = {{"Chi sei?", "Come ti chiami?"}, {"Come stai?", "Come va?"}, {"Ciao", "Ehy", "Salve", "Buongiorno", "Buonasera", "Buon pomeriggio"}, {"Bene e tu?", "Male e tu?", "Bene te?", "Male te?"}, {"Bene"}, {"Male"}};
    private final String[][] chatServer = {{"Nessuno", "Il tuo incubo peggiore", "Non ho nome", "Non ti deve interessare"}, {"Bene", "Male", "Tutto bene", "Bene grazie", "Male", "Tutto bene grazie", "Così così", "Così, così"}, {"Come stai?", "Come va?"}, {"Bene grazie", "Male", "Tutto bene grazie", "Così così", "Così, così"}, {"Mi fa piacere"}, {"Mi dispiace"}};
    private final String[] serverIsDoing = {"Non ti interessa cosa faccio io", "Io ti rispondo", "Nulla che ti interessa", "Io rispondo alle tue domande"};
    private final String[] serverReaction = {"Bello!", "Divertente!", "Forte!"};
    private final String[] serverPassion = {"aggiornarmi", "dormire", "cantare"};
    
    public String processInput(String theInput) {
        String theOutput = null;

        Random random = new Random();

        if (state == waiting) {
            theOutput = "Ehy";
            state = sent;
        } else if (state == sent) {
            int pos;
            boolean found = false;

            for (int i = 0; i < chatClient.length && found == false; i++) {
                for (int y = 0; y < chatClient[i].length && found == false; y++) {
                    if (theInput.equalsIgnoreCase(chatClient[i][y])) {
                        found = true;
                        pos = i;
                        theOutput = chatServer[i][random.nextInt(chatServer[i].length)];
                        if (pos == 1 || pos >= 3 && pos <= 5) {
                            state = doing;
                            theOutput = chatServer[i][random.nextInt(chatServer[i].length)] + ". Cosa fai?";

                        }
                    }
                }

            }

            if (found == false) {
                theOutput = "non ho capito cosa intendi";
            }
        } else if (state == doing) {

            if (theInput.substring(theInput.length() - 3).equals("tu?") || theInput.substring(theInput.length() - 3).equals("te?")) {

                theOutput = serverReaction[random.nextInt(serverReaction.length)]+ " " + serverIsDoing[random.nextInt(serverIsDoing.length)]+" Cosa ti piace fare nel tempo libero?";

            } else {
                theOutput = serverReaction[random.nextInt(serverReaction.length)]+" Cosa ti piace fare nel tempo libero?";
            }
            state = passion;
        } else if (state == passion) {

            if (theInput.substring(theInput.length() - 3).equals("tu?") || theInput.substring(theInput.length() - 3).equals("te?")) {

                theOutput = serverReaction[random.nextInt(serverReaction.length)]+ " A me piace " + serverPassion[random.nextInt(serverPassion.length)]+". Arrivederci alla prossima è stato bello parlare con te";
                
            } else {
                theOutput = serverReaction[random.nextInt(serverReaction.length)]+" Arrivederci alla prossima è stato bello parlare con te";
                
            }
            state = exit;
        }else if (state == exit){
            System.exit(1);
        }
        
        

        
        return theOutput;
    }
}
