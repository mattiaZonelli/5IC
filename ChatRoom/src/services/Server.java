package services;

import chatroom.FXMLClientController;
import dbutil.ConnectionDB;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Swapnil Paul
 */
public class Server extends Thread {

    /**
     *
     * @param args
     * @throws IOException
     */
    private DatagramSocket clientSock = null;
    private int port;
    private String hostname;
    private Connection conn = null;

    /**
     *
     */
    public Server() {
        this.port = 7777;
    }

    @Override
    public void run() {
        ArrayList<String> portArray = new ArrayList<>();
        try {
            clientSock = new DatagramSocket(7777);
            clientSock.setBroadcast(true);

            System.out.println("Server UDP started. Waiting for incoming data from client...");
            while (true) {
                //buffer to receive incoming data
                byte[] buffer = new byte[1000];//65536
                DatagramPacket incomingPacket = new DatagramPacket(buffer, buffer.length);

                clientSock.receive(incomingPacket);

                byte[] data = incomingPacket.getData();
                String stringMsg = new String(data, 0, incomingPacket.getLength());
                if (stringMsg.length() > 4 && stringMsg.substring(0, 2).equals("XX")) {
                    if (stringMsg.substring(2, 9).equals("ADDUSER")) {
                        String user[] = stringMsg.substring(9, stringMsg.length()).split("§§");
                        String returnMsg = "XXADDUSER" + handleNewUserPacket(user[0], user[1]);
                        DatagramPacket dp = new DatagramPacket(returnMsg.getBytes(), returnMsg.getBytes().length, incomingPacket.getAddress(), incomingPacket.getPort());
                        clientSock.send(dp);
                    } else if (stringMsg.substring(2, 9).equals("LOGINVE")) {
                        String user[] = stringMsg.substring(9, stringMsg.length()).split("§§");
                        String returnMsg = "XXLOGINVE" + handleLoginVerification(user[0], user[1]);
                        System.out.println(returnMsg);
                        DatagramPacket dp = new DatagramPacket(returnMsg.getBytes(), returnMsg.getBytes().length, incomingPacket.getAddress(), incomingPacket.getPort());
                        clientSock.send(dp);
                    }
                } else {
                    String ipv4 = incomingPacket.getSocketAddress().toString().substring(1);
                    String user[] = stringMsg.split(":");
                    String addUser = "";
                    if (!portArray.contains(ipv4)) {
                        portArray.add(ipv4);
                        addUser = "           << "+ user[0] + " è entrato nella chatroom >>ç";
                        System.err.println(ipv4);
                    }
                    
                    if(user[1].equalsIgnoreCase("  close")||user[1].trim().equalsIgnoreCase("  chiudi")){
                        addUser = "           << "+ user[0] + " è uscito dalla chatroom >";
                        stringMsg = ">";
                        System.out.println("uscitp");
                    }
                    System.out.println(incomingPacket.getAddress().getHostAddress() + " : " + incomingPacket.getPort() + " - " + stringMsg);

                    if (!stringMsg.isEmpty() || stringMsg.equals(">")) {
                        String portAddrs[];
                        String newString;
                        for (int i = 0; i < portArray.size(); i++) {
                            portAddrs = portArray.get(i).split(":");
                            newString = addUser + stringMsg;
                            DatagramPacket dp = new DatagramPacket(newString.getBytes(), newString.getBytes().length, InetAddress.getByName(portAddrs[0]), Integer.parseInt(portAddrs[1]));
                            //InetAddress.getByName("192.168.1.255") ->>> BROADCAST!!!
                            clientSock.send(dp);
                        }
                        newString = "";
                        stringMsg = null;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param username
     * @param password
     * @return String
     */
    public String handleNewUserPacket(String username, String password) {
        String out = "Database not working!";
        boolean existUs = false;
        try {
            conn = ConnectionDB.getConnection();
            if (conn == null) {
                System.err.println("Non è possibile connetersi al DB");
            } else {
                PreparedStatement stmUser = conn.prepareStatement("SELECT * FROM Users");
                ResultSet rsUser = stmUser.executeQuery();
                do {
                    String userNamers = rsUser.getString("username");
                    String pswrs = rsUser.getString("password");
                    if (userNamers.equalsIgnoreCase(username)) {
                        out = "User alredy exist!";
                        existUs = true;
                    }
                } while (rsUser.next() && !existUs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLClientController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!existUs) {
            ConnectionDB.insertUser(username, password);
            out = "User successfully added!";
        }
        return out;
    }

    /**
     *
     * @param username
     * @param password
     * @return boolean
     */
    public boolean handleLoginVerification(String username, String password) {
        boolean verify = false;
        try {
            conn = ConnectionDB.getConnection();
            if (conn == null) {
                System.err.println("Non è possibile connetersi al DB");
            } else {
                PreparedStatement stmLog = conn.prepareStatement("SELECT * FROM Users");
                ResultSet rsLog = stmLog.executeQuery();
                do {
                    String usernameRs = rsLog.getString("username");
                    String pswRs = rsLog.getString("password");
                    if (usernameRs.equals(username) && pswRs.equals(password)) {
                        verify = true;
                    }
                } while (rsLog.next() && !verify);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return verify;
    }

    public void clientSockClose() {
        clientSock.close();
    }

    /* public static void main(String[] args) throws IOException {
        Server s = new Server();
        s.start();
    }*/
}
