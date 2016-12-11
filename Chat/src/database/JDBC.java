package database;

import java.sql.*;
import java.util.ArrayList;

public final class JDBC {

    Connection c = null;
    Statement stmt = null;
    ResultSet result;

    int ID, port;
    boolean find;
    String name, surname, username, password, query;
    
    /**
     * Recupero da database nome e cognome grazie all'ID.
     * 
     * @param port ID  utente.
     * @return 
     */
    public String getNameSurnameByPort(int port){
        String nameSurname = "";
        try {
            result = stmt.executeQuery("SELECT * FROM users;");
            while (result.next()) {
                this.port = result.getInt("port");
                if (port == this.port) {
                    nameSurname = result.getString("name") + " " + result.getString("surname");
                    break;
                }
            }
        } catch (SQLException ex) {
        } 
        return nameSurname;
    }
    
    /**
     * Recupera porta o ID da database grazie all'username.
     * 
     * @param username utente
     * @return 
     */
    public int getPortByUsername(String username){
        int port = 0;
        try {
            result = stmt.executeQuery("SELECT * FROM users;");
            while (result.next()) {
                this.username = result.getString("username");
                if (username.equals(this.username)) {
                    port = result.getInt("port");
                    break;
                }
            }
        } catch (SQLException ex) {
        } 
        return port;
    }
    
    /**
     * Controlla se è possibile loggare l'utente.
     * 
     * @param username utente
     * @param password utente
     * @param port utente ID
     * @return 
     */
    public boolean login(String username, String password, int port) {
        boolean find = false;
        try {
            result = stmt.executeQuery("SELECT * FROM users;");
            while (result.next()) {
                if (result.getString("username").equals(username) && result.getString("password").equals(password)) {
                    updatePort(result.getInt("ID"), port);
                    find = true;
                    break;
                }
            }
        } catch (SQLException ex) {
        }
        return find;
    }

    /**
     * Quando viene il login viene settata la porta.
     * 
     * @param ID 
     * @param port utente
     */
    public void updatePort(int ID, int port) {
        try {
            PreparedStatement updatePort = c.prepareStatement("UPDATE users SET port = ? WHERE ID = ?");

            updatePort.setInt(1, port);
            updatePort.setInt(2, ID);
            updatePort.executeUpdate();
        } catch (SQLException ex) {
        }
    }
    
    /**
     * Recupera tutte le porte o id dal database.
     * 
     * @return ArrayList con tutte le porte o id
     */
    public ArrayList getAllPort() {
        ArrayList ports = new ArrayList();
        try {
            result = stmt.executeQuery("SELECT port FROM users;");
            while (result.next()) {
                String port = (String) (result.getInt("port") + "");
                ports.add(port);
            }
        } catch (SQLException ex) {
        }
        return ports;
    }
    
    /**
     * Controlla se esiste l'username.
     * @param username da controllare
     * @return 
     */
    public boolean checkUsername(String username) {
        boolean find = false;
        try {
            result = stmt.executeQuery("SELECT username FROM users;");
            while (result.next()) {
                this.username = result.getString("username");
                if (username.equals(this.username)) {
                    find = true;
                    break;
                }
            }
        } catch (SQLException ex) {
        }
        return find;
    }
    
    /**
     * Inserisce nel database un utente.
     * 
     * @param name utente
     * @param surname utente
     * @param username utente
     * @param password utente
     * @param port utente
     */
    public void addUser(String name, String surname, String username, String password, int port) {
        try {
            PreparedStatement newUser = c.prepareStatement("INSERT INTO users(name,surname,username,password,port) VALUES(?,?,?,?,?)");

            newUser.setString(1, name);
            newUser.setString(2, surname);
            newUser.setString(3, username);
            newUser.setString(4, password);
            newUser.setInt(5, port);
            newUser.executeUpdate();

        } catch (SQLException ex) {
        }
    }
    
    /**
     * Costruttore della classe che si collega al database.
     */
    public JDBC() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:users.sqlite");
            stmt = c.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            System.exit(1);
        }
    }

    public static void main(String args[]) {
        JDBC db = new JDBC();
    }
}
