package clientecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author leonardo.issori
 */

public class ClientEcho
{
    public static final int PORT = 9090;
    static String serverIP = "127.0.0.1";

    public static void main(String[] args)
    {
        Socket socket;
        Scanner scan = new Scanner(System.in);

        System.out.print("Metti l' ip del server: ");

        try
        {
            socket = new Socket(serverIP,PORT);
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(true)
            {
                System.out.print(">");
                os.print(scan.nextLine() + "\r\n");
                os.flush();
                System.out.println("\n" + is.readLine());

            }
        }
        catch (IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
    }

}
