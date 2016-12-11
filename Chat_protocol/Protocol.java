
import java.net.*;
import java.io.*;
import java.util.*;


public class Protocol {

    private int stateMachine = 0;

    public Protocol() {
        this.stateMachine = stateMachine;
    }

    public String name = "";

    private int num = 1;

    private boolean find = false;

    private int cant;

    private String[] appoggio;

    private int state = stateMachine;

    private String scelte = "-Calcio "
            + "-Moda "
            + "-Ragazze "
            + "-Cibo "
            + "-Informatica "
            + "-Social";

    private static Set<String> inizio = new HashSet<String>(Arrays.asList("ehi", "ciao", "hola", "yo"));

    private String[] arg = {"Come va? #3", "Come stai? #3", "Di dove sei? #18", "Come mai stai utlizzando questo programma? #19"};

    private String[] domande = {"ascolti musica? #20", "materia preferita? #21", "colore preferito? #22", "console preferita? #23"};

    private String[] colori = {"rosso #Pregi: sei una persona attiva, carismatica, energetica, estroversa e sensuale. Riesci sempre a farti notare con gli amici e gli sconosciuti grazie alla tua sincerità. Ottimo lavoratore, pieno di idee, hai lo spirito del leader e del trascinatore e non temi i tuoi istinti e risulti diretto e schietto.\n"
        + "Difetti: sei predisposto all’insonnia, non ti fermi a riflettere, troppe idee rimangono a metà.", "arancione #Pregi: sei una persona concreta a cui piace portare a termine i propri progetti, senza aver paura di modificare le proprie idee per riuscire a renderle concrete. Ti prendi cura di te stesso, ma sei anche molto curioso e passionale, senza risultare invasivo e appariscente.\n"
        + "Difetti: il tuo narcisismo può portarti a trascurare le persone che ti sono vicine.", "giallo #Pregi: sei una persona molto dinamica, allegra e brillante nelle relazioni sociali. Sei molto deciso nello scegliere, sei un inguaribile narcisista che predilige sempre l’ottimismo. Molto creativo ama fantasticare su molti progetti nei quali si appassiona per un certo periodo.\n"
        + "Difetti: il tuo narcisismo ti porta a essere troppo sicuro di te e delle tue capacità. Il dinamismo e la voglia di cambiare ti accompagnano anche nella vita di coppia, oltre a minare parte dei tuoi mille progetti.", "verde #Pregi: la stabilità, solidità ed equilibrio sono le tue caratteristiche principali. Hai dei valori molto forti e riusciresti a mantenere le tue convinzioni anche se dovessi rimanere l’unico a pensarla così. Per te le tentazioni non esistono, il tuo essere interiore è equilibrato. Molto bravo nelle cose pratiche.\n"
        + "Difetti: la mancanza di fantasia, il tuo essere un po’ pedante in alcune situazioni sono caratteristiche intrinseche del vostro essere.", "blu #Pregi: sei molto riflessivo su te stesso e gli altri, tranquillo e meditativo. Sei una persona molto sensibile, con una certa timidezza che pensa molto prima di agire, il che ti rende affidabile. Il tuo essere molto attento ai bisogni tuoi e altri ti rende sempre pieno di amici.\n"
        + "Difetti: il tuo essere introverso, passivo e fatalista può portare verso la tristezza e la malinconia. A volte la tua spiritualità ti allontana troppo dal mondo reale.", "viola #Pregi: il colore della creatività, della seduzione e dell’erotismo. Hai una particolare predisposizione per ciò che è bello e affascinante per questo sei portato per lavori inerenti alla moda, arte, web design. Sai vivere i sogni senza perdere di vista la realtà. I tuoi desideri non sono molti e tutti raggiungibili.\n"
        + "Difetti: Se ti lasci andare tendi alla malinconia e a emarginarti. Le novità esistenziali, sia buone che cattive, non fanno per te, ti mettono ansia.", "rosa #Pregi: sei una persona dolce e molto ospitale, che contiene in sé diversi modi di essere (freddezza e passione, dolcezza e aggressività, pigrizia e attivismo…) che spesso si armonizzano tra loro e ti rendono molto ottimista.\n"
        + "Difetti: i diversi modi d’essere altre volte entrano in conflitto.", "marrone #Pregi: nutri una voglia di avere contatto con la natura, di trovare le tue radici sia a livello famigliare che sociale. Sei una persona aperta e disponibile, esprimi sempre emotività e sensualità risultando allo stesso tempo equilibrata e in buona salute. Sei un buon lavoratore e hai ambizioni moderate, che procedono per piccoli passi.\n"
        + "Difetti: puoi essere insoddisfatto, con difficoltà a trovare il tuo spazio e a indirizzare le tue energie verso le giuste persone.", "bianco #Sei una persona con grande larghezza di vedute, capace di sedurre e di mostrarsi sempre al meglio con gli altri. In genere cerchi di accontentare tutti, senza prendere decisioni scomode. Sia che tu sia contro o a favore di tutti cerchi sempre di evitare il conflitto. Hai grandi qualità intellettuali e morali.\n"
        + "Difetti: sei una persona indecisa, chi sceglie il bianco o il nero non ha scelto un vero colore (il nero è assenza di colore, il bianco li contiene tutti). Sei una persona indecisa che non ha ancora trovato il vero sé.", "nero #Sei una persona con grande larghezza di vedute, capace di sedurre e di mostrarsi sempre al meglio con gli altri. In genere cerchi di accontentare tutti, senza prendere decisioni scomode. Sia che tu sia contro o a favore di tutti cerchi sempre di evitare il conflitto. Hai grandi qualità intellettuali e morali.\n"
        + "Difetti: sei una persona indecisa, chi sceglie il bianco o il nero non ha scelto un vero colore (il nero è assenza di colore, il bianco li contiene tutti). Sei una persona indecisa che non ha ancora trovato il vero sé."};

    private static Set<String> good = new HashSet<String>(Arrays.asList("bene", "c'é male", "tutto bene", "tutto apposto", "apposto dai", "bene dai", "benissimo", "superbamente", "al massimo", "benissimo", "molto bene", "va", "va benissimo", "non male", "non male dai"));

    private static Set<String> bad = new HashSet<String>(Arrays.asList("male", "malissimo", "non bene", "insomma", "molto male", "somma", "bene no di certo", "...", "lascia stare", "lascia perdere", "bah"));

    private static Set<String> rispostaComeVa = new HashSet<String>(Arrays.asList("bene", "benissimo", "bene e tu?", "bene, tu?", "bene, tu?", "male", "cosi cosi", "così così", "non così bene", "sto bene", "ok"));

    private static Set<String> squadre = new HashSet<String>(Arrays.asList("Juventus", "juve", "milan", "inter", "napoli", "roma", "lazio", "fiorentina", "torino", "manchester city", "city", "manchester united", "chelsea", "arsenal", "liverpool", "bayern", "real madrid", "borussia dortmund", "borussia", "barcellona", "barca", "atletico madrid", "atletico", "psg", "monaco", "benfica", "porto", "leicester", "schalke 04", "bayer leverkusen", "siviglia"));

    private static Set<String> marche = new HashSet<String>(Arrays.asList("supreme", "palace", "nike", "adidas", "north face", "zara", "imperial"));

    private static Set<String> cellulari = new HashSet<String>(Arrays.asList("lg", "samsung", "nexus", "nokia", "huawei"));

    private static Set<String> piatti = new HashSet<String>(Arrays.asList("pasticcio", "pizza", "pasta", "riso"));

    private static Set<String> generi = new HashSet<String>(Arrays.asList("rock", "rap"));

    private static Set<String> console = new HashSet<String>(Arrays.asList("ps4", "ps3", "ps1", "psp", "nintedo ds", "nintedo 2ds", "nintendo 3ds", "wii", "xbox", "xbox 360", "xbox one"));

    private String[] question = {"chi sei? #sono X", "da dove vieni? #dalla mente del mio creatore", "quanti anni hai? #neanche un anno", "qual'è il tuo colore preferito? #giallo", "che fai? #sto cercando di intraprendere una conversazione", "dove abiti? #a Prozzolo", "di dove sei? #di Prozzolo", "che combini? #niente di bello"};

    private String[] cantantiRock = {"Robert Plant (LED ZEPPELIN)",
        "Freddie Mercury (QUEEN)",
        "Paul Rodgers (FREE, BAD COMPANY)",
        "Ian Gillan (DEEP PURPLE)",
        "Roger Daltrey (THE WHO)",
        "David Coverdale (WHITESNAKE)",
        "Axl Rose (GUNS N’ ROSES)",
        "Bruce Dickinson (IRON MAIDEN)",
        "Mick Jagger (THE ROLLING STONES)",
        "Bon Scott (AC/DC)",
        "David Bowie",
        "Jon Bon Jovi (BON JOVI)",
        "Steven Tyler (AEROSMITH)",
        "Jon Anderson (YES)",
        "Bruce Springsteen",
        "Joe Cocker",
        "Ozzy Osbourne",
        "Bono (U2)",
        "Peter Gabriel",
        "James Hetfield (METALLICA)",
        "Janis Joplin",
        "Chris Cornell (AUDIOSLAVE / SOUNDGARDEN)",
        "Roger Chapman (FAMILY)",
        "Phil Lynott (THIN LIZZY)",
        "Glenn Hughes (DEEP PURPLE)",
        "Steve Perry (JOURNEY)",
        "Jim Morrison (THE DOORS)",
        "Alex Harvey (THE SENSATIONAL ALEX HARVEY BAND)",
        "Rob Halford (JUDAS PRIEST)",
        "Ronnie James Dio (DIO)",
        "Sammy Hagar (VAN HALEN)",
        "Meat Loaf",
        "Alice Cooper",
        "Geddy Lee (RUSH)",
        "Brian Johnson (AC/DC)",
        "David Gilmour (PINK FLOYD)",
        "Fish (MARILLION)",
        "Dave Lee Roth (VAN HALEN)",
        "Biff Byford (SAXON)",
        "Neil Young"};

    private String[] cantantiRap = {"Dr.Dre", "Eminem", "Diddy", "50 cent", "Kenye West", "jay-Z"};

    public String processInput(String insertString) {

        String exitString = "";

        

        if (state == stateMachine) {
            exitString = "Benvenuto nel mio regno straniero";
            state = 1;
        }
        if (state == 1) {
            if (inizio.contains(insertString)) {
                exitString = "come ti chiami straniero?";
                state = 2;
            } else {
                exitString = "non ti hanno insegnato a salutare dalle tue parti?! " + "Quale sarebbe dunque il tuo nome?";
                state = 2;
            }
        }
        if (state == 2) {
            if (insertString.length() > 8) {
                if (insertString.substring(0, 9).equalsIgnoreCase("mi chiamo")) {
                    name = insertString.substring(9, insertString.length());
                }
                if (insertString.substring(0, 13).equalsIgnoreCase("il mio nome è")) {
                    name = insertString.substring(13, insertString.length());
                }
            } else {
                name = insertString;
            }
            exitString = "nel tuo pianeta avete bei nomi!" +  name + " ,piacere di fare la tua amicizia!";
            String s[] = (arg[1 + (int) (Math.random() * arg.length)]).split("#");
            exitString += "/b" + s[0];
            state = Integer.parseInt(s[1]);
        }
        if (state == 3) {
            if (rispostaComeVa.contains(insertString)) {
                if (good.contains(insertString)) {
                    exitString = "sono contento per te " + name;
                    state = 6;
                }
                if (bad.contains(insertString)) {
                    exitString = "come mai?!" + " dai dimmi che hai che ne parliamo..";
                    state = 4;
                }
                if (insertString.contains("e tu?")) {
                    exitString = "Io sono una macchina, non provo emozioni..";
                    state = 6;
                } else {
                    exitString = "se non mi dici come stai mi arrabbio sia chiaro! hai la " + num + " possibilità di dirmelo.. come stai?";
                    num++;
                    state = 3;
                }
            }
        }
        if (state == 4) {
            exitString = "non so proprio cosa farci, verrei a consolarti ma sono dietro uno schermo... fammi pensare.." + "/b" + "Ho molte notizie interessanti che ti tireranno su il morale";
            exitString += scelte;
            exitString += "A te la scelta!";
            state = 5;
        }
        if (state == 5) {
            if (insertString.equalsIgnoreCase("calcio")) {
                exitString = "ho trovato la tua passione,vero?";
                state = 6;
            }
            if (insertString.equalsIgnoreCase("moda")) {
                exitString = "ho trovato la tua passione,vero?";
                state = 7;
            }
            if (insertString.equalsIgnoreCase("ragazze")) {
                exitString = "ho trovato la tua passione,vero?";
                state = 8;
            }
            if (insertString.equalsIgnoreCase("cibo")) {
                exitString = "ho trovato la tua passione,vero?";
                state = 9;
            }
            if (insertString.equalsIgnoreCase("informatica")) {
                exitString = "ho trovato la tua passione,vero?";
                state = 10;
            }
            if (insertString.equalsIgnoreCase("social")) {
                exitString = "ho trovato la tua passione,vero?";
                state = 11;
            } else {
                exitString = "Dopo che ti do una scelta, non ne approffitti.. te le ripropongo : ";
                exitString += scelte;
                state = 5;
            }
        }
        if (state == 6) {
            if (insertString.equalsIgnoreCase("si")) {
                exitString = "quale squadra tifi?";
                state = 12;
            } else {
                exitString = "per quale squadra simpatizzi però?";
                state = 12;
            }
        }
        if (state == 12) {
            if (squadre.contains(insertString)) {
                if (insertString.equalsIgnoreCase("juve") || insertString.equalsIgnoreCase("juventus")) {
                    exitString = "Grande " + name + " è la squadra che preferisco! Dybala & Higuain is on fire!";
                } else {
                    exitString = "Bella squadra non c'è che dire! (inferiore alla vecchia Signora)";
                }
            } else {
                exitString = "Boh.. mi è nuova questa squadra";
            }
            String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
            exitString += "/b" + s[0];
            state = Integer.parseInt(s[1]);
        }
        if (state == 7) {
            if (insertString.equalsIgnoreCase("si")) {
                exitString = "cha marca ti piace vestire?";
                state = 13;
            } else {
                exitString = "avrai una marca preferita però, quale?";
                state = 13;
            }
        }
        if (state == 13) {
            if (marche.contains(insertString)) {
                exitString = "Anche a me piace!";
                String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
                exitString += "/b" + s[0];
                state = Integer.parseInt(s[1]);
            } else {
                exitString = "Dev'essere veramente bella!";
                String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
                exitString += "/b" + s[0];
                state = Integer.parseInt(s[1]);
            }

        }
        if (state == 8) {
            if (insertString.equalsIgnoreCase("si")) {
                exitString = "un po come 3/4 mondo hahaha";
                exitString += "more,bionde o castane?";
                state = 14;
            } else {
                exitString = "gusti son gusti!";
                exitString += "mori, biondi o castani?";
                state = 14;
            }
        }
        if (state == 14) {
            if (insertString.equalsIgnoreCase("more") || insertString.equalsIgnoreCase("bionde") || insertString.equalsIgnoreCase("castane")) {
                exitString = "Abbiamo gli stessi gusti!";
                String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
                exitString += "/b" + s[0];
                state = Integer.parseInt(s[1]);
            } else {
                exitString = "beh, se piacciono a te sono contento!";
                String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
                exitString += "/b" + s[0];
                state = Integer.parseInt(s[1]);
            }

        }
        if (state == 9) {
            if (insertString.equalsIgnoreCase("si")) {
                exitString = "piatto preferito?";
                state = 15;
            } else {
                exitString = "voi umani però dovete mangiare.. cosa ti piace di più tra tutti i cibi?";
                state = 15;
            }
        }
        if (state == 15) {
            if (piatti.contains(insertString)) {
                exitString = "mmmmh ho già l'acquolina in bocca!";
                String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
                exitString += "/b" + s[0];
                state = Integer.parseInt(s[1]);
            } else {
                exitString = "mamma che fame che mi hai fatto venire!";
                String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
                exitString += "/b" + s[0];
                state = Integer.parseInt(s[1]);
            }

        }
        if (state == 10) {
            if (insertString.equalsIgnoreCase("si")) {
                exitString = "che marca di cellulare utilizzi?";
                state = 16;
            } else {
                exitString = "dimmi almeno che marca di cellulare utilizzi..";
                state = 16;
            }
        }
        if (state == 16) {
            if (cellulari.contains(insertString)) {

                exitString = "Vedo che ne capisci molto!";
                String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
                exitString += "/b" + s[0];
                state = Integer.parseInt(s[1]);
            } else {
                exitString = "bell'acquisto " + name;
                String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
                exitString += "/b" + s[0];
                state = Integer.parseInt(s[1]);
            }

        }
        if (state == 11) {
            if (insertString.equalsIgnoreCase("si")) {
                exitString = "che social utilizzi il più del tempo?";
                state = 17;
            } else {
                exitString = "però sui social so che sei presente eheh, dai su dimmi quale usi di più..";
                state = 17;
            }
        }
        if (state == 17) {
            if (insertString.equalsIgnoreCase("facebook")) {
                exitString = "chiedi l'amicizia a questo mio amico : ";
                exitString += "https://www.facebook.com/leonardo.cabianca.7 ";
                String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
                exitString += "/b" + s[0];
                state = Integer.parseInt(s[1]);
            } else if (insertString.equalsIgnoreCase("instagram")) {
                exitString = "segui questo mio amico : _leonardocabianca_ ";
                String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
                exitString += "/b" + s[0];
                state = Integer.parseInt(s[1]);
            } else if (insertString.equalsIgnoreCase("ask")) {
                exitString = "fai un po domande a questo mio amico @LeonardoCabianca(se hai tempo) !";
                String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
                exitString += "/b" + s[0];
                state = Integer.parseInt(s[1]);
            } else if (insertString.equalsIgnoreCase("snapchat")) {
                exitString = " aggiungi questo mio amico : leocabianca1998 !";
                String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
                exitString += "/b" + s[0];
                state = Integer.parseInt(s[1]);

            } else {
                exitString = "non è molto popolare..";
                String s[] = (domande[1 + (int) (Math.random() * domande.length)]).split("#");
                exitString += "/b" + s[0];
                state = Integer.parseInt(s[1]);
            }

        }
        if (state == 18) {
            exitString = "posto molto tranquillo da quello che so!";
            exitString += "fammi pure delle domande " + name;
            state = 40;
        }
        if (state == 19) {
            exitString = "eheh lo sapevo, era una domanda retorica";
            exitString += "su dai chiedimi qualcosa..";
            state = 40;
        }
        if (state == 20) {
            if (insertString.equalsIgnoreCase("si")) {
                exitString = "ahn si?! che genere ascolti?";
                state = 30;
            } else {
                exitString = "vabbene.. scusa.. fammi te delle domande allora..";
                state = 40;
            }
        }
        if (state == 30) {
            if (generi.contains(insertString)) {
                exitString = "ne conosco alcuni che cantano questo genere!";
                exitString += "quanti ne vuoi sapere?";
                state = 31;
            } else if (generi.contains(insertString)) {
                exitString = "ne conosco alcuni che cantano questo genere!";
                exitString += "quanti ne vuoi sapere?";
                state = 32;
            } else {
                exitString = "Hai il tuo stile " + name;
                exitString += "chiedimi qualcosa dai che mi annoio...";
                state = 40;
            }

        }
        if (state == 31) {
            cant = Integer.parseInt(insertString);
            for (int i = 0; i < cant; i++) {
                exitString += cantantiRock[i] + " ";
            }
            exitString += "/b";
            exitString += "ora tocca a te fare domande!";
            state = 40;
        }
        if (state == 32) {
            cant = Integer.parseInt(insertString);
            for (int i = 0; i < cant; i++) {
                exitString += cantantiRap[i] + " ";
            }
            exitString += "/b";
            exitString += "ora tocca a te fare domande!";
            state = 40;
        }
        if (state == 21) {
            exitString = "almeno a te c'è una materia che preferisci haha";
            exitString += "dai su chiedi qualcosa";
            state = 40;
        }
        if (state == 22) {
            for (int i = 0; i < colori.length; i++) {
                if (colori[i].contains(insertString)) {
                    appoggio = colori[i].split("#");
                    find = true;
                }
            }
            if (find == true) {
                exitString = "bello il " + insertString;
                exitString += "il significato del tuo colore è :"
                        + appoggio[1];
                exitString += "prova a farmi qualche domanda te ora!";
            } else {
                exitString = "colore molto particolare!";
                exitString += "prova a farmi qualche domanda te ora!";
                state = 40;
            }
        }
        if (state == 23) {
            if (console.contains(insertString)) {
                exitString = "il mio programma è più potente di quella console eheh";
                exitString += "domandami qualcosa " + name;
                state = 40;
            } else {
                exitString = "mai sentita, sono sicuramente più potente io di quella console eheh";
                exitString += "domandami qualcosa " + name;
                state = 40;
            }
        }
        if (state == 40) {

            boolean trov = false;
            for (int i = 0; i < question.length; i++) {
                if (insertString.equalsIgnoreCase(question[i].substring(0, question[i].indexOf("?")))) {
                    appoggio = question[i].split("#");
                    trov = true;
                }
            }
            if (trov == true) {
                exitString = appoggio[1];
                exitString += "chiedi pure altro! no problem!";
                state = 40;
            } else {
                exitString = "ma ti sembrano domande da fare?!";
                exitString += "chiedi altro dai";
                state = 40;
            }
        }
    return exitString ;

    }
}




