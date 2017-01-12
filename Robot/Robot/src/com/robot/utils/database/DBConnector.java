package com.robot.utils.database;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Classe che permette la connessione ad una database.
 *
 * @author Nicola Pasqualetto
 * @version 1.1
 */
public class DBConnector {

    /**
     * L'oggetto rappresentante la connessione.
     */
    private static Connection connection;
    /**
     * Il nome del database.
     */
    public final String DB_NAME;
    /**
     * Dove cercare il database.
     */
    private final String FOLDER_URL;

    /**
     * Costruttore
     *
     * @param dbName nome del database
     */
    public DBConnector(String dbName) {
        Path p = null;
        try {
            p = Paths.get(DBConnector.class.getResource(".").toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        FOLDER_URL = p.getParent().getParent().getParent().getParent().getParent().getParent() + "\\Databases\\";
        DB_NAME = dbName;
        connection = null;
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + FOLDER_URL + dbName + ".sqlite");
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error occurred while connecting with " + dbName, "Error", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("Database " + dbName + " opened");
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Crea una nuova connessione ad un altro database.
     *
     * @param dbName nome database
     * @return connessione
     */
    public static DBConnector change(String dbName) {
        try {
            connection.close();
        } catch (SQLException ex) {
            popError();
        }
        return new DBConnector(dbName);
    }

    /**
     * Termina la connessione.
     */
    public void abort() {
        try {
            connection.abort(Executors.newSingleThreadExecutor());
        } catch (SQLException ex) {
            popError();
        }
    }

    /**
     * Visualizza messaggio di errore senza bloccare esecuzione programma.
     */
    private static void popError() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "Error occurred while closing connection", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        t.start();
    }

    public DBReader newDBReader() {
        return new DBReader(this);
    }

    public DBWriter newDBWriter() {
        return new DBWriter(this);
    }

}
