
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// Fantuzzo Marco 5IC

public class Server {
    Server() {
        ServerSocket s;
        try {
            s = new ServerSocket(9999);
            System.out.println("Server avviato");
            int IDClient = 0;
            while (true) {
                Thread t = new Thread(new User(s.accept(), IDClient++));
                t.start();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public class User implements Runnable {
        Socket s;
        String messaggio = "";
        PrintWriter uscita;
        BufferedReader ingresso;
        int IDClient;

        public User(Socket s, int ID) {
            this.s = s;
            this.IDClient = ID;
        }

        @Override
        public void run() {
            try {
                uscita = new PrintWriter(s.getOutputStream(), true); //true = Autoflush
                ingresso = new BufferedReader(new InputStreamReader(s.getInputStream()));
                while (true) {
                    messaggio = ingresso.readLine();
                    System.out.println("Client " + IDClient + ": " + messaggio);
                    uscita.println("Messaggio ricevuto dal Server");
                    if (messaggio == null) {
                        break;
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }

        }

    }
    
    public static void main(String[] args) throws IOException {
        Server s = new Server();
    }
}
