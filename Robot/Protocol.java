
/**
 *
 * @author Fabio Manfrin
 */
public class Protocol {

    private static final int SALUTO = 0;
    private static final int PRESENTAZIONE = 1;
    private static final int CATALOGO = 2;
    private static final int SCELTA = 3;
    private static final int PAGAMENTO = 4;
    private static final int COMPLETAMENTO_ACQUISTO = 5;
    private static final int SPEDIZIONE = 6;
    private static final int ARRIVEDERCI = 7;
    private static final int QUIT = 9;
    private static final int TIPO_PAGAMENTO = 10;
    private static final int NUOVO_ORDINE = 11;
    private static final int CONFERMA_NUOVO_ORDINE = 12;

    private int state;
    private int frasecorrente;
    private int importo;

    private String[] frasi = {"Benvenuto in ArgoElectronics",
        "Il seguente è il notro catalogo prodotti, scelga i prodotti che vuole acquistare indicando il codice prodotto.\n (in caso di acquisto multiplo indicare i codici prodotto distanziati con un \"-\" esempio: codice-codice-codice",
        "ecco il catalogo: ",
        "ora può scegliere: w",
        "questo è l'importo da pagare: ",
        "Completare l'acquisto?(y\\n)w",
        "Quale metodo di pagamento scegli:\n"
        + "-Carta di credito\n"
        + "-Conto bancario\n"
        + "-PayPal\n"
        + "inserisci il tuo metodo di pagamento: w",
        "Vuole fare altri acquisti?(y\\n)w",
        "Metodo di pagamento aggiunto\nindirizzo di spedizione: w",
        "Grazie per l'acquisto, Saluti"};

    Protocol() {
        state = SALUTO;
        frasecorrente = 0;
        importo = 0;
    }

    public String controlla(String s) {
        String risposta = null;

        switch (state) {
            case SALUTO:
                risposta = frasi[frasecorrente];
                state = PRESENTAZIONE;
                break;
            case PRESENTAZIONE:
                frasecorrente = 1;
                risposta = frasi[frasecorrente];
                state = CATALOGO;
                break;
            case CATALOGO:
                frasecorrente = 2;
                String catalogo = "";
                Catalogo c = new Catalogo();
                catalogo = c.getCatalogo();
                risposta = frasi[frasecorrente] + "\n" + "----------------------------------------------------" + "\n" + catalogo + "----------------------------------------------------";
                state = SCELTA;
                break;
            case SCELTA:
                frasecorrente = 3;
                risposta = frasi[frasecorrente];
                state = PAGAMENTO;
                break;
            case PAGAMENTO:
                frasecorrente = 4;
                c = new Catalogo();
                String riepilogo = c.getRiepilogo(s);
                importo = c.getConto(s);
                if (importo == 0) {
                    risposta = "Scelta non valida";
                    state = CATALOGO;
                } else {
                    risposta = "Riepilogo ordine: " + "\n" + riepilogo + frasi[frasecorrente] + " " + importo + " €" + "\n" + frasi[5];// 5 = frasecorrente +1
                    state = COMPLETAMENTO_ACQUISTO;
                }
                break;
            case COMPLETAMENTO_ACQUISTO:
                if (s.equalsIgnoreCase("y")) {
                    state = TIPO_PAGAMENTO;
                    risposta = "Acquisto: ";
                } else {
                    state = NUOVO_ORDINE;
                    risposta = "Ordine annullato";
                }

                break;
            case TIPO_PAGAMENTO:
                frasecorrente = 6;
                risposta = frasi[frasecorrente];
                state = SPEDIZIONE;
                break;
            case NUOVO_ORDINE:
                frasecorrente = 7;
                risposta = frasi[frasecorrente];
                state = CONFERMA_NUOVO_ORDINE;
                break;
            case CONFERMA_NUOVO_ORDINE:
                if (s.equalsIgnoreCase("y")) {
                    risposta = frasi[1];
                    state = CATALOGO;
                } else {
                    risposta = "Arrivederci";
                    state = QUIT;
                }
                break;
            case SPEDIZIONE:
                frasecorrente = 8;
                risposta = frasi[frasecorrente];
                state = ARRIVEDERCI;
                break;
            case ARRIVEDERCI:
                frasecorrente = 9;
                risposta = frasi[frasecorrente];
                state = NUOVO_ORDINE;
                break;
            case QUIT:
                risposta = "quit";
                break;
        }
        return risposta;
    }

}
