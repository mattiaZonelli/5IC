import java.io.*;
import java.net.Socket;

/**
 * Created by Rikkardo on 16/10/2016.
 */


public class ClientHandler implements Runnable{

    Socket clientSocket;
    BufferedReader is;
    PrintStream os;
    boolean working;
    String[] outComb;

    public ClientHandler(Socket clientSocket, String[] outComb){
        try{
            this.clientSocket = clientSocket;
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintStream printStream = os = new PrintStream(clientSocket.getOutputStream());
            working = true;
            this.outComb = outComb;
        } catch(IOException e){
            System.out.println("Problems establishing a new connection.");
        }
    }

    public void run(){
        String line;
        int i = 0;
        try {
            while(working) {
                line = is.readLine();
                if (outComb[i].equals(line)) {
                    i++;
                    if (i == outComb.length) {
                        working = false;
                    }
                } else {
                    i = 0;
                }
                if(i<outComb.length){
                    Thread.sleep(1000);
                    os.println("ECHO " + line);
                }
            }
        } catch(IOException e){
            System.out.println("A connection has expired.");
            working = false;
        } catch(InterruptedException e){
            System.out.println("Threading problems.");
            working = false;
        }
        try {
            clientSocket.close();
            System.out.println("Connection closed succesfully.");
        } catch (IOException e){
            System.out.println("Can't close connection.");
        }
    }

}
