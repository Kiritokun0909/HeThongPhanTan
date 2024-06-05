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
import server.Main;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Ticket;
import swing.Item;
import swing.ScrollBar;
import javax.swing.SwingUtilities;
import model.Account;
import swing.IPItem;

public class FormHome extends javax.swing.JPanel {

    public void setEvent(EventItem event) {
        this.event = event;
    }

    private EventItem event;

    public FormHome() {
        initComponents();
        scroll.setVerticalScrollBar(new ScrollBar());
        scroll1.setVerticalScrollBar(new ScrollBar());
        scroll2.setVerticalScrollBar(new ScrollBar());
    }

    public void addItem(Ticket tk) {
        Item item = new Item();
        item.setTicket(tk);

        if (tk.isSold()) {
            item.selectTicket(3);
        } else {
            if (tk.getUserName() != null) {
                item.selectTicket(2);
            }
        }
        panelItem1.add(item);
        panelItem1.revalidate();
        panelItem1.repaint();

    }

    public void addIP(String ip, int status) {
        System.out.println(panelClient.getComponentCount());
        for (int i = 0; i < panelClient.getComponentCount(); i++) {
            IPItem tmp = (IPItem) panelClient.getComponent(i);
            if (tmp.getIP().equals(ip)) {
                tmp.setStatus(status);
                System.out.println("Check loi~");
                System.out.println(ip);
                System.out.println(tmp.getIP());
                panelClient.revalidate();
                panelClient.repaint();
                return;
            }

        }
        IPItem item = new IPItem();
        item.setIp(ip);
        item.setStatus(status);

        panelClient.add(item);
        panelClient.revalidate();
        panelClient.repaint();
    }

    public void exitIP(String ip) {
        for (Component com : panelClient.getComponents()) {
            IPItem i = (IPItem) com;
            if (ip.equals(i.getIP())) {
                i.setStatus(0);
                panelClient.revalidate();
                panelClient.repaint();
                break;
            }
        }
    }

    public void setSelected(Ticket tk, Account acc, int status) {
        for (Component item : panelItem1.getComponents()) {
            //System.out.println("vé chọn: " + String.valueOf(tk.getId()) + ", vé kiểm tra: "+ String.valueOf(((Item) item).getTicket().getId()));
            if (((Item) item).getTicket().getId() == tk.getId()) {
                if (status == 0 || status == 3) {
                    ((Item) item).setSelected(false);
                    ((Item) item).selectTicket(status);
                    for (int j = 0; j < panelSelect.getComponentCount(); j++) {
                        Component com1 = panelSelect.getComponent(j);
                        ItemList il = (ItemList) com1;

                        if (il.getId() == tk.getId() && acc.getUserName().equals(il.getUserName())) {
                            System.out.println("kiem tra loi~ huy " + acc.getUserName() + ":" + il.getUserName());
                            panelSelect.remove(j);
                            break;
                        }
                    }
                    panelSelect.revalidate();
                    panelSelect.repaint();
                } else {
                    ((Item) item).setSelected(true);
                    ((Item) item).selectTicket(status);
                    showItem(tk, acc, status);
                }
                panelItem1.revalidate();
                panelItem1.repaint();
                break;
            }
        }
    }

    public void showItem(Ticket tk, Account acc, int status) {
        ItemList i = new ItemList();
        i.setItem(tk, acc, status);
        panelSelect.add(i);
        panelSelect.revalidate();
        panelSelect.repaint();
    }

    public void deleteItemList(int id, String username1, String username2) {
        for (int i = 0; i < panelSelect.getComponentCount(); i++) {
            ItemList item = (ItemList) panelSelect.getComponent(i);
            if (item.getId() == id && item.getUserName().equals(username1)) {
                panelSelect.remove(i);
                break;
            }
        }
        for (int i = 0; i < panelSelect.getComponentCount(); i++) {
            ItemList item = (ItemList) panelSelect.getComponent(i);
            if (item.getId() == id && item.getUserName().equals(username2)) {
                item.setStatus(1);
                break;
            }
        }
        panelSelect.revalidate();
        panelSelect.repaint();
    }
    
    public void deleteAllItemList(int id) {
        int i =panelSelect.getComponentCount()- 1;
        while(i >= 0) {
            if(((ItemList)panelSelect.getComponent(i)).getId() == id) {
                panelSelect.remove(i);
            }
            i --;
        }
        panelSelect.revalidate();
        panelSelect.repaint();
    }
    public void deleteAllItem() {
        panelItem1.removeAll();
        panelSelect.removeAll();
        panelSelect.revalidate();
        panelSelect.repaint();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll = new javax.swing.JScrollPane();
        panelItem1 = new swing.PanelItem();
        panelInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        scroll1 = new javax.swing.JScrollPane();
        panelSelect = new swing.PanelItem();
        scroll2 = new javax.swing.JScrollPane();
        panelClient = new swing.PanelItem();
        jLabel2 = new javax.swing.JLabel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1300, 570));

        scroll.setViewportView(panelItem1);

        panelInfo.setOpaque(false);
        panelInfo.setPreferredSize(new java.awt.Dimension(230, 457));

        jLabel1.setText("Thông báo");

        scroll1.setViewportView(panelSelect);

        scroll2.setViewportView(panelClient);

        jLabel2.setText("Client:");

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll1)
            .addComponent(scroll2)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(167, Short.MAX_VALUE))
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scroll2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 1066, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
            .addComponent(panelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private swing.PanelItem panelClient;
    private javax.swing.JPanel panelInfo;
    private swing.PanelItem panelItem1;
    private swing.PanelItem panelSelect;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JScrollPane scroll1;
    private javax.swing.JScrollPane scroll2;
    // End of variables declaration//GEN-END:variables

}
