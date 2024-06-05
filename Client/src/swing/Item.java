/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import client.Main;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import model.Ticket;

/**
 *
 * @author Admin
 */
public class Item extends javax.swing.JPanel {

    /**
     * @return the ticket
     */
    public Ticket getTicket() {
        return ticket;
    }

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }
    
    public Item() {
        initComponents();
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(242, 242, 242));
        g2.fillRoundRect(0, 0, getWidth(),getHeight(),20,20);
        g2.dispose();
        super.paint(g); 
    }
    
    private Ticket ticket;
    
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        lbName.setText("Số vé: "+ ticket.getId());
    }
    
    public void selectTicket(int check) {
        //System.out.println(check);
        String imagePath = "image/";
        if(check == 0) {
            imagePath = imagePath + "chair1.png";
        } else if (check == 1) {
            imagePath = imagePath + "chair4.png";
        } else if (check == 2) {
            imagePath = imagePath + "chair3.png";
        } else if (check == 3) {
            imagePath = imagePath + "chair2.png";
        }
        java.net.URL imageURL = Main.class.getClassLoader().getResource(imagePath);
        imagePath = imageURL.getFile();
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Icon icon = (Icon) imageIcon;
        //System.out.println(icon);
        pictureBox1.setImage(icon);
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbName = new javax.swing.JLabel();
        pictureBox1 = new swing.PictureBox();

        lbName.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbName.setForeground(new java.awt.Color(76, 76, 76));
        lbName.setText("Số ghế");

        pictureBox1.setImage(new javax.swing.ImageIcon(getClass().getResource("/image/chair1.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(pictureBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lbName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pictureBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbName;
    private swing.PictureBox pictureBox1;
    // End of variables declaration//GEN-END:variables
}
