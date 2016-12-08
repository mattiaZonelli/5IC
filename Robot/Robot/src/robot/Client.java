package robot;

import java.io.*;
import java.net.*;

public class Client {

    Socket con;
    BufferedReader br;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }

    public void run() throws IOException {

        String hostName;
        int portNumber;

        hostName = "localhost";
        portNumber = 9999;

        try (
                Socket connessione = new Socket(hostName, portNumber);
                PrintWriter uscita = new PrintWriter(connessione.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connessione.getInputStream()));) {
                BufferedReader stdIn
                    = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye.")) {
                    break;
                }

                fromUser = stdIn.readLine();
                if (fromUser != null) {

                    uscita.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            
            System.exit(1);
        } catch (IOException e) {
           
            System.exit(1);
        }
    }
}
