package com.robot.utils.database;

import com.robot.server.Server;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 * Classe che permette la scrittura su database.
 *
 * @author Nicola Pasqualetto
 * @version 1.1
 */
public final class DBWriter {

    /**
     * @see java.sql.Connection
     */
    private final Connection connection;
    /**
     * @see com.robot.utils.database.DBConnector
     */
    private final DBConnector connector;
    /**
     * @see java.sql.Statement
     */
    private Statement statement;

    /**
     * Costruttore
     *
     * @param connector l'oggetto rappresentante la connessione
     */
    public DBWriter(DBConnector connector) {
        this.connector = connector;
        this.connection = connector.getConnection();
        try {
            this.statement = connection.createStatement();
        } catch (SQLException ex) {
            popError();
        }
    }

    /**
     * Svuota una tabella del database
     *
     * @param table la tabella da svuotare
     */
    public void empty(String table) {
        try {
            statement.executeUpdate("DELETE FROM " + table);
            Server.log("Deleting all users data...");
            connection.commit();
        } catch (SQLException ex) {
            popError();
        }
    }

    /**
     * Scrive una tupla sul database
     *
     * @param t la tupla
     * @param table la tabella
     */
    public void write(Tuple t, String table) {
        try {
            String sql1 = "INSERT INTO " + table + " (";
            String sql2 = "VALUES (";
            Iterator i = t.iterator();
            while (i.hasNext()) {
                Field f = (Field) i.next();
                sql1 += f.getName() + ", ";
                sql2 += getStringFormat(f) + ", ";
            }
            sql1 = sql1.substring(0, sql1.length() - 2) + ") ";
            sql2 = sql2.substring(0, sql2.length() - 2) + ");";
            System.out.println(sql1 + sql2);
            statement.executeUpdate(sql1 + sql2);

        } catch (SQLException ex) {
            popError();
        }
        try {
            connection.commit();
        } catch (SQLException ex) {
            popError();
        }
    }

    /**
     * Formatta le stringhe secondo il DDL MySQL
     *
     * @param f il campo da formattare
     * @return il campo formattato
     */
    private static String getStringFormat(Field f) {
        return f.getValue().getClass().getName().equals("java.lang.String") ? "'" + f.getValue().toString() + "'" : f.getValue().toString();
    }

    /**
     * Aggiorna una tupla del database
     *
     * @param valuesToBeModified tupla con valori da modificare
     * @param table la tabella
     * @param condition la condizione da verificare
     */
    public void update(Tuple valuesToBeModified, String table, String condition) {
        String query = "UPDATE " + table + " SET ";
        for (Object o : valuesToBeModified) {
            Field f = (Field) o;
            query += f.getName() + " = " + getStringFormat(f) + ", ";
        }
        query = query.substring(0, query.length() - 2) + " ";
        if (!condition.isEmpty()) {
            query += "WHERE " + condition + " ;";
        }
        System.out.println(query);
        try {
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLException ex) {
            popError();
        }

    }

    /**
     * Visualizza un errore senza bloccare il programma.
     */
    private void popError() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "Error occurred while writing into " + connector.DB_NAME, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        t.start();
    }

}
