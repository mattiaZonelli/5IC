/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.database;

import com.robot.server.Server;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author Nicola
 */
public final class DBWriter {

    private final Connection connection;

    private final DBConnector connector;

    private Statement statement;

    public DBWriter(DBConnector connector) {
        this.connector = connector;
        this.connection = connector.getConnection();
        try {
            this.statement = connection.createStatement();
        } catch (SQLException ex) {
           popError();
        }
    }

    public void empty(String table) {
        try {
            statement.executeUpdate("DELETE FROM " + table);
            Server.log("Deleting all users data...");
            connection.commit();
        } catch (SQLException ex) {
             popError();
        }
    }

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

    private static String getStringFormat(Field f) {
        return f.getValue().getClass().getName().equals("java.lang.String") ? "'" + f.getValue().toString() + "'" : f.getValue().toString();
    }

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
    
    private void popError(){
         Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(null, "Error occurred while writing into " + connector.DB_NAME, "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            t.start();
    }

}
