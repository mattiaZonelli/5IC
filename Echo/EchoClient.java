
import java.io.*;
import java.net.*;

public class EchoClient {

    private Socket socket = null;
    private static final String IP = "127.0.0.1";
    private static final int PORT = 9090;
    private boolean endedClient;
    private boolean endLoop;
    private boolean setValues;
    private boolean sessionStarted;
    private final BufferedReader keyboard;

    public EchoClient(String ipAddress, int port) {
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        sessionStarted = true;
        while (sessionStarted) {
            try {

                endLoop = false;
                while (!endLoop) {
                    endedClient = false;
                    setValues = false;
                    System.out.println("----------------------------------CLIENT ECHO----------------------------------");
                    System.out.println("To edit ip address type \"edit\". \n The default values are \"localhost\" and \"9090\".\n To go on without setting type \"start\"");
                    String line = keyboard.readLine();
                    while (!line.equalsIgnoreCase("start") && !setValues) {
                        if (line.equalsIgnoreCase("edit")) {
                            System.out.print("IP: ");
                            ipAddress = keyboard.readLine().trim();
                            System.out.println(ipAddress);
                            System.out.print("PORT: ");
                            try {
                                port = Integer.parseInt(keyboard.readLine().trim());
                            } catch (NumberFormatException ex) {
                                System.out.println("Type an available port number");
                            }
                            System.out.println(port);
                            setValues = true;
                        } else {
                            line = keyboard.readLine();
                        }
                    }
                    if (line.equalsIgnoreCase("start") || setValues) {
                        System.out.print("Do you want to connect to the server? (y/n)");
                        if (keyboard.readLine().equalsIgnoreCase("y")) {
                            socket = new Socket(ipAddress, port); //connessione al server
                            System.out.println(write() ? "Echo service successfully ended" : "Echo service quit with error");
                            endLoop = true;
                        }
                    }
                }
                System.out.println("Do you want to start another connection at " + ipAddress + ":" + port + " ? (y/n) ");
                if (keyboard.readLine().equalsIgnoreCase("n")) {
                    sessionStarted = false;
                }
            } catch (UnknownHostException ex) {
                System.err.println("I can't find " + ipAddress);
            } catch (SocketException ex) {
                System.err.println("Could not connect to " + ipAddress);
            } catch (IOException ex) {
                System.out.println("Connection issue. Try checking the IP address and port, then restart the service.");
            }

        }
    }

    private boolean write() {
        boolean status = true;
        PrintStream ps = null;
        BufferedReader bt = null;
        BufferedReader keyboard = null;
        try {
            while (!endedClient) {
                keyboard = new BufferedReader(new InputStreamReader(System.in));
                ps = new PrintStream(socket.getOutputStream(), true);
                ps.println(keyboard.readLine());
                bt = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String echoedString = bt.readLine();
                System.out.println(echoedString);
                if (echoedString.charAt(echoedString.length() - 1) == (char) 0) {
                    endedClient = true;
                }
                Thread.sleep(1000);
            }
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
            status = false;
        } catch (IOException e) {
            e.printStackTrace();
            status = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            return status;
        }

    }

    public static void main(String args[]) {
        EchoClient es = new EchoClient(IP, PORT);

    }

}
