/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Nicola
 */
public class DBReader {

    private Connection connection;

    private Statement statement;

    private ResultSet queries;

    private DBConnector connector;

    public DBReader(DBConnector connector) {
        this.connector = connector;
        this.connection = connector.getConnection();
        try {
            this.statement = connection.createStatement();
        } catch (SQLException ex) {
              popError();
            
        }
    }

    public boolean exists(Field f, String tableName) {
        boolean exists = false;
        try {
            String query = "SELECT " + f.getName() + " FROM " + tableName + " WHERE " + f.getName() + " LIKE " + (f.getValue() == null ? "null" : f.getValue().toString());
            System.out.println(query);
            queries = statement.executeQuery(query);
        } catch (SQLException ex) {
           popError();
        }
        try {
            if (!queries.isClosed()) {
                exists = true;
            }
        } catch (SQLException ex) {
          popError();
        }
        System.out.println("EXISTS: " + exists);
        return exists;
    }

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
            popError();
        }
        return t;
    }

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
    
      private void popError(){
         Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                   JOptionPane.showMessageDialog(null, "Error occurred while reading from "+connector.DB_NAME, "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            t.start();
    }

}
