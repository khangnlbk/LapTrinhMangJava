package PreGameManage;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class PlayerInfo extends JPanel {
	private static final int width = 200;
	private static final int height = 250;
	
	private JLabel PlayerName = new JLabel();
	private JLabel Avatar = new JLabel();
	
	public PlayerInfo(int x,int y){
            super();
            this.setLayout(null);
            this.setBounds(x,y,width, height);
            initComponents();
	}
	
	private void initComponents(){
            PlayerName.setBounds(0,0,width,50);
            PlayerName.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(102, 102, 102), new Color(102, 102, 102)));
            PlayerName.setHorizontalAlignment(SwingConstants.CENTER);
            Avatar.setBounds(0, 48, width,200);
            Avatar.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(102, 102, 102), new Color(102, 102, 102)));
            Avatar.setHorizontalAlignment(SwingConstants.CENTER);

            this.add(PlayerName);
            this.add(Avatar);
	}
        
        public void setIcon(int i)
        {
            IconManage.setIcon(Avatar, i);
        }
        
        public void setName(String Name)
        {
            PlayerName.setText("Player: "+Name);
        }
}
