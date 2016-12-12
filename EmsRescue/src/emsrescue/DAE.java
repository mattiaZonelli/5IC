/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emsrescue;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matteo
 */
public class DAE {

    protected boolean isDefibrillabile;
    protected boolean isConnected;

    public DAE() throws InterruptedException {
        isConnected = true;
        isDefibrillabile = true;
        giveInstructions();
        
    }

    private void printAnalisi() {
        if (isConnected) {
            System.out.println("\nAnalisi in corso, non toccare il paziente!");
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException ex) {
                    System.err.println("Avaria, impossibile scaricare");
                    Logger.getLogger(DAE.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.print(".");
            }
        } else {
            System.out.println("Applicare gli elettrodi.");
        }
    }
    
    public void scarica() throws InterruptedException{
        System.out.println("\n************ NON TOCCARE IL PAZIENTE  *********** \n");
        Thread.sleep(1000);
        System.out.println("\n SCARICA IN CORSO \n");
        Thread.sleep(500);
        System.out.println("\n SCARICA EFFETTUATA \n");
        Thread.sleep(500);
        System.out.println("\n MASSAGGIARE \n");
    }

    public boolean getIsDefibrillabile() {
        return isDefibrillabile;
    }

    public boolean getIsConnected() {
        return isConnected;
    }
    
    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    private void comprimere() throws InterruptedException{
        System.out.println("\nComprimere a questo ritrmo.");
        for (int i = 0; i < 30; i++) {
            System.out.print(i+" ");
            Thread.sleep(500);
            
        }
    }

    private void giveInstructions() throws InterruptedException {
        System.out.println("\nQuesto è un defibrillatore automatico DAE, allertare il 118");
        System.out.println("Applicare gli elettrodi");
        
        for (int i = 0; i < 4; i++) {
            
        
        printAnalisi();

        if (isDefibrillabile == true) {
            System.err.println("\nScarica consigliata.");
        } else {
            System.err.println("\nScarica non consigliata.");
            break;

        }
        
        Scanner input=new Scanner(System.in);
        System.out.println("PREMERE 'X' PER SCARICARE");
        if(input.nextLine().equalsIgnoreCase("x"))
            scarica();
        comprimere();
        }
        
    }
    

    public static void main(String[] args) throws InterruptedException {
        
        DAE dae = new DAE();

    }
}
