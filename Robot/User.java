/**
 * Classe che rappresenta l'utente corrente
 * Created by pool on 17/11/16.
 */
public class User {
    String name;
    String surname;
    String address;

    /**
     * Costruttore default
     */
    public User(){
        super();
    }

    /**
     * Getter nome
     * @return nome
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome
     * @param name nome da impostare
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter surname
     * @return il cognome
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter Surname
     * @param surname cognome da impostare
     */

    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Getter address
     * @return l'indirizzo
     */
    public String getAddress() {
        return address;
    }

    /**
     * Imposta l'indirizzo
     * @param address indirizzo da impostare
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Override toString
     * @return Rappresentazione User sottoforma di Stringa
     */
    @Override
    public String toString() {
        return "\nNome:\t"+getName()+
                "\nCognome:\t"+getSurname()+
                "\nRecapito:\t"+getAddress()+"\n";
    }

}
