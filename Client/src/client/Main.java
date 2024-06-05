/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Account;
import model.Ticket;
import swing.EventItem;
import swing.FormHome;
import swing.Item;
import swing.ItemList;


public class Main extends javax.swing.JFrame {

    private FormHome home;
    private List<Ticket> tk;
    private String ipAddress;
    private int port;
    private Account acc;
    private ArrayList<Ticket> listTicketWait = new ArrayList<>();
    private boolean running = false;
    
    public Main(String s1, String ipAddress, int port, Account acc) {
        initComponents();
        setBackground(new Color(0, 0, 0));
        this.tk = splitData(s1);
        this.ipAddress = ipAddress;
        this.port = port;
        this.acc = acc;
        init();
    }

    
    private void init() {
        home = new FormHome();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(home);
//        home.setVisible(true);
        testData();
    }
    
    private void testData() {
        home.setEvent(new EventItem(){
            public void itemClick(Component com, Ticket tk) {
                if(!((Item) com).isSelected()){
                    String input = "chonve "+ acc.getUserName() + " " + String.valueOf(tk.getId());
                    String output = "";
                    try {
                        Socket socket = new Socket(ipAddress, port);
                         // khởi tạo socketclinet
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        dataOutputStream.writeUTF(input);
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        output = dataInputStream.readUTF();
                    } catch (IOException e) {
                        System.err.println("Error connecting to server: " + e.getMessage());
                    }
                    
                    System.out.println(output + " output chonve");
                    String[] lout = output.split(", ");
                    int check = Integer.parseInt(lout[1]);
                    home.setSelected(com, tk, check);

                }else {
                    String input = "huyve "+ acc.getUserName() + " " + String.valueOf(tk.getId());
                    String output = "";
                    try {
                        Socket socket = new Socket(ipAddress, port);
                         // khởi tạo socketclinet
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        dataOutputStream.writeUTF(input);
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        output = dataInputStream.readUTF();
                    } catch (IOException e) {
                        System.err.println("Error connecting to server: " + e.getMessage());
                    }

                    System.out.println(output);
                    home.setSelected(com, tk, 0);
                }
                
                
                
            }
            
//            public void itemClickDelete(Component com, Ticket tk) {
//                String input = "huyve "+ acc.getUserName() + " " + String.valueOf(tk.getId());
//                String output = "";
//                try {
//                    Socket socket = new Socket(ipAddress, port);
//                     // khởi tạo socketclinet
//                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//                    dataOutputStream.writeUTF(input);
//                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
//                    output = dataInputStream.readUTF();
//                } catch (IOException e) {
//                    System.err.println("Error connecting to server: " + e.getMessage());
//                }
//                
//                System.out.println(output);
//            }

            @Override
            public void itemListCLick(Component com) {
                ItemList il = (ItemList) com;
                String input = "huyve "+ acc.getUserName() + " " + String.valueOf(il.getId());
                String output = "";
                try {
                    Socket socket = new Socket(ipAddress, port);
                     // khởi tạo socketclinet
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF(input);
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    output = dataInputStream.readUTF();
                } catch (IOException e) {
                    System.err.println("Error connecting to server: " + e.getMessage());
                }
                System.out.println(output);
                home.deleteItemList(com);
            }
            
            @Override
            public void deleteTicket(int id) {
                String input = "huyve "+ acc.getUserName() + " " + String.valueOf(id);
                String output = "";
                try {
                    Socket socket = new Socket(ipAddress, port);
                     // khởi tạo socketclinet
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF(input);
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    output = dataInputStream.readUTF();
                } catch (IOException e) {
                    System.err.println("Error connecting to server: " + e.getMessage());
                }
                System.out.println(output);
            }
            @Override
            public void buyTicket(int id){
                String input = "datve "+ acc.getUserName() + " " + String.valueOf(id);
                String output = "";
                try {
                    Socket socket = new Socket(ipAddress, port);
                     // khởi tạo socketclinet
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF(input);
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    output = dataInputStream.readUTF();
                } catch (IOException e) {
                    System.err.println("Error connecting to server: " + e.getMessage());
                }
                System.out.println(output);
            }
        });
        for (Ticket t: tk) {
            home.addItem(t,acc.getUserName());
        }
    }
    public List<Ticket> splitData(String s) {
        s = s.replace("[", "");
        s = s.replace("]",  "");
//        s = s.trim();
        String[] tmp = s.split(", ");
        List<Ticket> result = new ArrayList<>();
        System.out.println(s);
        for(String t : tmp) {
            
            String[] tmp1 = t.split(" ");
            Ticket t1 = new Ticket(Integer.parseInt(tmp1[0]),Boolean.parseBoolean(tmp1[1]), tmp1[2]);
            result.add(t1);
        }
        return result;
    }
    public void recall() {
        Thread callBack = new Thread() {
            public void run() {
                while(true){
                    String input="capnhat";
                    String output = "";
                   try {
                        Socket socket = new Socket(ipAddress, port);
                         // khởi tạo socketclinet
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        dataOutputStream.writeUTF(input);
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        output = dataInputStream.readUTF();
                    } catch (IOException e) {
                        System.err.println("Error connecting to server: " + e.getMessage());
                        JOptionPane.showMessageDialog(rootPane, "Ngắt kết nối với Server!");
                        break;
                    }
                    System.out.println(output);
                    tk = splitData(output);
                    home.updateItem(tk, acc.getUserName());
                    try {
                        Thread.sleep(500);
                        } catch (InterruptedException e) {
                                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                }
            } 
        };
        callBack.start();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new swing.Background();
        mainPanel = new swing.MainPanel();
        mainPanel1 = new swing.MainPanel();
        jSeparator1 = new javax.swing.JSeparator();
        pictureBox1 = new swing.PictureBox();
        Client = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        background1.setPreferredSize(new java.awt.Dimension(1352, 750));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 632, Short.MAX_VALUE)
        );

        pictureBox1.setImage(new javax.swing.ImageIcon(getClass().getResource("/image/pngegg.png"))); // NOI18N

        Client.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        Client.setText("Client");

        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1512, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(pictureBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Client, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addComponent(pictureBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(9, 9, 9))
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(Client, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(background1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 840, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(background1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        String input="ngatketnoi";
        String output = "";
        try {
            Socket socket = new Socket(ipAddress, port);
                         // khởi tạo socketclinet
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(input);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            output = dataInputStream.readUTF();
            } catch (IOException e) {
                System.err.println("Error connecting to server: " + e.getMessage());
            }
        System.out.println(output);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Main().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Client;
    private swing.Background background1;
    private javax.swing.JSeparator jSeparator1;
    private swing.MainPanel mainPanel;
    private swing.MainPanel mainPanel1;
    private swing.PictureBox pictureBox1;
    // End of variables declaration//GEN-END:variables
}
