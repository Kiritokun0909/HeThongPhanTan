/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import client.Main;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Ticket;
import swing.Item;
import swing.ScrollBar;
import javax.swing.SwingUtilities;

public class FormHome extends javax.swing.JPanel {

    public void setEvent(EventItem event) {
        this.event = event;
    }
    private int count = 0;
    private boolean running = false;
    private int timeCountDown = 10;
    private int countWait = 0;
    private EventItem event;
    private int demV = 0;


    public FormHome() {
        initComponents();
        scroll.setVerticalScrollBar(new ScrollBar());
//        panelSelect.setLayout(new BorderLayout());
        scroll1.setVerticalScrollBar(new ScrollBar());
  
    }

    public void addItem(Ticket tk, String username) {
        Item item = new Item();
        item.setTicket(tk);
        if(!tk.isSold()){
            item.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    if (SwingUtilities.isLeftMouseButton(me)) {
                        event.itemClick(item, tk);

                    }
                }
            });
        } else {
            if (tk.getUserName().equals(username)) {
                item.selectTicket(5);
                demV ++;
            } else {
                item.selectTicket(3);
                demV ++;
            }
        }
        panelItem1.add(item);
        panelItem1.revalidate();
        panelItem1.repaint();

    }

    public void setSelected(Component item, Ticket tk, int check) {
        if (check == 0) {
            ((Item) item).setSelected(false);
            ((Item) item).selectTicket(check);
//            System.out.println("vị trí cần xoá :" + String.valueOf(tk.getId()));
//            System.out.println("Vi tri so sanh:");
            for (int j = 0; j < panelSelect.getComponentCount(); j++) {
                Component com1 = panelSelect.getComponent(j);
                System.out.println(((ItemList) com1).getId());
                if (((ItemList) com1).getId() == tk.getId()) {
                    panelSelect.remove(j);
                    break;
                }
            }
            panelSelect.revalidate();
            panelSelect.repaint();
            demV --;
        } else {
            ((Item) item).setSelected(true);
            ((Item) item).selectTicket(check);
            showItem(tk, check);
            demV++;
        }
    }

    public void showItem(Ticket tk, int status) {

        /*
        status = 1 : dang trong gio hang
        status = 0 : dang doi 
         */
        ItemList i = new ItemList();
        if (status == 1) {
            i.setItem(tk, timeCountDown);
           
            count = count + 1;
        } else {
            i.setItem(tk, -1);
            countWait +=1;
        }

        i.setBtnDelete(event, i);
        panelSelect.add(i);
        panelSelect.revalidate();
        panelSelect.repaint();
       
        if (!running) {
            Thread countDown = new Thread() {
                public void run() {
                    while (count > 0) {
                        for (Component com : panelSelect.getComponents()) {
                            ItemList i = (ItemList) com;
                            if (i.setTime() == 0) {
                                event.deleteTicket(i.getId());
                                Item tmp = (Item) panelItem1.getComponent(i.getId() - 1);
                                //System.out.println(tmp.getTicket().getTicketId());
                                if (tmp.isSelected()) {
                                    tmp.setSelected(false);
                                    tmp.selectTicket(0);
                                    panelItem1.revalidate();
                                    panelItem1.repaint();
                                }
                                panelSelect.remove(0);
                                panelSelect.revalidate();
                                panelSelect.repaint();
                                count = count - 1;
                            }
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    running = false;
                }
            };
            countDown.start();
            running = true;
        }
    }

    public void deleteItemList(Component com) {
        ItemList i = (ItemList) com;
        Item tmp = (Item) panelItem1.getComponent(i.getId() - 1);
        System.out.println(tmp.getTicket().getId());
        if (tmp.isSelected()) {
            tmp.setSelected(false);
            tmp.selectTicket(0);
            panelItem1.revalidate();
            panelItem1.repaint();
        }
        for (int j = 0; j < panelSelect.getComponentCount(); j++) {
            Component com1 = panelSelect.getComponent(j);
            if (((ItemList) com1).getId() == i.getId()) {
                if(((ItemList) com1).getTime() == -1) {
                    countWait -=1;
                }
                panelSelect.remove(j);
                break;
            }
        }

        panelSelect.revalidate();
        panelSelect.repaint();
        count = count - 1;
    }

    public void updateItem(List<Ticket> tk, String username) {
        int dem = 0;
        for (int i = 0; i < tk.size(); i++) {
            Ticket tmp = tk.get(i);
            if (tmp.isSold()) {
                if (!tmp.getUserName().equals(username)) {
                    // nếu vé đã bán và không phải là client này 
                    for (int j = 0; j < panelItem1.getComponentCount(); j++) {
                        Component com1 = panelItem1.getComponent(j);
                        if (((Item) com1).getTicket().getId() == tmp.getId()) {
                            // nếu client này dang chon ve đó 
                            if (((Item) com1).isSelected()) {
                                for (int t = 0; t < panelSelect.getComponentCount(); t++) {
                                    Component com2 = panelSelect.getComponent(t);
                                    if (((ItemList) com2).getId() == tmp.getId()) {
                                        panelSelect.remove(t);
                                        break;
                                    }
                                }
                                countWait -=1;
                                panelSelect.revalidate();
                                panelSelect.repaint();
                                ((Item) com1).setSelected(false);
                            }
                            ((Item) com1).selectTicket(3);
                            MouseListener[] mouseListeners = ((Item) com1).getMouseListeners();

                            // Loại bỏ toàn bộ MouseListener
                            for (MouseListener listener : mouseListeners) {
                                ((Item) com1).removeMouseListener(listener);
                            }
                            panelItem1.revalidate();
                            panelItem1.repaint();
                        }
                    }
                }
            } else {
                if (tmp.getUserName().equals(username)) {
                    for (int t = 0; t < panelSelect.getComponentCount(); t++) {
                        Component com2 = panelSelect.getComponent(t);
                        if (((ItemList) com2).getId() == tmp.getId()) {
                            if (((ItemList) com2).getTime() == -1) {
                                ((ItemList) com2).setTimeCountDown(timeCountDown);
                                count = count + 1;
                                countWait -=1;
                               
                                if (!running) {
                                    Thread countDown = new Thread() {
                                        public void run() {
                                            while (count > 0) {
                                                for (Component com : panelSelect.getComponents()) {
                                                    ItemList i = (ItemList) com;
                                                    if (i.setTime() == 0) {
                                                        event.deleteTicket(i.getId());
                                                        Item tmp = (Item) panelItem1.getComponent(i.getId() - 1);
                                                        //System.out.println(tmp.getTicket().getTicketId());
                                                        if (tmp.isSelected()) {
                                                            tmp.setSelected(false);
                                                            tmp.selectTicket(0);
                                                            panelItem1.revalidate();
                                                            panelItem1.repaint();
                                                        }
                                                        panelSelect.remove(0);
                                                        panelSelect.revalidate();
                                                        panelSelect.repaint();
                                                        count = count - 1;
                                                    }
                                                }
                                                try {
                                                    Thread.sleep(1000);
                                                } catch (InterruptedException e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }
                                            }
                                            running = false;
                                         
                                        }
                                    };
                                    countDown.start();
                                    running = true;
                                }
                                for (int j = 0; j < panelItem1.getComponentCount(); j++) {
                                    Component com1 = panelItem1.getComponent(j);
                                    if (((Item) com1).getTicket().getId() == tmp.getId()) {
                                        // nếu client này dang chon ve đó 
                                        ((Item) com1).selectTicket(1);
                                        panelItem1.revalidate();
                                        panelItem1.repaint();
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }

                } else if(tmp.getUserName().equals("null")) { dem ++;}
//                else if (tmp.getUserName().equals("null")){
//                    for (int t = 0; t < panelSelect.getComponentCount(); t++) {
//                        Component com2 = panelSelect.getComponent(t);
//                        if (((ItemList) com2).getId() == tmp.getId()) {
//                            panelSelect.remove(t);
//                            panelSelect.revalidate();
//                            panelSelect.repaint();
//                            break;
//                        }
//                    }
//                    for (int j = 0; j < panelItem1.getComponentCount(); j++) {
//                        Component com1 = panelItem1.getComponent(j);
//                        if (((Item) com1).getTicket().getId() == tmp.getId()) {
//                                        // nếu client này dang chon ve đó 
//                            ((Item) com1).selectTicket(0);
//                            ((Item) com1).addMouseListener(new MouseAdapter() {
//                                    @Override
//                                    public void mousePressed(MouseEvent me) {
//                                        if (SwingUtilities.isLeftMouseButton(me)) {
//                                            event.itemClick((Item) com1, ((Item) com1).getTicket());
//
//                                        }
//                                    }
//                                });
//                            panelItem1.revalidate();
//                            panelItem1.repaint();
//                            break;
//                        }
//                    }
//                }
            }
            if (dem == tk.size() && demV > 0) {
                panelSelect.removeAll();
                panelSelect.revalidate();
                panelSelect.repaint();
                panelItem1.removeAll();
                for (Ticket t: tk) {
                    addItem(t,username);
                }
                demV =0;
            }

        }
        panelSelect.revalidate();
        panelSelect.repaint();
        System.out.println(running);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll = new javax.swing.JScrollPane();
        panelItem1 = new swing.PanelItem();
        panelInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnHuy = new javax.swing.JButton();
        btnMua = new javax.swing.JButton();
        scroll1 = new javax.swing.JScrollPane();
        panelSelect = new swing.PanelItem();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1300, 570));

        scroll.setViewportView(panelItem1);

        panelInfo.setOpaque(false);
        panelInfo.setPreferredSize(new java.awt.Dimension(230, 457));

        jLabel1.setText("Giỏ vé");

        btnHuy.setText("Huỷ");
        btnHuy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHuyMouseClicked(evt);
            }
        });

        btnMua.setText("Mua");
        btnMua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMuaMouseClicked(evt);
            }
        });
        btnMua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMuaActionPerformed(evt);
            }
        });

        scroll1.setViewportView(panelSelect);

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll1)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnMua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(btnHuy)
                .addGap(25, 25, 25))
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMua)
                    .addComponent(btnHuy))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 1066, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnMuaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMuaMouseClicked
        // TODO add your handling code here:
        
        int i = panelSelect.getComponentCount();
        for (int j =0; j<i;j ++) {
            if (((ItemList) panelSelect.getComponent(j)).getTime() == -1) {
                JOptionPane.showMessageDialog(panelSelect, "Có vé đang đợi thêm vào giỏ vé, không thể mua!");
                return;
            }
        }
        String result= "";
        while (i > 0) {
            ItemList tmp = (ItemList) panelSelect.getComponent(0);
            this.event.buyTicket(tmp.getId());
            result += String.valueOf(tmp.getId());
            if (i != 1) {
                result += ", ";
            }
            for (int j = 0; j < panelItem1.getComponentCount(); j++) {
                Item tmp1 = (Item) panelItem1.getComponent(j);
                if (tmp1.getTicket().getId() == tmp.getId()) {
                    MouseListener[] mouseListeners = tmp1.getMouseListeners();
                    tmp1.selectTicket(5);
                    // Loại bỏ toàn bộ MouseListener
                    for (MouseListener listener : mouseListeners) {
                        tmp1.removeMouseListener(listener);
                    }
                    break;
                }
            }
            panelSelect.remove(0);
            count = count - 1;
            i--;
        }
        JOptionPane.showMessageDialog(panelSelect, "Đặt thành công các vé: "+ result+ " !");
        running = false;
        panelSelect.revalidate();
        panelSelect.repaint();
        panelItem1.revalidate();
        panelItem1.repaint();
        //JOptionPane.showMessageDialog(panelSelect, "Vừa bam nút mua cac vé" + s);
    }//GEN-LAST:event_btnMuaMouseClicked

    private void btnHuyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyMouseClicked
        // TODO add your handling code here:
        for (Component com : panelSelect.getComponents()) {
            ItemList i = (ItemList) com;
            event.deleteTicket(i.getId());
            Item tmp = (Item) panelItem1.getComponent(i.getId() - 1);
            //System.out.println(tmp.getTicket().getTicketId());
            if (tmp.isSelected()) {
                tmp.setSelected(false);
                tmp.selectTicket(0);
                panelItem1.revalidate();
                panelItem1.repaint();
            }
            panelSelect.remove(0);

        }
        panelSelect.revalidate();
        panelSelect.repaint();
        count = panelSelect.getComponentCount();
    }//GEN-LAST:event_btnHuyMouseClicked

    private void btnMuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMuaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnMua;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panelInfo;
    private swing.PanelItem panelItem1;
    private swing.PanelItem panelSelect;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JScrollPane scroll1;
    // End of variables declaration//GEN-END:variables

}
