package com.graphics;


import com.client.Client;

import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Nicola
 */
public class ContactLayout extends javax.swing.JPanel {

    private final String labelName;
    private int width, height;
    private Client client;

    /**
     * Creates new form ContactLayout
     *
     * @param labelName
     */

    public ContactLayout(int width, int height, String labelName, Client client) {
        super();
        this.client = client;
        this.width = width;
        this.height = height;
        this.setBackground(Color.white);
        this.labelName = labelName;
        initComponents();
        addTouchListener();
        setVisible(true);
    }

    public void addTouchListener() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                client.setSendTo(labelName);
                ClientLayout.clearBox();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                changeBorder(new Color(1, 146, 143), BevelBorder.LOWERED);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                changeBorder(Color.WHITE, BevelBorder.RAISED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void changeBorder(Color color, int borderLevel) {
        setBorder(javax.swing.BorderFactory.createBevelBorder(borderLevel, color, color, new java.awt.Color(0, 89, 89), new java.awt.Color(0, 89, 89)));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        name = new javax.swing.JLabel();
        imageLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)));
        setMaximumSize(new Dimension(width,height));
        setMinimumSize(new Dimension(width,height));

        name.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        name.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        name.setText(labelName);
        name.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(imageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imageLabel))
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel name;
    // End of variables declaration//GEN-END:variables
}