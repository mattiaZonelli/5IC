
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Fantuzzo Marco
 */
public class Server {

    ServerSocket s;
    Socket conn = null;
    PrintWriter uscita = null;
    BufferedReader ingresso = null;
    String messaggio = "";

    Server() {
        try {
            s = new ServerSocket(9999);
            while (true) {
                conn = s.accept();
                uscita = new PrintWriter(conn.getOutputStream(), true);
                ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while (true) {
                    messaggio = ingresso.readLine();
                    uscita.println("Messaggio letto");
                    if (messaggio == null) {
                        break;
                    }
                    System.out.println(messaggio);
                    uscita.flush();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) throws IOException {
        Server s = new Server();
    }

}
