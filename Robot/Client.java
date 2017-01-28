import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by pool on 25/10/16.
 */
public class Client {
    Socket s;
    BufferedReader in;
    BufferedReader keyboard;
    PrintStream out;
    boolean stopped;
    public Client(String ip, int port){
        try {
            s = new Socket(ip,port);
            keyboard = new BufferedReader(new InputStreamReader(System.in));
        }catch(UnknownHostException e){
            e.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        stopped=false;
    }

    public void setStopped() {
        stopped = true;
    }

    public boolean isStopped(){
        return stopped;
    }

    public String decodeAnswer(String answer){
        return answer.replaceAll(""+(char)0,"\n").replaceAll(""+(char)1,"").replaceAll(""+(char)2,"");
    }

    public boolean needAnswer(String question){
        return question.charAt(question.length()-1)!=(char)1;
    }

    public void startComunication(){
            try{
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                out = new PrintStream(s.getOutputStream(),true);
                String received = in.readLine();
                while(!isStopped()){
                    if(received.charAt(received.length()-1) == (char)2) {
                        setStopped();
                        System.out.println(decodeAnswer(received));
                    }else {
                        System.out.println(decodeAnswer(received));
                        String line = (needAnswer(received)) ? keyboard.readLine() : "";
                        out.println(line);
                        received = in.readLine();
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    public static void main(String[] args) {
        String ip="";
        int port=0;
        Scanner input = new Scanner(System.in);
        System.out.println("Inserisci l'ip:");
        ip+= input.nextLine();
        System.out.println("Inserisci la porta:");
        port = input.nextInt();
        Client c = new Client(ip,port);
        c.startComunication();

    }
}
