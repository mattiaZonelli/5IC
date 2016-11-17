
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabio Manfrin
 */
public class Catalogo {

    private File f;
    Scanner input;
    LinkedList<Prodotto> l;

    Catalogo() {
        l = new LinkedList();
        f = new File("CATALOGO.txt");
        String s = "";
        try {
            input = new Scanner(f);
            while (input.hasNextLine()) {
                s = input.nextLine();
                Prodotto p = new Prodotto(s);
                l.add(p);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Catalogo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private class Prodotto {

        private String CODICE;
        private String PRODOTTO;
        private int COSTO;

        Prodotto(String s) {
            int c = 0;
            String str = "";
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '\t') {
                    switch (c) {
                        case 0:
                            CODICE = str;
                            break;
                        case 1:
                            PRODOTTO = str;
                            break;
                    }
                    str = "";
                    c++;
                } else {
                    str = str + s.charAt(i);
                }
            }
            COSTO = Integer.parseInt(str);

        }

        String getCodice() {
            return CODICE;
        }

        String getProdotto() {
            return PRODOTTO;
        }

        int getCosto() {
            return COSTO;
        }
    }

    String getCatalogo() {      //restituisce il catalogo dal file di testo formattato in una stringa
        String s = "";
        for (int i = 0; i < l.size(); i++) {
            s = s + l.get(i).getCodice() + " " + l.get(i).getProdotto() + " " + l.get(i).getCosto() + "\n";
        }
        return s;
    }

    int getConto(String s) { //restituisce la somma dei prezzi dell'ordine
        s = s + "-";
        int conto = 0;
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '-') {
                int j = 0;
                while (j < l.size()) {
                    if (l.get(j).getCodice().equalsIgnoreCase(str)) {
                        conto = conto + l.get(j).getCosto();
                        str = "";
                    } else {
                        j++;
                    }
                }
                str = "";
            } else {
                str = str + s.charAt(i);
            }
        }
        return conto;
    }

    String getRiepilogo(String s) { //restituisce in ordine i prodotti scelti
        s = s + "-";
        String r = "";
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '-') {
                int j = 0;
                while (j < l.size()) {

                    if (l.get(j).getCodice().equalsIgnoreCase(str)) {
                        r = r + l.get(j).getCodice() + "\t" + l.get(j).getProdotto() + "\t" + l.get(j).getCosto() + " €" + "\n";
                        str = "";
                    } else {
                        j++;
                    }
                }
                str = "";
            } else {
                str = str + s.charAt(i);
            }
        }
        return r;
    }

}
