
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

//Fantuzzo Marco 5IC

public class Client {

    String line;
    BufferedReader is, s1;
    PrintStream os;
    static boolean stop = false;

    Client() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9999);
        is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        os = new PrintStream(socket.getOutputStream(), true);
        s1 = new BufferedReader(new InputStreamReader(System.in));
        String str;
        String received;

        while (!stop) {
            str = s1.readLine();
            os.println(str);
            received = is.readLine();
            //Controllo se hanno completato la sequenza di chiusura
            if (received.equals("chiudiComunicazione")) {
                stop = true;
            } else {
                System.out.println(received);
            }

        }
    }
    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }
}
