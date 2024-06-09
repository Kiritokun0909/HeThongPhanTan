/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import model.Account;
import model.Ticket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import swing.EventItem;

/**
 *
 * @author Admin
 */
class ReadServer extends Thread {

    private Socket socket;
    private ArrayList<Map<Account, Integer>> task;
    private ArrayList<Socket> listClient;
    private String DB_URL = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=VEMAYBAY"
            + ";encrypt=true;trustServerCertificate=true;";
    private String USER_NAME = "sa";
    private String PASSWORD = "kc";

    private EventItem event;

    public void setEvent(EventItem event) {
        this.event = event;
    }
    
    public ReadServer() {
    }
    public ReadServer(ArrayList<Map<Account, Integer>> task, ArrayList<Socket> listClient) throws SQLException {
        this.task = task;
        this.listClient = listClient;
    }

    public ReadServer(Socket socket, ArrayList<Map<Account, Integer>> task, ArrayList<Socket> listClient) throws SQLException {
        this.socket = socket;
        this.task = task;
        this.listClient = listClient;
    }

    // trả về danh sach ve cho client
    public List<Ticket> DanhSachVe() throws SQLException {
        List<Ticket> listTicket = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        Statement statement = connection.createStatement();
        String stringQuery = "SELECT * FROM Ticket";
        ResultSet resultSet = statement.executeQuery(stringQuery);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            boolean sold = resultSet.getBoolean(2);
            var userName = resultSet.getNString(3);
            Ticket ticket = new Ticket(id, sold, userName);
            listTicket.add(ticket);
        }
        return listTicket;
    }

    public int Login(String userName, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        Statement statement = connection.createStatement();
        String stringQuery = "SELECT * FROM Account WHERE USERNAME = '" + userName + "' AND PASSWORD = '" + password + "'";
        ResultSet resultSet = statement.executeQuery(stringQuery);
        if (resultSet.next() == false) {
            return 0;
        }
        return 1;
    }

    public int DatVe(String userName, int soGhe) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        Statement statement = connection.createStatement();
        String stringQuery = "SET TRANSACTION ISOLATION LEVEL SERIALIZABLE BEGIN TRAN "
                +"UPDATE Ticket SET USERNAME = '" + userName + "', SOLD = " + 1 + " WHERE ID = '" + soGhe + "' COMMIT";
        int rowUpdate = statement.executeUpdate(stringQuery);
        return rowUpdate;
    }
    public int resetTicket() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        Statement statement = connection.createStatement();
        String stringQuery = "SET TRANSACTION ISOLATION LEVEL SERIALIZABLE BEGIN TRAN "
                +"UPDATE Ticket SET USERNAME = NULL, SOLD = 'False' COMMIT";
        int rowUpdate = statement.executeUpdate(stringQuery);
        return rowUpdate;
    }
    
    public int blockTicket(String userName, int soGhe) throws SQLException {
        if (!userName.equals("NULL")) {
            userName = "'"+userName+"'";
        }
        Connection connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        Statement statement = connection.createStatement();
        String stringQuery = "SET TRANSACTION ISOLATION LEVEL SERIALIZABLE BEGIN TRAN "
                +"UPDATE Ticket SET USERNAME = "+userName+", SOLD = 'False'"+ " WHERE ID = '" + soGhe + "' COMMIT";
        System.out.println(stringQuery);
        int rowUpdate = statement.executeUpdate(stringQuery);
        return rowUpdate;
    }
    
    @Override
    public void run() {
        // lấy dữ liệu được gửi từ client
        DataInputStream dataInputStream = null; // ban đầu dữ liệu gửi về là null
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            String message = dataInputStream.readUTF();
            System.out.println("Client " + socket.getInetAddress().getHostAddress() + " đã gửi yêu cầu " + message);
            if (message.contains("xemve")) {
                // gửi dữ liệu về client
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(DanhSachVe().toString()); // gửi dữ liệu về cho client đó
            } 
            else if (message.contains("capnhat")) {
                // gửi dữ liệu về client
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                this.event.setListTicket(DanhSachVe());
                dataOutputStream.writeUTF(this.event.getListTicket().toString()); // gửi dữ liệu về cho client đó
            } 
            else if (message.contains("huyve")) {// chuỗi gửi về phải có dạng huyve usernam soghe
                String[] s = message.split(" "); // chuỗi login gửi về có dạng huyve usernam soghe -> tach thanh ['huyve', 'username', 'soghe']
                String userName = s[1];
                int soGhe = Integer.parseInt(s[2]);
                Iterator<Map<Account, Integer>> iterator = task.iterator();
                while (iterator.hasNext()) {
                    Map<Account, Integer> map = iterator.next();
                    for (Map.Entry<Account, Integer> entry : map.entrySet()) {
                        Account key = entry.getKey();
                        Integer value = entry.getValue();
                        if (key.getUserName().equals(userName) && soGhe == value) {
                            iterator.remove(); // Sử dụng iterator để xóa phần tử
                            System.out.println("Đã hủy vé " + value + " cho người dùng " + userName);
                        } else {
                            // Xử lý trường hợp người dùng đã đặt và lưu trong db
                        }
                    }
                }
                System.out.println("Danh sách chọn vé còn");
                int d = 0;
                String username2 ="";
                for (Map<Account, Integer> m : task) {
                    for (Map.Entry<Account, Integer> entry : m.entrySet()) {
                        // System.out.println(entry.getKey().getUserName() + " đã chọn vé " + entry.getValue());
                        if (entry.getValue() == soGhe) {
                            d = 1; // nếu đã tồn tại 1 người đặt vé đó thì trả về là phải đợi
                            this.event.setTicketOfList(soGhe, false, entry.getKey().getUserName());
                            username2 = entry.getKey().getUserName();
                        }
                    }
                    if(d == 1) break;
                }
                int dl = 0;
                if (d == 0) {   //Trao vé mua cho người đầu tiên đang chờ đặt ghế này
                    this.event.itemRequest(new Ticket(soGhe, false, null), new Account(userName, null), 0);
                    this.event.setTicketOfList(soGhe, false, null);
                    dl = blockTicket("NULL", soGhe);
                }
                else {  //Reset ghế này khi không ai chọn
                    this.event.resetSelect(soGhe, userName, username2);
                    dl = blockTicket(username2, soGhe);
                }
                System.out.println(dl);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                String s1 = "Bạn đã hủy vé " + soGhe;
                dataOutputStream.writeUTF(s1); // gửi dữ liệu về cho client đó
            } 
            else if (message.contains("chonve")) { // user chọn vé thì cho vào queue // testok
                // chuỗi chọn vé gửi về có dạng chonve username soghe (lưu ý mỗi lần 1 lưu 1 ghế)
                String[] tmp = message.split(" "); //tách chuỗi gửi về thành ['chonve', 'username', 'soghe']
                //lúc này chỉ cần add vào queue để xử lí nếu người dùng yêu cầu đặt vé
                Account account = new Account();
                account.setUserName(tmp[1]);
                int soGhe = Integer.parseInt(tmp[2]);
                Map<Account, Integer> map = new HashMap<>();
                map.put(account, soGhe);

                task.add(map);// add yêu cầu vào queue
                int d = 0;
                for (Map<Account, Integer> m : task) {
                    for (Map.Entry<Account, Integer> entry : m.entrySet()) {
                        if (!entry.getKey().getUserName().equals(account.getUserName()) && entry.getValue() == soGhe) {
                            d = 1; // nếu đã tồn tại 1 người đặt vé đó thì trả về là phải đợi
                        }
                        System.out.println(entry.getKey().getUserName() + " đã chọn vé " + entry.getValue());
                    }
                }
                if (d == 0) {
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    this.event.itemRequest(new Ticket(soGhe, false, account.getUserName()), account, 1);
                    //this.event.itemListRequset(new Ticket(soGhe,false,account.getUserName()), account, 1);
                    this.event.setTicketOfList(soGhe, false, account.getUserName());
                    int dl = blockTicket(account.getUserName(), soGhe);
                    System.out.println(dl);
                    System.out.println("checkadasfsad");
                    String s = soGhe + ", 1";
                    dataOutputStream.writeUTF(s); // gửi dữ liệu về cho client đó
                } else {
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    this.event.itemListRequset(new Ticket(soGhe, false, account.getUserName()), account, 2);
                    String s = soGhe + ", 2";
                    dataOutputStream.writeUTF(s); // gửi dữ liệu về cho client đó
                }
            } 
            else if (message.contains("datve")) {// chuỗi gửi về phải có dạng datve username soghe
                String[] tmp = message.split(" "); // tách chuỗi thành ['datve', 'username', 'soghe']
                String userName = tmp[1];
                int soGhe = Integer.parseInt(tmp[2]);
                int d = 0;
                int d1 = 0;
                if (!task.isEmpty()) {
                    Iterator<Map<Account, Integer>> iterator = task.iterator();
                    while (iterator.hasNext()) {
                        Map<Account, Integer> map = iterator.next();
                        for (Map.Entry<Account, Integer> entry : map.entrySet()) {
                            Account key = entry.getKey();
                            Integer value = entry.getValue();
                            if (soGhe == value && !key.getUserName().equals(userName)) {
                                d++; // người dùng đến sau nhưng đặt cùng vé của người đến trước thì d tăng
                            }
                            if (key.getUserName().equals(userName) && soGhe == value && d == 0) {
                                d1 = DatVe(userName, soGhe);
                                if (d1 == 1) {
                                    iterator.remove(); // Sử dụng iterator để xóa phần tử
                                    System.out.println("Đã đặt vé " + value + " cho người dùng " + userName);
                                    this.event.setTicketOfList(value, true, userName);
                                    this.event.itemRequest(new Ticket(value, true, userName), new Account(userName, null), 3);
                                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                                    String s = "Bạn đã đặt thành công vé " + soGhe;
                                    dataOutputStream.writeUTF(s); // gửi dữ liệu về cho client đó
                                    break; // lấy phần tử đầu tiên trùng rồi break luôn
                                } else {
                                    System.out.println("Chưa thể đặt vé cho người này");
                                }
                            } else {
                                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                                String s = "Bạn phải chờ đặt vé " + soGhe;
                                dataOutputStream.writeUTF(s); // gửi dữ liệu về cho client đó
                            }
                        }
                    }
                }
                // Xóa các client đang chờ của ghế đã được đặt thành công
                if (d == 1) {
                    Iterator<Map<Account, Integer>> iterator = task.iterator();
                    while (iterator.hasNext()) {
                        Map<Account, Integer> map = iterator.next();
                        for (Map.Entry<Account, Integer> entry : map.entrySet()) {
                            Account key = entry.getKey();
                            Integer value = entry.getValue();
                            if (soGhe == value) {
                                iterator.remove(); // Sử dụng iterator để xóa phần tử
                                System.out.println("Đã hủy vé " + value + " cho người dùng " + key.getUserName());
                            } else {
                                // Xử lý trường hợp người dùng đã đặt và lưu trong db
                            }
                        }
                    }
                }
            } 
            else if (message.contains("login")) {
                // chuỗi login gửi về có dạng login username pass -> tach thanh ['login', 'username', 'pass']
                String[] account = message.split(" ");
                String userName = account[1];
                String pass = account[2];
                int check;
                if (Login(userName, pass) == 1) {//login thanh cong
                    check = 1;
                } else { //login that bai
                    check = 0;
                }
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(String.valueOf(check)); // gửi dữ liệu về cho client đó
            } 
            else if (message.contains("ngatketnoi")) {
                System.out.println("Client " + socket.getInetAddress().getHostAddress() + "  đã ngắt kết nối");
                for (Socket socket1 : listClient) {
                    if (socket1.getInetAddress().getHostAddress().equals(socket.getInetAddress().getHostAddress())) {
                        listClient.remove(socket1);
                        break;
                    }
                }
                this.event.exitClient(socket.getInetAddress().getHostAddress());
                socket.close();
                System.out.println("Trong danh sách client, còn những client sau: ");
                for (Socket socket1 : listClient) {
                    System.out.println("Client: " + socket1.getInetAddress().getHostAddress());
                }
            }
            dataInputStream.close();
        } catch (IOException ex) {
            try {
                socket.close();
            } catch (IOException ex1) {
                Logger.getLogger(ReadServer.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ReadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReadServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
