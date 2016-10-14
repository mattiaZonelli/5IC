import java.io.*;
import java.net.*;
import java.lang.Thread;

public class Echo {
    public static void main(String args[]) {
        
        ServerSocket echoServer = null;
        String line;
        BufferedReader is;
        PrintStream os;
        try {
			echoServer = new ServerSocket(9999);
			System.out.println("Server Creato!");
		} catch (IOException e) {
			System.out.println(e);
		}   
		while(true){
			Socket clientSocket = null;
			try {
				System.out.println("In attesa di connessioni");
				clientSocket = echoServer.accept();
				System.out.println("Connesso: "+clientSocket.getInetAddress());
				is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				os = new PrintStream(clientSocket.getOutputStream());
				boolean stopped = false;
				int stopCounter = 0;
				String wordSequence = "";
				while (!stopped) {
					 line = is.readLine();
					 if(stopCounter == 0){
						 wordSequence = line;
						 stopCounter++;
					 }else{
						 if(wordSequence.equals(line)){
							stopCounter++;
							if(stopCounter >= 3){
								stopped = true;
							}
						}else{
								wordSequence = line;
								stopCounter = 1;
						}
					 }
					 if(stopped){
						 os.println("exit");
					 }else{
						Thread.sleep(1000);
						os.println("ECHO " + line);
						//System.out.println("Ricevuto: "+line); Messaggi di debug 
						//System.out.println("word: "+wordSequence);
						//System.out.println("stopCounter: "+stopCounter);
					 }
					 System.out.println(stopped);
				}
			} catch (IOException e) {
				System.out.println(e);
			}
			catch (InterruptedException ie) {
				System.out.println(ie);
			}
		}
    }
}
