import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by pool on 07/12/16.
 */
public class DBUtility {
    Connection c;

    public DBUtility(String path) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:./src/" + path);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public synchronized boolean isNameRegistered(String nck){
        String name = "";
        try {
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("SELECT name from User where nickname=\""+nck+"\"");
            name = rs.getString("name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (!name.equals(""))?true:false;
    }

    public synchronized boolean isPasswordCorrect(String passwd){
        String name="";
        try {
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("SELECT name from User where password=\""+passwd+"\"");
            name = rs.getString("name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (!name.equals(""))?true:false;
    }

    public synchronized User getUser(String nck){
        User currentUser = null;
        try {
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * from User where nickname=\""+nck+"\"");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String nickname = rs.getString("nickname");
            currentUser = new User(name,surname,nickname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentUser;
    }

    public synchronized Object[] selectEveryUser(){
        ArrayList<String> users = new ArrayList<>();
        try{
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("select * from User");
            while(rs.next()){
                users.add(rs.getString("nickname"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users.toArray();
    }

    public synchronized User registerUser(String name,String srname,String nckname,String passwd){
        User user = null;
        try{
            Statement stm = c.createStatement();
            int returnCode = stm.executeUpdate("insert into User(\"name\",\"surname\",\"nickname\",\"password\") values(\""+name+"\", \""+srname+"\", \""+nckname+"\", \""+passwd+"\")");
            if(returnCode !=0)
                user= new User(name,srname,nckname);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
        DBUtility p = new DBUtility("Chat.sqlite");
    }

}
