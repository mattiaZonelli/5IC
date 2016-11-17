/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. This software is created by Swapnil Paul
 */
package robotgraphics;

import java.util.*;

/**
 *
 * @author Swapnil Paul
 */
public class Protocol {

    private int state;

    /**
     *
     */
    public Protocol() {
        this.state = 0;
    }

    private static String name = "";

    private static Set<String> saluto = new HashSet<String>(Arrays.asList(
            "ciao", "buongiorno", "hey", "hi", "hola", "hey la", "buongiornissimo"
    ));
    private static Set<String> rispostaComeVa = new HashSet<String>(Arrays.asList(
            "bene", "benissimo", "bene e tu?", "bene, tu?", "bene, tu?", "male", "cosi cosi", "così così", "non così bene", "sto bene", "ok"
    ));

    private static Set<String> sport = new HashSet<String>(Arrays.asList(
            "basket", "calcio", "pallavolo", "pallamano", "pallacanestro", "tennis", "golf", "judo", "kick boxing", "ping pong",
            "nuoto", "rugby", "football", "cricket", "badminton"
    ));
    private static Set<String> musica = new HashSet<String>(Arrays.asList(
            "house", "tecno", "electro", "metal", "heavy metal", "rock", "hard rock"
    ));

    private static Set<String> film = new HashSet<String>(Arrays.asList(
            "horror", "fantascienza", "comico", "romantico", "azione", "storico"
    ));

    private String[] hobby = {"Pratichi qualche sport? #14", "Che tipo di musica ti piace? #16", "Che genere di film di piacciono? #17"
    };

    private String[][] titoliFilm = {{"Troy", "Alatriste", "Marie Antoinette", "King Arthur"}, {"The Exorcist", "The Shining", "The Ring"},
    {"The Notebook", "Silver Linings Playbook", "The Holiday"}, {"Spectre", "Mission: Impossible – Rogue Nation", "Skyfall"},
    {"Ace Ventura: Pet Detective", "Fantozzi", "What a Beautiful Day"}, {"Batman v Superman: Dawn of Justice", "Interstellar", "Inception"}};

    private String[] arg = {"Come ti chiami? #2", "Come va? #3", "Come stai? #3",
        "Di dove sei? #7", "Come mai stai utlizzando questo programma? #12"};
    private String[] barzeletta = {"Perchè è comparso l'uomo sulla terra?§Perchè se l'uomo compariva sull'acqua, affogava!",
        "Telefonata al tecnico di computer: \"c'è un altro modo per spegnere il computer? §perchè se premo arresta il sistema poi arrivano i carabinieri e me lo portano via?\"",
        "Un bambino sta giocando al computer e la madre, durante le sue pulizie della casa dice: §\"ragazzo mio te l'ho sempre detto che prima di navigare in internet ti devi mettere il salvagente\"",};

    private String[] askAI = {"Qual'è lo scopo della vita? #Vivere per sempre", "Dove ti trovi adesso? #Sono nel bel mezzo del nulla.", "Qual'è lo scopo di morire? #Avere una vita",
        "Qual'è lo scopo delle emozioni #Non lo so sono una macchina!", "Cos'è l'altruismo? #Se non credi in Dio non potrai mai saperlo!",
        "Cosa stai facendo adesso? #Sto cercando di capire il senso di questi zeri e uno"};

    private static boolean thanksChk = false;
    private static int hobbyChk = 0;
    private static int countMovie = 0;

    /**
     *
     * @param input
     * @return
     */
    public synchronized String processInput(String input) {
        String output = "";
        if (state == 0) {
            output = "Ciao, sono Genesis!";
            state = 1;
        } else if (state == 1) {
            if (saluto.contains(input)) {
                String s[] = (arg[(int) (Math.random() * arg.length)]).split("#");
                output = s[0];
                state = Integer.parseInt(s[1]);
            } else {
                output = "Che c'è non mi saluti! Come ti chiami?";
                state = 2;
            }
        } else if (state == 2) {
            if (input.length() > 8) {
                if (input.substring(0, 9).equalsIgnoreCase("Mi chiamo")) {
                    name = input.substring(9, input.length());
                }
                if (input.substring(0, 13).equalsIgnoreCase("Il mio nome è")) {
                    name = input.substring(13, input.length());
                }
            } else {
                name = input;
            }
            output = "Wow " + name + ", che bel nome!";
            String s[] = (arg[1 + (int) (Math.random() * arg.length)]).split("#");
            output += "§" + s[0];
            state = Integer.parseInt(s[1]);
        } else if (state == 3) {  //come stai?
            if (rispostaComeVa.contains(input)) {
                if (input.equals("bene") || input.equals("sto bene") || input.equals("benissimo")) {
                    output = "Mi fa piacere, che stai bene!";
                    state = 6;
                }
                if (input.equals("bene e tu?") || input.equals("bene, tu?")) {
                    output = "Anch'io sto bene, grazie!";
                    state = 6;
                }
                if (input.equals("male") || input.equals("così così") || input.equals("cosi cosi") || input.equals("non così bene")) {
                    output = "Perchè cos'è successo? Racconta?";
                    state = 4;
                }
            } else {
                output = "Non riesco a capire come stai?";
                //rispostaComeVa.add(input);
                output = "Non riesco a capire, quello che hai appena scritto e una sesazione positiva o negativa?";
                state = 5;
            }
        } else if (state == 4) { //sottostato di come stai? Risposta male -> state =3
            output = "ooh mi dispiace, che triste sta storia ): Vuoi sentire una barzeletta?";
            state = 10;
        } else if (state == 5) { // sottostato di Non riesco a capire come stai? st=3
            if (input.equalsIgnoreCase("negativa") || input.equalsIgnoreCase("neg")) {
                output = "ooh, allora mi dispiace!§ Dai ti tiro su il morale con un barzelleta, Vuoi sentirla?";
                state = 10;
            } else if (input.equalsIgnoreCase("positiva") || input.equalsIgnoreCase("pos")) {
                output = "wow, sono felice per te!§Ti va di sentire una barzeletta?";
                state = 10;
            } else {
                output = "Non riesco a capire, quello che hai appena scritto e una sesazione positiva o negativa?";
                state = 5;
            }
        } else if (state == 6) {
            if (thanksChk) {
                output = "Se non mi ringrazi non mi muovo da qui!";
                state = 6;
            }
            if (thanksChk == false) {
                output = "Anche se sono solo una macchina dovresti comunque rigraziarmi";
                thanksChk = true;
                state = 6;
            }
            if (input.equalsIgnoreCase("grazie") || input.equalsIgnoreCase("Ti ringrazio")) {
                output = "Di nulla " + name + "§Vuoi sentire una barzeletta?";
                thanksChk = false;
                state = 10;
            }
        } else if (state == 7) { // Di dove sei?
            output = "ooh wow, visto che sono una ignorante, il luogo che hai appena nominato è una nazione, una regione o una città?";
            state = 8;
        } else if (state == 8) { //sottostato di 7 
            if (input.equalsIgnoreCase("nazione") || input.equalsIgnoreCase("naz")) {
                output = "Mi piace il nome di questa nazione!§Come vanno le giornate?";
                state = 3;
            } else if (input.equalsIgnoreCase("regione") || input.equalsIgnoreCase("reg")) {
                output = "wow, questa regione di quale stato appartiene?";
                state = 9;
            } else if (input.equalsIgnoreCase("citta") || input.equalsIgnoreCase("città")) {
                output = "wow belissima città, assolutamente da vedere...§Come stanno andando le tue giornate?";
                state = 3;
            } else {
                output = "Non riesco a capire quello che stai dicendo!§Perchè non mi dici come vanno le tue giornate?";
                state = 3;
            }
        } else if (state == 9) { //sottostato di 8
            output = "hmm, bello!§Come vanno le giornate?";
            state = 3;
        } else if (state == 10) { //sottostato di vuoi una barzeletta? state = 4,5
            if (input.equalsIgnoreCase("si") || input.equalsIgnoreCase("va bene") || input.equalsIgnoreCase("yes")) {
                output = barzeletta[(int) (Math.random() * barzeletta.length)];
                output += "§L'avevi già sentita?";
                state = 11;
            } else if (input.equalsIgnoreCase("no")) {
                output = "Va bene parliano di altro, per esempio dei tuoi hobby?";
                state = 13;
            } else {
                output = "Non riesco a capire la tua risposta!§Potresti rispondere alla mia domanda pure con un semplice si o no.";
                state = 10;
            }
        } else if (state == 11) {//sottostato di 10
            if (input.equalsIgnoreCase("si") || input.equalsIgnoreCase("yes")) {
                output = "Ne ero sicuro, anche perchè è parecchio famosa. Ne vuoi sentire un'altra?";
                state = 10;
            } else if (input.equalsIgnoreCase("no")) {
                output = "Hmmm forse la conosco solo io!§Perchè non cambiamo argomento, parliamo dei tuoi hobby, va bene?";
                state = 13;
            } else {
                output = "Non riesco a capire la tua risposta!§Potresti rispondere alla mia domanda pure con un semplice si o no.";
                state = 11;
            }
        } else if (state == 12) {
            output = "Ooh ti capisco!§Come vanno le giornate?"; //§Come ti chiami?
            state = 3;
        } else if (state == 13) { //HOBBY   Vuoi parlami dei tuoi hobby?
            if (input.equalsIgnoreCase("si") || input.equalsIgnoreCase("ok") || input.equalsIgnoreCase("va bene")) {
                hobbyChk++;
                String s[] = (hobby[(int) (Math.random() * hobby.length)]).split("#");
                output = s[0];
                state = Integer.parseInt(s[1]);
            } else if (input.equalsIgnoreCase("no")) {
                output = "Se vuoi puoi farmi delle domande."
                        + "§Però ti devo avvertire che la mia intelligenza è ancora in fase di sviluppo."
                        + "§Desideri vedere a quali domande posso risponderti?";
                state = 19;
            } else {
                output = "Non riesco a capire la tua risposta!§Potresti rispondere alla mia domanda pure con un semplice si o no.";
                state = 13;
            }
        } else if (state == 14) { //sottostato sport hooby[0]
            if (input.equalsIgnoreCase("si") || input.equalsIgnoreCase("ok") || input.equalsIgnoreCase("va bene")) {
                output = "Davvero! Quale?";
                state = 15;
            } else if (input.equalsIgnoreCase("no")) {
                String s[] = hobby[1].split("#");
                output = s[0];
                state = Integer.parseInt(s[1]);
            } else {
                output = "Non riesco a capire la tua risposta!§Potresti rispondere alla mia domanda pure con un semplice si o no.";
                state = 14;
            }
        } else if (state == 15) { //sottostato lv2 sport hobby[0] - Davvero! Quale? state = 14
            String s[] = hobby[1].split("#");
            if (sport.contains(input)) {
                output = "Wow pure io conosco il " + input + ", e ne sono pure appassionato§" + s[0];
                state = Integer.parseInt(s[1]);
            } else {
                output = "Mi dispiace ma non conosco il " + input + "§" + s[0];
                state = Integer.parseInt(s[1]);
            }
        } else if (state == 16) { //musica hobby[1]
            String s[] = hobby[2].split("#");
            if (musica.contains(input)) {
                output = "Sinceramente a me non piace questo genere di musica che ascolti! § Mi appasiona molto la musica classica, jazz, e hip-pop."
                        + "§" + s[0]; //s[0] che film ti piacciono
                state = Integer.parseInt(s[1]); //state = 17
            } else {
                output = "Davvero pure io avvolte ascolto " + input + "§" + s[0];
                state = Integer.parseInt(s[1]); //film state = 17
            }
        } else if (state == 17) { //Che genere di film ti piacciono?
            String ar[] = film.toArray(new String[film.size()]);
            int n = 0;

            if (film.contains(input)) {
                for (int i = 0; i < ar.length; i++) {
                    if (ar[i].indexOf(input) == 0) {
                        n = i;
                        break;
                    }
                }
                output = "A me piacciono questi film del genere " + input + ": ";
                for (int i = 0; i < titoliFilm[n].length; i++) {
                    output += titoliFilm[n][i] + ", ";
                }
                output += "§Invece a te quali ti piacciono sempre di questo genere?";
                state = 18;
            } else if (countMovie < 1) {
                output = "Non conosco questo genere di film, " + name + "§Altri tipi di film che ti piaccioni?";
                countMovie++;
                state = 17;
            } else {
                output = "Se vuoi puoi farmi delle domande."
                        + "§Però ti devo avvertire che la mia intelligenza è ancora in fase di sviluppo."
                        + "§Desideri vedere a quali domande posso risponderti?";
                state = 19;
            }
        } else if (state == 18) {//sottostato di sst = 17
            output = "woow non mi ricordo se l'ho visto, ma ho sentito dire che è un bel film!§Altri generi di film che ti piaciono?";
            state = 17;
        } else if (state == 19) { // domande a gensis
            int n = 0;
            if (input.equalsIgnoreCase("si") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("ok") || input.equalsIgnoreCase("va bene")) {
                for (int i = 0; i < askAI.length; i++) {
                    String st[] = askAI[i].split("#");
                    output += i + " - " + st[0] + "§";
                }
                n++;
                output += "Puoi direttamente scegliere il numero, al posto di riscrivere la domanda!";
                state = 20;
            } else if (input.equalsIgnoreCase("no")) {
                if (hobbyChk == 0) {
                    if (n == 0) {
                        output = "Allora parliamo per forza dei tuoi hobby, va bene?";
                        state = 13;
                    } else {
                        output = "Tu mi hai fatto molte domande, però io non so nulla di te.§Quindi parliamo dei tuoi hobby?";
                        state = 13;
                    }
                } else if (hobbyChk > 0) {
                    output = "chIUdi";
                    output = "Scusami ma adesso sono molto stanco, quindi mi sa che andrò a riposarmi Ciaooo!";
                    state = 123;
                }
            }
        } else if (state == 20) {
            if (input.length() > 2) {
                output = "Puoi direttamente scegliere il numero, al posto di riscrivere la domanda!";
                state = 20;
            }
            if (Integer.parseInt(input) > askAI.length - 1) {
                output = "Il numero per la scelta della domanda è sbagliata!§Riprova e sarai fortunato :) ahahha";
                state = 20;
            } else {
                int n = Integer.parseInt(input);
                String st[] = askAI[n].split("#");
                output = st[1] + "§Vuoi chiedermi altro?";
                state = 19;
            }
        } else if (state == 123) {
            output = "Genesis sta dormendo non disturbarlo! zzzz... zzz.. zzzz...";
            state = 123;
        }
        return output;
    }
}
