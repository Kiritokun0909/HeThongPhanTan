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
import model.Account;
import java.awt.Component;
import java.util.List;

public interface EventItem {

    public void itemRequest( Ticket item, Account acc, int status);
    
    public void itemListRequset( Ticket item, Account acc, int status);
    
    public void exitClient(String ip);
    
    public void setTicketOfList(int id, boolean sold, String username);
    
     public void setListTicket(List<Ticket> listTK1);
    
    public List<Ticket> getListTicket() ;

    public void resetSelect(int id, String username1, String username2);
    
}