
import java.util.LinkedList;

// Fantuzzo Marco 5IC
public class Protocol {

    String output = null;

    private static int SALUTI;
    private static int NOMEROBOT;
    private static int NOMECLIENT;
    private static int OCCUPAZIONE;
    private static int SPORT;
    private static int HOBBY;
    private static int FINE;
    private static int SALVA;

    LinkedList<String> lavori = new LinkedList<>();
    
    String[] sportList = new String[]{"calcio", "atletica", "rugby", "pallavolo", "pallamano",
        "palestra", "tennis", "zapping"};
    String[] listaLavori = new String[]{"impiegato", "dirigente", "idraulico", "atleta", "elettrecista",
        "contabile", "infermiere", "dottore", "studente"};
    String[] listaHobby = new String[]{"paracadutismo", "leggere", "politica", "sport", "canto",
        "palestra", "tennis", "zapping"};

    public int state = SALUTI;
    public boolean find;
   // public boolean findSport;
    //public boolean findHobby;

    public Protocol() {
        SALUTI = 0;
        NOMEROBOT = 1;
        NOMECLIENT = 2;
        OCCUPAZIONE = 3;
        SPORT = 4;
        HOBBY = 5;
        FINE = 6;
        SALVA = 7;
        find = false;
    }


    public String Conversazione(String input) {
        String nomeOccupazione = "";

        if (state == SALUTI) {
            output = "BUON POMERIGGIO";
            state = NOMEROBOT;
        } else if (state == NOMEROBOT) {
            if (input.equalsIgnoreCase("chi sei?")) {
                output = "Il mio nome è Cortana. Tu come ti chiami?";
                state = NOMECLIENT;
            } else {
                output = "Suppongo tu voglia chiedermi: chi sei?. Riprova!";
            }
        } else if (state == NOMECLIENT) {
            if (input != null) {
                output = "Che bel nome, un nome veramente stupendo. Che impiego hai?";
                state = OCCUPAZIONE;
            } else {
                output = "Scrivi il tuo nome perpiacere...";
            }
        } else if (state == OCCUPAZIONE) {
            for (int i = 0; i < listaLavori.length; i++) {
                if (input.equalsIgnoreCase(listaLavori[i])) {
                    output = "Gran bel lavoro " + listaLavori[i] + ". Quale sport pratichi?";
                    state = SPORT;
                    find = true;
                }
            }
            if (find == false) {
                output = "non conosco la tua professione... interessante. Vuoi che l'aggiunga alla lista delle professioni?";
                state = SALVA;
            }
        } else if (state == SALVA) {
            if (input.equalsIgnoreCase("si")) {
                lavori.add(input);
                output = "Ho aggiunto alla mia lista" + nomeOccupazione + ". Quale sport pratichi?";
            } else {
                output = "va bene, non salvo la tua professione nel database... Quale sport pratichi?";
            }
            state = SPORT;
        } else if (state == SPORT) {
            for (int i = 0; i < sportList.length; i++) {
                if (input.equalsIgnoreCase(sportList[i])) {
                    output = "Pure a me piace " + sportList[i] + ". Io invece non pratico sport, sono troppo pigro ahah. Hai qualche interesse in particolare? Quale? Altrimenti digita :no";
                    find = true;
                }
            }
            if (find == false) {
                output = "wow, non ho mai praticato" + input + ", molto interessante. Non si finisce mai di imparare. \"Hai qualche interesse in particolare? Quale? Altrimenti digita :no";
            }
            state = HOBBY;
        } else if (state == HOBBY) {
            for (int i = 0; i < listaHobby.length; i++) {
                if (input.equalsIgnoreCase(listaHobby[i])) {
                    output = listaHobby[i] + "? Emozionante. E' stato bello conoscerti. Per terminare 'fine, altrimenti 'nuovo'";
                    find = true;
                }
            }
            if (find == false) {
                output = "Interessante, non lo conoscevo. E' stato bello conoscerti. A prestooo. Se vuoi terminare il dialogo scrivi 'fine', altrimenti digita 'nuovo'";
            }
            state = FINE;
        } else if (state == FINE) {
            if (input.equalsIgnoreCase("nuovo")) {
                state = SALUTI;
            }
        }
        find = false;
        return output;
    }

}
