/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

/**
 *
 * @author Admin
 */
import model.Ticket;
import java.awt.Component;

public interface EventItem {
   // Xử lí click trên ghế
    public void itemClick(Component com, Ticket item);
    
//    public void itemClickDelete(Component com, Ticket tk);
    
    
    // Xử lí click trên giỏ vé
    public void itemListCLick(Component com);
    
    // Xử lí xoá vé
    public void deleteTicket(int id);
    
    public void buyTicket(int id);
    
}