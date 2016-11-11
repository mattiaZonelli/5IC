import java.io.*;
import java.net.*;

/**
 *
 * @author lucabassanello
 */
public class Client {
   
    public static void main(String[] args) throws IOException {
	try{	
     Socket conn=new Socket("localhost",9999);
     
   
   
     BufferedReader ingresso=new BufferedReader(new InputStreamReader(conn.getInputStream()));
     
    
     PrintWriter uscita=new PrintWriter(conn.getOutputStream(),true);
     
    
     BufferedReader tastiera= new BufferedReader(new InputStreamReader(System.in));
     String messaggio="";
     while(true){
		 System.out.print("messaggio: ");
          messaggio= tastiera.readLine();
         if(messaggio.equals("fine")==true){
             break;
         }
		 uscita.println(messaggio);
		 uscita.flush();
		 messaggio=ingresso.readLine();
     }
	 conn.close();
     
	}
	catch(Exception e){
		System.out.println(e);
	}
    }
	
}
