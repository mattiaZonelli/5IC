package chatthreaddentico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThreadDentico {

    public static void main(String[] args) throws IOException {
        final int port = 8000;
        System.out.println("Il server è in attesa");
        ServerSocket server = new ServerSocket(port);
        Socket clientSocket;
        int i = 1;
        while (true) {
            clientSocket = server.accept();
            System.out.println("collegamento ricevuto dall'indirizzo " + clientSocket.getInetAddress() + " sulla porta " + clientSocket.getPort());
            ServerThread serverThread = new ServerThread(i, clientSocket);
            Thread thread = new Thread(serverThread);
            i++;
            thread.start();
        }
    }
}

class ServerThread implements Runnable {

    Socket clientSocket;
    int nClient;

    public ServerThread(int nClient, Socket s) {
        this.clientSocket = s;
        this.nClient = nClient;
    }

    @Override
    public void run() {
        RiceviMessaggio ricezione = new RiceviMessaggio(this.clientSocket, this.nClient);
        Thread messaggio = new Thread(ricezione);
        messaggio.start();
    }
}

class RiceviMessaggio implements Runnable {

    Socket clientSocket = null;
    BufferedReader input = null;
    ServerThreadDentico server;
    int nClient;
    PrintWriter write;

    public RiceviMessaggio(Socket Socket, int nClient) {
        this.nClient = nClient;
        this.clientSocket = Socket;
        server = new ServerThreadDentico();
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            write = new PrintWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            String messaggioRicevuto;
            String confermaMessaggio = "messaggio ricevuto dal Client";
            while (true) {
                while ((messaggioRicevuto = input.readLine()) != null) {
                    if (messaggioRicevuto.equalsIgnoreCase("chiudi")) {
                        break;
                    }
                    System.out.println("Client " + this.nClient + ": " + messaggioRicevuto);
                    write.println(confermaMessaggio);
                    write.flush();
                }
                clientSocket.shutdownInput();
                clientSocket.shutdownOutput();
            }
        } catch (IOException ex) {
            System.out.println("Il client " + nClient + " si è disconnesso.");
        }
    }
}
