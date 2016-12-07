package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Swapnil Paul
 */
public class ConnectionDB {

    private static final String NAME_DB = "C:\\Users\\Swapnil\\Desktop\\scuola\\5ic\\TPSIT\\UsbTPSITremoto\\ChatRoom\\chatroomDB.db";
    private static final String PATH_DB = "database\\chatroomDB.db";
    private static Connection conn = null;

    /**
     *
     * @return connection
     */
    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_DB);
            } catch (ClassNotFoundException | SQLException ex) {
                conn = null;
            }
        }
        return conn;
    }

    /**
     *
     */
    public static void closeConnection() {
        try {
            conn.close();
            conn = null;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param username
     * @param psw
     */
    public static void insertUser(String username, String psw) {
        Connection conn = ConnectionDB.getConnection();
        String query = "INSERT INTO Users(username,password)" + "VALUES(?,?)";
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, psw);
            st.executeUpdate();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
