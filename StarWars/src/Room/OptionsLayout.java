package Room;

import GameClient.ListenServer;
import GameClient.Login_frame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OptionsLayout extends JPanel implements ActionListener{
    private static final int width = 200; //chieu rong cua button
    private static final int height = 400; //chieu dai button
    private ListenServer client;

    public JButton create_button = new JButton("Create Room");
    public JButton join_button = new JButton("Join Now");
    public JButton back_button = new JButton("Quit");

    public OptionsLayout(ListenServer client){
        super();
        this.client= client;
        this.setLayout(new GridLayout(0, 1));
        this.setPreferredSize(new Dimension(width, height));
        create_button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        join_button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        back_button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        this.add(Box.createVerticalStrut(100));
        this.add(create_button);
        this.add(Box.createVerticalStrut(100));
        this.add(join_button);
        this.add(Box.createVerticalStrut(100));
        this.add(back_button);
        this.add(Box.createVerticalStrut(100));

        join_button.setEnabled(false);
        create_button.addActionListener(this);
        join_button.addActionListener(this);
        back_button.addActionListener(this);
    }
    
    private void Create_Room()
    {
        client.getTransferMethod().SEND("CREATE_ROOM");
        client.setWaitting();
    }
    
    private void Join_Room(String room)
    {
        if(client.getchoosedRoom()!=null)
            if(client.getchoosedRoom().compareTo("")!=0)
            {
                client.getTransferMethod().SEND("JOIN_ROOM");
                client.getTransferMethod().SEND(room);
                client.setWaitting();
            }
    }
    
    public void setJoinButton(boolean isActive)
    {
        join_button.setEnabled(isActive);
    }
    
    public void logout()
    {
        client.logout();
        new Login_frame().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object ob = ae.getSource();
        if(ob == create_button)
            Create_Room();
        if(ob == join_button)
            Join_Room(client.getchoosedRoom());
        if(ob == back_button)
            logout();
    }	
}
