/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.database;

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
 *
 * @author Nicola
 */
public class DBConnector {

    private static Connection connection;

    public final String DB_NAME;

    private final String FOLDER_URL;

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
            JOptionPane.showMessageDialog(null, "Error occurred while connecting with "+dbName, "Error", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("Database " + dbName + " opened");
    }

    public Connection getConnection() {
        return connection;
    }
    
    public static DBConnector change(String dbName){
        try {
            connection.close();
        } catch (SQLException ex) {
              popError();
        }
        return new DBConnector(dbName);
    }

    public void abort() {
        try {
            connection.abort(Executors.newSingleThreadExecutor());
        } catch (SQLException ex) {
            popError();
        }
    }
    
    private static void popError(){
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
