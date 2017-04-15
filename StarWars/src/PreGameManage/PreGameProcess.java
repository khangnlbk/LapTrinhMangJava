package PreGameManage;

import GameClient.ListenServer;
import GameServer.RoomManager;
import InGameManage.ObjectLocation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

public class PreGameProcess extends JFrame {
    private static final int width = 160;
    private static final int height = 80; 
    private ListenServer client;
    private JPanel contentPane;
    public PlayerInfo playerPanel1;
    public PlayerInfo playerPanel2;
    
    private JButton Start_button = new JButton("Start Game");
    private JButton Exit_button = new JButton("End Game");
    private JToggleButton Ready_button = new JToggleButton("Ready");

    private RoomManager roomManager;
    private int color;
    private boolean isBoss = true;
	
    public PreGameProcess(ListenServer client,int color,boolean isBoss) {
        this.client = client;
        this.color = color;
        init();
        this.client.remote_roomUI(this);
        setInfoPlayer(playerPanel1, client.getName());
        setInfoPlayer(playerPanel2, client.getchoosedRoom());
        this.isBoss = isBoss;
        checkBoss(this.isBoss);
    }
    
    private void init()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 615, 550);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        playerPanel1 = new PlayerInfo(50, 50);
        contentPane.add(playerPanel1);
        playerPanel2 = new PlayerInfo(360, 50);
        contentPane.add(playerPanel2);
     

        Start_button.setBounds(220, 320, width, height);
        contentPane.add(Start_button);
        
        Ready_button.setBounds(220, 320, width, height);
        contentPane.add(Ready_button);

        Exit_button.setBounds(220, 410, width, height);
        contentPane.add(Exit_button);
        this.setLocationRelativeTo(null);
        Exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ExitAction();
            }
        });

        Start_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Start_action();
            }
        });
        Ready_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Ready_action();
            }
        }
        );
        setIcon(this.color);
    }
    private void ExitAction()
    {
        client.getTransferMethod().SEND("OUT_ROOM");
        client.setWaitting();
        new Room.RoomManage(client).setVisible(true);
        client.remote_roomUI(null);
        this.dispose();
    }
    private void setInfoPlayer(PlayerInfo player,String name)
    {
        if(name == null) player.setName("Anonymous");
        else player.setName(name);
    }
    public void setEnemyInfo(String name)
    {
        setInfoPlayer(playerPanel2, name);
    }
    public void checkBoss(boolean isBoss)
    {
        if(isBoss == true)
        {
            Ready_button.setVisible(false);
            Start_button.setVisible(true);
            Start_button.setEnabled(false);
            Exit_button.setEnabled(true);
        }
        else
        {
            Start_button.setVisible(false);
            Ready_button.setVisible(true);
        }
    }

    public void setIcon(int color)
    {
        this.color = color;
        playerPanel1.setIcon(color);
        playerPanel2.setIcon((color+1)%2);
    }
    private void Ready_action()
    {
        if(Ready_button.isSelected())
        {
            client.getTransferMethod().SEND("READY");
            client.getTransferMethod().SEND("1");
            Exit_button.setEnabled(false);
        }
        else
        {
            client.getTransferMethod().SEND("READY");
            client.getTransferMethod().SEND("0");
            Exit_button.setEnabled(true);
        }
    }
    private void Start_action()
    {
        client.getTransferMethod().SEND("START");
        client.setWaitting();
    }
    public void setStartButtonEnable(int i)
    {
        if(i==0) Start_button.setEnabled(false); 
        if(i==1) Start_button.setEnabled(true);   
    }
    public void PlayGame(ObjectLocation ol)
    {
        if(Ready_button.isShowing()) 
        {
            Exit_button.setEnabled(true);
            Ready_button.setSelected(false);
        }
        if(Start_button.isShowing())
        {
            Start_button.setEnabled(false);
        }
        new InGameManage.GameWindowDesign(client,color,client.getName(),ol).setVisible(true);
        this.setVisible(false);
    }
}
