
import java.io.*;
import java.net.*;

public class EchoServer {

    ServerSocket echoServer = null;
    Socket clientSocket = null;
    private static final String IP = "127.0.0.1";
    private static final int PORT = 9090;
    public final int MAX_REPS = 3;
    private boolean endedEchoService;

    public EchoServer(int port) {
        try {
            echoServer = new ServerSocket(port);
            System.out.println("----------------------------------SERVER ECHO----------------------------------");
            while (true) {
                clientSocket = echoServer.accept();
                endedEchoService = false;
                echo();
            }

        } catch (IOException e) {
            System.out.println(e);
        } finally {
            System.out.println("Chiusura del server in corso...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
            System.exit(0);

        }

    }

    private void echo() {
        System.out.println("------------------------------------\nACCETTATA CONNESSIONE DA: " + clientSocket.getInetAddress());
        String line = "";
        String previous = "";
        int ageCounter = 1;
        BufferedReader is = null;
        PrintStream os = null;
        try {
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream(), true);
            while (!endedEchoService) {
                line = is.readLine();
                if (line == null ? previous == null : line.equals(previous)) {
                    ageCounter++;
                    System.out.println("E' stata inviata la stringa \"" + line + "\" " + (ageCounter) + " volte di seguito");
                } else {
                    ageCounter = 1;
                }
                previous = line;
                if (ageCounter == MAX_REPS) {
                    endedEchoService = true;
                    os.println("ECHO " + line + (char) 0);
                } else {
                    os.println("ECHO " + line);
                    Thread.sleep(1000);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        } finally {
            System.out.println("Connessione con " + clientSocket.getInetAddress() + " terminata");;
        }
    }

    public static void main(String args[]) {
        EchoServer es = new EchoServer(PORT);
    }
}
