/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import javax.swing.JFrame;
import model.Account;
import model.Ticket;
import swing.EventItem;
import swing.FormHome;
import java.sql.SQLException;
import java.util.LinkedList;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main extends javax.swing.JFrame {
    private int port;
    private ArrayList<Socket> listClient;
    private ArrayList<Map<Account, Integer>> task;
    private List<Ticket> listTK;
    private FormHome home;
    
    public Main(int port){
        initComponents();
        this.port = port;
        this.listClient = new ArrayList<>();
        this.task = new ArrayList<>();
        setBackground(new Color(0, 0, 0));
        init();
    }
    
    private void init(){
        home = new FormHome();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(home);
        mainPanel2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ReadServer readServer = null;
        try {
            readServer = new ReadServer(task, listClient);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        testData(readServer);
    }
    
    public void excute() throws IOException, SQLException {
        ServerSocket server = new ServerSocket(port);
        System.out.println("start server");
        while (true) {
            Socket socket = server.accept();
            // lấy danh sách client để hiển thị trên server
            if (listClient != null) {
                boolean alreadyInList = false;
                for (Socket socket1 : listClient) {
                    if (socket1.getInetAddress().getHostAddress().equals(socket.getInetAddress().getHostAddress())) {
                        alreadyInList = true;
                        break;
                    }
                }

                if (!alreadyInList) {
                    listClient.add(socket);
                    home.addIP(socket.getInetAddress().getHostAddress(), 1);
                    System.out.println("Server đã kết nối với client: " + socket.getInetAddress().getHostAddress());
                }
            } 
            else {
                // Khởi tạo danh sách nếu nó chưa được khởi tạo
                listClient.add(socket);
                home.addIP(socket.getInetAddress().getHostAddress(), 1);
                System.out.println("Server đã kết nối với client: " + socket.getInetAddress().getHostAddress());
            }
            
            ReadServer readServer = new ReadServer(socket, task, listClient);
            readServer.setEvent(new EventItem(){
                    public void itemRequest( Ticket item, Account acc, int status){
                          home.setSelected(item, acc, status);
                    };
                    
                    public void itemListRequset( Ticket item, Account acc, int status){
                        home.showItem(item, acc, status);
                    };
                    
                    public void exitClient(String ip){
                        home.exitIP(ip);
                    };
                    
                    public void setTicketOfList(int id, boolean sold, String username) {
                        for (int i =0; i< listTK.size(); i++){
                            if(listTK.get(i).getId() == id){
                                listTK.get(i).setSold(sold);
                                listTK.get(i).setUserName(username);
                                break;
                            }
                        }
                        if (sold) {
                            home.deleteAllItemList(id);
                        }
                    };
                    
                    public List<Ticket> getListTicket() {
                        return listTK;
                    }
                    
                    public void setListTicket(List<Ticket> listTK1) {
                        listTK = listTK1; 
                    }
                    
                    public void resetSelect(int id, String username1, String username2){
                        home.deleteItemList(id, username1, username2);
                    }
            });
            readServer.start();
        }
    }
    
    private void testData(ReadServer readServer) {
        try {
            listTK = readServer.DanhSachVe();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(Ticket tk : listTK) {
            home.addItem(tk);
        }
    }
    
    private void resetVe() {
        ReadServer readServer = new ReadServer();
        try {
            readServer.resetTicket();
            listTK = readServer.DanhSachVe();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        home.deleteAllItem();
        for(Ticket tk : listTK) {
            home.addItem(tk);
        }
        task.clear();
        System.out.println(task.size());
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new swing.Background();
        mainPanel = new swing.MainPanel();
        mainPanel1 = new swing.MainPanel();
        jSeparator1 = new javax.swing.JSeparator();
        mainPanel2 = new swing.MainPanel();
        pictureBox1 = new swing.PictureBox();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");

        background1.setPreferredSize(new java.awt.Dimension(1352, 750));

        mainPanel.setPreferredSize(new java.awt.Dimension(1330, 580));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1355, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 586, Short.MAX_VALUE)
        );

        mainPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainPanel2MouseClicked(evt);
            }
        });

        pictureBox1.setImage(new javax.swing.ImageIcon(getClass().getResource("/image/pngegg.png"))); // NOI18N

        javax.swing.GroupLayout mainPanel2Layout = new javax.swing.GroupLayout(mainPanel2);
        mainPanel2.setLayout(mainPanel2Layout);
        mainPanel2Layout.setHorizontalGroup(
            mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pictureBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanel2Layout.setVerticalGroup(
            mainPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pictureBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel1.setText("Server");

        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1355, Short.MAX_VALUE)
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, 1355, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mainPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanel2MouseClicked
        // TODO add your handling code here:
        resetVe();
    }//GEN-LAST:event_mainPanel2MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])throws IOException, SQLException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        int port = 1111;    // TCP port to allow connection (Inbound Rule in Firewall)
        Main main = new Main(port);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                main.setVisible(true);    
            }
        });
        main.excute();
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Background background1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private swing.MainPanel mainPanel;
    private swing.MainPanel mainPanel1;
    private swing.MainPanel mainPanel2;
    private swing.PictureBox pictureBox1;
    // End of variables declaration//GEN-END:variables
}
