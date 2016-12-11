/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myknockknock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Serverkk {

    Serverkk() throws IOException {
        ServerSocket ss = new ServerSocket(9999);
        System.out.println("Server Started");
        int ID = 0;
        while (true) {
            Thread t = new Thread(new User(ss.accept(), ID++));
            t.start();
        }
    }

    public static void main(String[] args) throws IOException {
        Serverkk s = new Serverkk();
    }

    public class User implements Runnable {

        Socket s;
        String mess = "";
        PrintWriter uscita;
        BufferedReader br;
        int ID;

        public User(Socket s, int ID) {
            this.s = s;
            this.ID = ID;
        }

        @Override
        public void run() {
            try {
                uscita = new PrintWriter(s.getOutputStream(), true);
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String outputLine;

                Protocolkk kkp = new Protocolkk();
                outputLine = kkp.processInput(null);
                
                uscita.println(outputLine);
                
                
                while ((mess = br.readLine()) != null ) {
                    //mess = br.readLine();
                    System.out.println("Client[" + ID + "]: " + mess);
                    outputLine = kkp.processInput(mess);
                    uscita.println(outputLine);
                    if (mess == null) {
                        break;
                    }

                }
            } catch (IOException ex) {
            }

        }

    }
}
