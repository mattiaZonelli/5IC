package com.database;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe che permette la lettura da database.
 *
 * @author Nicola Pasqualetto
 * @version 1.1
 */
public class DBReader {

    /**
     * Connessione al database.
     */
    private Connection connection;
    /**
     * @see java.sql.Statement
     */
    private Statement statement;
    /**
     * @see java.sql.ResultSet
     */
    private ResultSet queries;

    /**
     */
    private DBConnector connector;

    /**
     * Costruttore
     *
     * @param connector Oggetto rappresentante la connessione
     */
    public DBReader(DBConnector connector) {
        this.connector = connector;
        this.connection = connector.getConnection();
        try {
            this.statement = connection.createStatement();
        } catch (SQLException ex) {
            popError();

        }
    }

    /**
     * Verifica se esiste un campo nel database.
     *
     * @param f campo da verificare
     * @param tableName nome tabella
     * @return se esiste
     */
    public boolean exists(Field f, String tableName) {
        boolean exists = false;
        try {
            String query = "SELECT "+f.getName()+" FROM " + tableName + " WHERE " + f.getName() + " LIKE " + (f.getValue() == null ? "null" : "'"+f.getValue().toString())+"'";
            System.out.println(query);
            queries = statement.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            popError();
        }

        try {
            if (queries!=null && !queries.isClosed()) {
                exists = true;
            }
        } catch (SQLException ex) {
            popError();
        }
        System.out.println("EXISTS: " + exists);
        return exists;
    }

    public boolean exists(Field f, String tableName, String[]andConditions){
        boolean exists = false;
        try {
            String query = "SELECT " +f.getName()+" FROM " + tableName + " WHERE ";
            for(String s:andConditions){
                query+=s+" AND ";
            }
            query=query.substring(0,query.length()-5);
            System.out.println(query);
            queries = statement.executeQuery(query);
        } catch (SQLException ex) {
            popError();
        }
        try {
            if (queries!=null && !queries.isClosed()) {
                exists = true;
            }
        } catch (SQLException ex) {
            popError();
        }
        System.out.println("EXISTS: " + exists);
        return exists;
    }

    /**
     * Legge una tupla dal database.
     *
     * @param t la tupla in cui vanno inseriti i dati
     * @return la tupla completa
     */
    public Tuple read(Tuple t) {
        try {
            queries = statement.executeQuery("SELECT * FROM " + connector.DB_NAME + ";");

            int cnt = 0;
            while (queries.next()) {
                String name = queries.getMetaData().getColumnName(cnt);
                Object value = queries.getObject(cnt).getClass().getName().equals("java.sql.Blob") ? queries.getBlob(cnt) : queries.getObject(cnt);
                cnt++;
                Field f = new Field(name, value);
                t.addField(f);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            popError();
        }
        return t;
    }

    /**
     * Legge dove è verificata la condizione
     *
     * @param t la tupla da riempire
     * @param condition la condizione che deve essere verificata
     * @param table la tabella da cui leggere
     * @return la tupla riempita
     */
    public Tuple read(Tuple t, String condition, String table) {
        try {
            String query = "SELECT * FROM " + table + " WHERE " + condition + " ;";
            System.out.println(query);
            queries = statement.executeQuery(query);
            int cnt = 0;
            int columns = queries.getMetaData().getColumnCount();
            String name;
            Object value;
            while (cnt < columns) {
                name = queries.getMetaData().getColumnName(cnt + 1);
                if (!queries.isClosed()) {
                    value = queries.getObject(cnt+1).getClass().getName().equals("java.sql.Blob") ? queries.getBlob(cnt+1) : queries.getObject(cnt+1);
                } else {
                    value = null;
                }
                cnt++;
                Field f = new Field(name, value);
                t.addField(f);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return t;
    }

    /**
     * Legge una riga a caso
     *
     * @param t la tupla da riempire
     * @param condition la condizione da verificare
     * @param table la tabella da cui leggere
     * @return la tupla riempita
     */
    public Tuple randomRead(Tuple t, String condition, String table) {
        try {
            String query = "SELECT * FROM " + table + " ORDER BY RANDOM() LIMIT 1;";
            System.out.println(query);
            queries = statement.executeQuery(query);
            query = "SELECT * FROM " + table + " WHERE " + condition + ";";
            System.out.println(query);
            queries = statement.executeQuery(query);
            int cnt = 0;
            int columns = queries.getMetaData().getColumnCount();
            String name;
            Object value;
            while (cnt < columns) {
                name = queries.getMetaData().getColumnName(cnt + 1);
                if (!queries.isClosed()) {
                    value = queries.getObject(cnt + 1);
                } else {
                    value = null;
                }
                cnt++;
                Field f = new Field(name, value);
                t.addField(f);
            }

        } catch (SQLException ex) {
            popError();
        }
        return t;
    }

    /**
     * Visualizza un errore di lettura senza bloccare il programma.
     */
    private void popError() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "Error occurred while reading from " + connector.DB_NAME, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        t.start();
    }

}
