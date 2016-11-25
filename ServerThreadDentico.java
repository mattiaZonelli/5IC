package chatconthread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        final int porta = 8000;
        System.out.println("Server in ascolto...");
		ServerSocket serverSocket = new ServerSocket(porta);
        Socket client;
        int i = 1;
        while (true) {
            client = serverSocket.accept();
            System.out.println("Client " + i + " si e' collegato sull' indirizzo " + client.getInetAddress() + " e sulla porta " + client.getPort()); 
            ServerThread serverThread = new ServerThread(i, client);
            Thread t = new Thread(serverThread);
            i++;
            t.start();
        }
    }
}
class ServerThread implements Runnable {
    Socket clientSocket;
    int id;
    public ServerThread(int id, Socket s) {
        this.clientSocket = s;
        this.id = id;
    }
    @Override
    public void run() {
        RecieveClient recieve = new RecieveClient(this.clientSocket, this.id);
        Thread receiveMessage = new Thread(recieve);
        receiveMessage.start();
    }
}
class RecieveClient implements Runnable {

    Socket clientSocket = null;
    BufferedReader input = null;
    Server s;
    int id;
    PrintWriter writer;

    public RecieveClient(Socket Socket, int id) {
        this.id = id;
        this.clientSocket = Socket;
        s = new Server();
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            String message;
            String msgClient = "Messaggio ricevuto";
            while (true) {
                while ((message = input.readLine()) != null) {
                    if (message.equalsIgnoreCase("chiudi")) {
                        break;
                    }
                    System.out.println("Client " + this.id + ": " + message);
                    
                    writer.println(msgClient);
                    writer.flush();
                }
                this.clientSocket.shutdownOutput();
                this.clientSocket.shutdownInput();   
            }
        } catch (IOException ex) {
			System.out.println("Client " + id + " disconnesso");
        }
    }
}