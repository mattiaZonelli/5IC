import java.util.Scanner;

/**
 * Rappresenta l'oggetto da comprare
 * Created by pool on 17/11/16.
 */
public class ShoppedItem {
    String model;
    String brand;
    double cost;
    User user;
    int quantity;

    /**
     * Costruttore default
     */
    public ShoppedItem() {
    }

    /**
     * Costruttore tramite i dati presi da una stringa
     * @param data dati oggetto
     */
    public ShoppedItem(String data){
        String[]  splittedData = data.split("\t");
        model = splittedData[0];
        brand = splittedData[1];
        cost = Double.parseDouble(splittedData[2]);
    }

    /**
     * Getter Model
     * @return modello oggetto
     */

    public String getModel() {
        return model;
    }

    /**
     * Setter modello
     * @param model modello oggetto
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Getter brand
     * @return marca oggetto
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Setter brand
     * @param brand marca da impostare
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Getter cost
     * @return costo modello
     */
    public double getCost() {
        return cost;
    }

    /**
     * Setter cost
     * @param cost imposta il costo
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Getter User
     * @return Utente corrente
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter User
     * @param user Utente da settare
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter quantity
     * @return quantita oggetti
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter quantity
     * @param quantity imposta la quantita
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getQueryString(){
        return "INSERT INTO Shopped(model, brand, cost, user, quantity) VALUES(\""+getModel()+"\","+getBrand()+", "+getCost()+", "+getQuantity()+")";
    }

    /**
     * Override toString
     * @return lo "Scontrino"
     */
    @Override
    public String toString() {
        return "============================================="+
                "\nModello: "+getModel()+
                "\nMarca: "+getBrand()+
                "\nQuantita: "+getQuantity()+
                "\n--------------------------------------------"+
                "\nDestinatario:"+getUser()+
                "\n--------------------------------------------"+
                "\n============================================="
                +"\nTOTALE: "+getCost()*getQuantity();
    }
}
