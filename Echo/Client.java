package echo;

import java.io.*;
import java.net.*;

/**
 * @author tasca.marco
 */
public class Client {

    Socket socket;

    String line;
    BufferedReader is;
    PrintStream os;

    BufferedReader read;
    boolean stopped;

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }

    private void run() throws IOException {
        socket = new Socket("127.0.0.1", 9999);
        is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        os = new PrintStream(socket.getOutputStream());

        read = new BufferedReader(new InputStreamReader(System.in));       
        stopped = false;

        while (!stopped) {
            System.out.print("Digita: ");
            line = read.readLine();
            
            os.println(line);
            
            line = is.readLine();
            if(line.equals("STOP")){
                System.out.println("Connessione interrotta dal server.");
                stopped = true;
            }else{
                System.out.println(line);
            }
        }
    }

}
