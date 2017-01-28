/**
 * Protocollo utilizzato per la comunicazione
 * Created by pool on 25/10/16.
 */
public class Protocol{
    private int state;
    private int lastState;
    String lastInput;
    DBUtility db;
    String extraConds;
    ShoppedItem itemToBeShopped;
    static Object[] interestedItems;
    User currentUser;

    /**
     * Costruttore che crea il protocollo con riferimento al database
     * @param db
     */
    public Protocol(DBUtility db){
        state = ProtocolStates.WELCOME;
        lastState = ProtocolStates.WELCOME;
        lastInput = "";
        extraConds="";
        currentUser = new User();
        this.db = db;
    }


    /**
     * Getter State
     * @return ritorna lo stato del protocollo
     */
    public int getState() {
        return state;
    }

    /**
     * Setter State
     * @param state stato da impostare
     */
    public void setState(int state) {
        this.state = state;
    }


    /**
     * Codifica la risposta
     * @param answer codifica la risposta
     * @return risposta senza \n
     */
    public String encodeAnswer(String answer){
        return answer.replaceAll("\n",""+(char)0);
    }


    /**
     * Analizza l'input
     * @param input inviato dal client
     */
    public void analyzeInput(String input){
        if(lastState == ProtocolStates.ASKCATALOG && input.equalsIgnoreCase("no")){
            setState(ProtocolStates.ASKBYBRAND);
        }else if(getState() == ProtocolStates.ASKQUANTITY)
            itemToBeShopped = new ShoppedItem((String)interestedItems[Integer.parseInt(input)]);
        else if(getState() == ProtocolStates.ASKNAME)
            itemToBeShopped.setQuantity(Integer.parseInt(input));
        else if(getState()==ProtocolStates.ASKSURNAME)
            currentUser.setName(input);
        else if(getState() == ProtocolStates.ASKADDRESS)
            currentUser.setSurname(input);
        else if (getState() == ProtocolStates.SHOWSUMMARY) {
            currentUser.setAddress(input);
            itemToBeShopped.setUser(currentUser);
        }

    }

    /**
     * Ritorna la stringa formattata
     * @return stringa formattata dei risultati della query
     */
    public String printInterestedItems(){
        String answer = "";
        int i = 0;
        for(Object c: interestedItems){
            answer+=i+"\t"+c.toString()+"\n";
            i++;
        }
        answer+=(char)1;
        return answer;
    }

    /**
     * Processa la risposta
     * @param inputtedValue valore inviato dal client
     * @return risposta in base allo stato
     */
    public String answer(String inputtedValue){
        analyzeInput(inputtedValue);
        String answer= "";
        switch (getState()){
            case ProtocolStates.WELCOME: answer+="Benvenuto nel negozio di scarpe specialistiche!"+(char)1;
                break;
            case ProtocolStates.ASKCATALOG: answer+="Vuoi visulaizzare il catalogo?: ";
                break;
            case ProtocolStates.SHOWCATALOG: {
                answer+="Ecco il catalogo disponibile: \n";
                interestedItems = db.showCatalog();
                answer+=printInterestedItems();
                setState(ProtocolStates.SHOWRESULT);
                break;
            }
            case ProtocolStates.ASKBYBRAND: answer+="Inserisci il brand da cercare: ";
                break;
            case ProtocolStates.SHOWRESULT: {
                answer+="Ecco tutte le scarpe del brand scelto: \n";
                interestedItems = db.showByBrand(inputtedValue);
                answer+=printInterestedItems();
                break;
            }
            case ProtocolStates.ASKWHICHMODEL: answer+="Inserisci il numero dei modelli che vuoi scegliere:";
                break;
            case ProtocolStates.ASKQUANTITY: answer+="Inserisci la quantita: ";
                break;
            case ProtocolStates.ASKNAME: answer+="Avrei bisogno del suo nome:";
                break;
            case ProtocolStates.ASKSURNAME: answer+="Anche del suo cognome: ";
                break;
            case ProtocolStates.ASKADDRESS: answer+="Mi servirebbe anche il suo indirizzo: ";
                break;
            case ProtocolStates.SHOWSUMMARY: answer+="Ecco il riassunto dell'acquisto: \n"+itemToBeShopped.toString()+"\nVuoi continuare?";
                break;
            case ProtocolStates.SENDORNOT: answer+=(inputtedValue.equalsIgnoreCase("si"))?"Ok, l'ordine verra spedito!" +(char)2:"Ok l'ordine verra annullato!"+(char)2;
                break;
        }
        lastState = getState();
        setState(getState()+1);
        return encodeAnswer(answer);
    }

}
