
package chatthread;

/*
 *Author Ventura Giorgio
 */
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatThread
{
    Socket conn;
    BufferedReader avvio;
    PrintStream uscita;
    OutputStream out;
    BufferedReader tastiera;
    public ChatThread() 
	{
        try 
		{
            conn = new Socket("localhost", 54444);
            avvio = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            out = conn.getOutputStream();
            uscita = new PrintStream(conn.getOutputStream(), true);
            tastiera = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ex) 
		{
            System.out.println("IO Exception");
        }
    }
    public void Spedisci() 
	{
        String messaggio = "";
        System.out.println("Digitare il messaggio da inviare , per interrompere scrivere 'esci' \n\t");
        while (true) 
		{
            try 
			{
                messaggio = tastiera.readLine();
                System.out.println(messaggio);
            } catch (IOException ex) 
			{
                Logger.getLogger(ChatThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (messaggio.equals("Esci")||messaggio.equals("ESCI")||messaggio.equals("esci")) 
			{
                System.out.println("Chiusura connessione...");
            }
            
            uscita.println(messaggio);
            try 
			{
                System.out.println("DAL SERVER: " + avvio.readLine());
                uscita.flush();
            } catch (IOException ex) 
			{
                Logger.getLogger(ChatThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void main(String[] args) throws IOException 
	{
        ChatThread c = new ChatThread();
        c.Spedisci();
    }
}
