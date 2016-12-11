
package chat;

import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {

    Connection c;

    public DBManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:./Chat.sqlite");
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Exception DB");
        }

    }

    public synchronized void addUser(String n) {

          try {
            if(!alreadyIn(n)){
            Statement stm = c.createStatement();
            stm.executeUpdate("INSERT INTO Users (Name) VALUES ('"+n+"')");
            }
        }catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public synchronized boolean alreadyIn(String name) {
        boolean in = false;
        LinkedList<String> l = new LinkedList<>();
        try {
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Name from Users");
            while (rs.next()) {
                l.add(rs.getString("Name"));
            }
            for (int i = 0; i < l.size(); i++) {
                if (l.get(i).equals(name)) {
                    in = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return in;
    }

}
