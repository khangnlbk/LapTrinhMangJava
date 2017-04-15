package Room;

import GameClient.ListenServer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.EtchedBorder;

public class ButtonListRoom extends JButton {	
	private String text;
    private ListenServer client;
    private static final int width = 50; //button in listRoom
    private static final int height = 40; //button in listRoom
	
	public ButtonListRoom(String text, ListenServer client){
            super(text);
            this.client = client;
            this.text =text;
            this.setPreferredSize(new Dimension(width, height));
            
            EtchedBorder border = new EtchedBorder(EtchedBorder.RAISED, new Color(255, 0, 0), new Color(255, 0, 0));
            this.setBorder(border);
            this.setFont(new java.awt.Font("Verdana", 1, 15));
            
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    client.setChoosedRoom(text);
                    if(text!=null)
                        if(text.compareTo("")!=0)
                            client.setEnableJoinButton(true);
                }
            });
	}
}