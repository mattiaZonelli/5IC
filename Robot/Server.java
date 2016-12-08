
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
        String messaggioInput, messaggioOutput;
        String messaggio = "";
        PrintWriter uscita;
        BufferedReader ingresso;
        int IDClient;
        Protocol robot;

        public User(Socket s, int ID) {
            this.s = s;
            this.IDClient = ID;
            messaggioInput = "";
            messaggioOutput = "";
        }
        @Override
        public void run() {
            try {
                
                uscita = new PrintWriter(s.getOutputStream(), true); //true = Autoflush
                ingresso = new BufferedReader(new InputStreamReader(s.getInputStream()));
                robot = new Protocol();
                messaggioInput ="";
                while (true) {
                    messaggioInput = ingresso.readLine();
                    messaggio = robot.Conversazione(messaggioInput);
                    uscita.println(messaggio);
                    System.out.println("Client " + IDClient + ": " + messaggioInput );
                    if (messaggio == null) {
                        //System.out.println("Client "+ IDClient + ": DISCONNESSO");
                        break;
                    }                    
                }
                messaggio = ingresso.readLine();
                uscita.println(messaggio);
            } catch (Exception ex) {
                //System.out.println(ex);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Server s = new Server();
    }
}
