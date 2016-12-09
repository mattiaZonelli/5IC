package com.database;

import javax.swing.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Iterator;

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
            connection.commit();
        } catch (SQLException ex) {
            popError();
        }
    }

    /**
     * Scrive una tupla sul database
     *
     * @param t     la tupla
     * @param table la tabella
     */
    public void write(Tuple t, String table) {
        try {
            String sql1 = "INSERT INTO " + table + " (";
            String sql2 = "VALUES (";
            Iterator i = t.iterator();
            Field icon = null;
            String username="";
            while (i.hasNext()) {
                Field f = (Field) i.next();
                if (!f.getValue().getClass().getName().equals("java.io.File")) {
                    sql1 += f.getName() + ", ";
                    sql2 += getStringFormat(f) + ", ";
                    if(f.getName().equals("Username")){
                        username=(String)f.getValue();
                    }
                } else {
                    icon = f;
                }
            }
            sql1 = sql1.substring(0, sql1.length() - 2) + ") ";
            sql2 = sql2.substring(0, sql2.length() - 2) + ");";
            System.out.println(sql1 + sql2);
            statement.executeUpdate(sql1 + sql2);
            writeBlob(table,icon,username);
        } catch (SQLException ex) {
            ex.printStackTrace();
            popError();
        }
        try {
            connection.commit();
        } catch (SQLException ex) {
            popError();
        }

    }

    public void writeBlob(String table, Field field,String username) {
        // update sql
        String updateSQL = "UPDATE " + table + " SET " + "Icon = ?  "+ "WHERE Username = ?";
        System.out.println(updateSQL+" "+username);

        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {

            System.out.println(Arrays.toString(readFile((File) field.getValue())));
            pstmt.setBytes(1, readFile((File) field.getValue()));
            pstmt.setString(2,username);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Stored the file in the BLOB column.");


    }

    /**
     * Read the file and returns the byte array
     *
     * @param file
     * @return the bytes of the file
     */
    private byte[] readFile(File file) {
        ByteArrayOutputStream bos = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1; ) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

    /**
     * Formatta le stringhe secondo il DDL MySQL
     *
     * @param f il campo da formattare
     * @return il campo formattato
     */
    private static String getStringFormat(Field f) {
        String value = "";
        if (f.getValue().getClass().getName().equals("java.lang.String")) {
            value = "'" + f.getValue().toString() + "'";
        } else if (f.getValue().getClass().getName().equals("java.io.File")) {
            try {
                System.out.println(((File) f.getValue()).getAbsolutePath());
                BufferedReader br = new BufferedReader(new FileReader(((File) f.getValue()).getAbsolutePath()));
                int a = br.read();
                while (a != -1) {
                    value += a;
                    a = br.read();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            value = f.getValue().toString();
        }
        return value;
    }

    /**
     * Aggiorna una tupla del database
     *
     * @param valuesToBeModified tupla con valori da modificare
     * @param table              la tabella
     * @param condition          la condizione da verificare
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
