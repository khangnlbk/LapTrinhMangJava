package GameServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameServer {
    private static final int PORT = 9999;

    /**
     * @param args the command line arguments
     */
    static Room_Client_list room_client_list = new Room_Client_list();
    public static void main(String[] args) {
        
        try {
            ServerSocket SERVER = new ServerSocket(PORT);
            System.out.println("Listenning Client...");
            while(true)
            {
                Socket SOCK = SERVER.accept();
                System.out.println("Connect Success To "+ SOCK.getPort());
                SocketOfServer SOCK_ID = new SocketOfServer();
                SOCK_ID.SERVER_ACCEPT_SocketID(SOCK);
                ListenClientRequest ser_cli = new ListenClientRequest(SOCK_ID, room_client_list);
                Thread x = new Thread(ser_cli);
                x.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
