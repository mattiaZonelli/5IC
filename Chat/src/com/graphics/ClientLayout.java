/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphics;

import com.client.Client;
import com.database.Field;
import com.protocol.Packet;
import com.protocol.Protocol;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DA FARE:
 * verificare password in congiunzione con username
 * Selezionare ip di invio in base a contatto selezionato
 * memorizzare cache conversazione
 * inserire funzione per amicizia
 * scaricare immagini in cache
 *
 * @author Nicola
 */
public class ClientLayout extends javax.swing.JFrame {

    private final String IMAGE_SRC = "img\\arrow.png";
    private final String DISABLED_SRC = "img\\disabledArrow.png";
    private final String HEADER = "img\\header.png";
    private final String CONTACTS;
    private final String FRIENDSHIP = "img\\friendship.png";
    private final String PRESSED_FRIENDSHIP = "img\\pressedFriendship.png";
    private FileOutputStream contactAdder = null;
    /**
     * Creates new form ClientLayout
     */
    private static Client client;

    public ClientLayout(Client client) {
        super("Logos");

        this.client = client;
        CONTACTS = "contacts\\" + client.getUsername() + "Contacts.dat";
        try {
            contactAdder = new FileOutputStream(new File(CONTACTS), true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        initComponents();

        this.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setMinimumSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));

        userPanel.setBackground(new Color(0, 89, 89));
        this.getContentPane().setBackground(new Color(0, 89, 89));

        resizeListener();
        initSendPanel();
        initHeaderPanel();
        initFriendshipPanel();
        initContactPanel();
        closingListener();
        setVisible(true);

    }

    private void closingListener(){

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Packet packet = null;
                try {
                    Field f=new Field("Closing",client.getUsername(),Protocol.CLOSING);
                    packet = new Packet(f, InetAddress.getByName(client.SERVER_ADDRESS), client.SERVER_PORT, true);
                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                }
                client.sendUrgent(packet);
                System.exit(0);

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private void resizeListener() {
        this.addComponentListener(new ComponentAdapter() {
            /**
             * Invoked when the component's size changes.
             *
             * @param e
             */
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                conversationPanel.revalidate();
                conversationPanel.repaint();
                revalidate();
                repaint();
            }
        });
    }



    private void initFriendshipPanel() {
        ImageIcon image = new ImageIcon(FRIENDSHIP);
        JLabel label = new JLabel("", image, JLabel.CENTER);
        friendshipPanel.setLayout(new BorderLayout());
        friendshipPanel.setBackground(new Color(1, 146, 143));
        friendshipPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String answer = JOptionPane.showInputDialog(null, "Enter the username of the person you want to befriend to ", "Friendship request", JOptionPane.QUESTION_MESSAGE);
                if(answer!=null && answer.trim()!="") {
                    if (!existsInFile(new File(CONTACTS), answer) && answer != client.getUsername()) {
                        boolean exists = SignIn.checkFieldExistence(new Field("Username", answer, Protocol.EXISTS));
                        if (exists) {
                            addContact(answer);
                        } else {
                            JOptionPane.showMessageDialog(null, "Username not found", "Friendship request", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                label.setIcon(new ImageIcon(PRESSED_FRIENDSHIP));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                label.setIcon(new ImageIcon(FRIENDSHIP));
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        friendshipPanel.add(label);
        headerPanel.add(friendshipPanel, BorderLayout.SOUTH);
    }

    private void initHeaderPanel() {
        ImageIcon image = new ImageIcon(HEADER);
        JLabel label = new JLabel("", image, JLabel.CENTER);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), this.getHeight() / 4));
        headerPanel.setBackground(new Color(1, 146, 143));
        headerPanel.add(label);
    }

    public static void addReceivedMessage(Object message) {
        StyledDocument document = conversationPanel.getStyledDocument();
        SimpleAttributeSet paragraph = new SimpleAttributeSet();
        StyleConstants.setAlignment(paragraph, StyleConstants.ALIGN_LEFT);

        String toBeVisualized="["+client.getSendTo()+ "]\n" + addNewLines(message.toString(),120)+"\n";

        document.setParagraphAttributes(conversationPanel.getText().length(), toBeVisualized.length(), paragraph, false);

        try {
            document.insertString(document.getLength(), toBeVisualized, paragraph);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }

    public static void addSendMessage(String message){
        StyledDocument document = conversationPanel.getStyledDocument();
        SimpleAttributeSet paragraph = new SimpleAttributeSet();
        StyleConstants.setAlignment(paragraph, StyleConstants.ALIGN_RIGHT);
        String toBeVisualized="[YOU]\n" + addNewLines(message,120);
        document.setParagraphAttributes(document.getLength(), toBeVisualized.length(), paragraph, false);

        try {
            document.insertString(document.getLength(), toBeVisualized, paragraph);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }

    private static String addNewLines(String s, int numberCharacters){
       String temp="";
       for(int i=0;i<s.length();i+=numberCharacters){
           temp+=s.substring(i,i+numberCharacters<s.length()?i+numberCharacters:s.length())+"\n";
       }
       return temp;
    }

    private void initSendPanel() {
        ImageIcon image = new ImageIcon(IMAGE_SRC);
        JLabel label = new JLabel("", image, JLabel.CENTER);
        sendPanel.setLayout(new BorderLayout());
        sendPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String ipAddress = askForIpResolution(new Field("Username", client.getSendTo(), Protocol.IP_RESOLUTION));
                    if (ipAddress.equals("0.0.0.0")) {
                        JOptionPane.showMessageDialog(null, client.getSendTo() + " is currently offline. Try later.", "Message not delivered", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        Packet packet = new Packet(inputPanel.getText(), InetAddress.getByName(ipAddress), client.SERVER_PORT, false, client.getSendTo());
                        if (client.getSendTo() != null) {
                            client.getSender().sendMessage(packet); //devo fare richeista al server dell'indirizzo ip
                        }

                      addSendMessage(inputPanel.getText());

                        revalidate();
                        repaint();
                        inputPanel.setText(null);

                        System.out.println("SENDING TO: " + client.getSendTo());
                    }
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                label.setIcon(new ImageIcon(DISABLED_SRC));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                label.setIcon(new ImageIcon(IMAGE_SRC));
                String line = inputPanel.getText();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        client.setSendTo(null);
        sendPanel.add(label);
    }

    private void initContactPanel() {
        BufferedReader br = null;
        contactsPanel.setLayout(new BoxLayout(contactsPanel, BoxLayout.Y_AXIS));
        try {
            br = new BufferedReader(new FileReader(new File(CONTACTS)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClientLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
        String line = "";
        List<String> users = new LinkedList<>();

        try {
            while ((line = br.readLine()) != null) {
                System.out.println("Contact: "+line);
                users.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        contactsPanel.removeAll();
        for (int i = 0; i < users.size(); i++)
            contactsPanel.add(new ContactLayout(contactsPanel.getWidth(), contactsPanel.getHeight() / 6, users.get(i), client));
    }

    public static void clearBox(){
        conversationPanel.setText(null);
    }
    private String askForIpResolution(Field f) {
        client.setIdle(true);
        Packet packet = null;
        //invio username
        try {
            packet = new Packet(f, InetAddress.getByName(client.SERVER_ADDRESS), client.SERVER_PORT, true);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        client.sendUrgent(packet);
        //aspetta la risposta
        while (client.isIdle()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (client.getControlField().getControl().equals(Protocol.IP_RESOLUTION) && client.getControlField().getName().equals(f.getName())) {
            System.out.println((String) client.getControlField().getValue());
            return (String) client.getControlField().getValue();
        } else {
            return null;
        }
    }

    private static boolean existsInFile(File file, String line) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean exists = false;
        String s = " ";
        while (!exists && s != null) { //controllare anche se file è terminato.
            try {
                s = br.readLine();
                if (s != null && s.equals(line)) {
                    exists = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return exists;
    }

    private void addContact(String contact) {
        try {
            contactAdder.write((contact + System.getProperty("line.separator")).getBytes());
            initContactPanel();
            validate();
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        friendshipPanel = new javax.swing.JPanel();
        contactsPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        userPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        inputPanel = new javax.swing.JTextArea();
        sendPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        conversationPanel = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        conversationPanel.setEditable(false);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        headerPanel.setMaximumSize(new Dimension(headerPanel.getWidth(), this.getHeight() / 4));
        headerPanel.setMinimumSize(new Dimension(headerPanel.getWidth(), this.getHeight() / 4));

        javax.swing.GroupLayout friendshipPanelLayout = new javax.swing.GroupLayout(friendshipPanel);
        friendshipPanel.setLayout(friendshipPanelLayout);
        friendshipPanelLayout.setHorizontalGroup(
                friendshipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 54, Short.MAX_VALUE)
        );
        friendshipPanelLayout.setVerticalGroup(
                friendshipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 55, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
                headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(headerPanelLayout.createSequentialGroup()
                                .addComponent(friendshipPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0))
        );
        headerPanelLayout.setVerticalGroup(
                headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                                .addGap(123, 123, 123)
                                .addComponent(friendshipPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        contactsPanel.setMaximumSize(new Dimension(this.getWidth() / 10, contactsPanel.getHeight()));
        contactsPanel.setMinimumSize(new Dimension(this.getWidth() /10, contactsPanel.getHeight()));
        contactsPanel.setPreferredSize(new Dimension(this.getWidth() / 10, contactsPanel.getHeight()));

        javax.swing.GroupLayout contactsPanelLayout = new javax.swing.GroupLayout(contactsPanel);
        contactsPanel.setLayout(contactsPanelLayout);
        contactsPanelLayout.setHorizontalGroup(
                contactsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 301, Short.MAX_VALUE)
        );
        contactsPanelLayout.setVerticalGroup(
                contactsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        inputPanel.setColumns(20);
        inputPanel.setLineWrap(true);
        inputPanel.setRows(5);
        inputPanel.setWrapStyleWord(true);
        inputPanel.setMaximumSize(new Dimension(conversationPanel.getWidth() - sendPanel.getWidth() - 5, inputPanel.getHeight()));
        inputPanel.setMinimumSize(new Dimension(conversationPanel.getWidth() - sendPanel.getWidth() - 5, inputPanel.getHeight()));
        inputPanel.setPreferredSize(new Dimension(conversationPanel.getWidth() - sendPanel.getWidth() - 5, inputPanel.getHeight()));
        jScrollPane2.setViewportView(inputPanel);

        javax.swing.GroupLayout sendPanelLayout = new javax.swing.GroupLayout(sendPanel);
        sendPanel.setLayout(sendPanelLayout);
        sendPanelLayout.setHorizontalGroup(
                sendPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 88, Short.MAX_VALUE)
        );
        sendPanelLayout.setVerticalGroup(
                sendPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 59, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(conversationPanel);

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
                userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, userPanelLayout.createSequentialGroup()
                                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sendPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10))
        );
        userPanelLayout.setVerticalGroup(
                userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addContainerGap(396, Short.MAX_VALUE)
                                .addComponent(sendPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36))
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(contactsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(userPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator1)
                                        .addComponent(userPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(contactsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                                                .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contactsPanel;
    private static javax.swing.JTextPane conversationPanel;
    private javax.swing.JPanel friendshipPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JTextArea inputPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel sendPanel;
    private javax.swing.JPanel userPanel;
    // End of variables declaration//GEN-END:variables
}
