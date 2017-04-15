package Room;

import GameClient.ListenServer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;

public class RoomManage extends JFrame {

    private JPanel contentPane;
    private ListRoomLayout roomListPanel;
    private OptionsLayout methodPanel;
    private ListenServer client;
    /**
     * Launch the application.
     */

    /**
     * Create the frame.
     */
    public RoomManage(ListenServer client) {
        this.client = client;
        init();
        this.client.remote_lobby(this);
    }
    private void init()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        
        roomListPanel = new ListRoomLayout(client);
        contentPane.add(roomListPanel);
        
        methodPanel = new OptionsLayout(client);
        contentPane.add(methodPanel);
        this.pack();
    }
    
    public void update_list_room()
    {
        roomListPanel.removeAllRoom();
        roomListPanel.addRooms(client.listRoom);
    }
    
    public void setEnableJoinButton(boolean active)
    {
        methodPanel.setJoinButton(active);
    }
    
    public void openRoomFrame(int i,boolean isBoss)
    {
        new PreGameManage.PreGameProcess(client,i,isBoss).setVisible(true);
        client.remote_lobby(null);
        this.dispose();
    }
}
