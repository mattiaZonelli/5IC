/**
 * Created by Rikkardo on 16/10/2016.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {

    String serverAddress;
    String userInput;
    String serverOutput;
    Socket socket;
    BufferedReader is;
    PrintWriter os;
    BufferedReader stdIn;
    boolean working;

    public EchoClient(String serverAddress) {
        try {
            this.serverAddress = serverAddress;
            userInput = "";
            serverOutput = "";
            socket = new Socket(serverAddress, 9999);
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new PrintWriter(socket.getOutputStream(), true);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            working = true;
        } catch (IOException e) {
            System.out.println("Couldn't establish a connection.");
        }
    }

    public void startCommunication(){
        try {
            while ((userInput = stdIn.readLine()) != null && working) {
                os.println(userInput);
                serverOutput = is.readLine();
                if (serverOutput == null) {
                    System.out.println("Connection expired.");
                    working = false;
                } else {
                    System.out.println(serverOutput);
                }
            }
        } catch(IOException e){

        }
    }

    public static void main(String[] args){
        EchoClient client = new EchoClient("192.168.0.2");
        client.startCommunication();
    }

}
