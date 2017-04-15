
package InGameManage;

import GameClient.ListenServer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class KeyboardControl extends KeyAdapter{
    private ListenServer client;
    public static int NO_ACTION=0,LEFT=1,RIGHT=2,UP=3,DOWN=4,FIRE=5;
    private int ACTION=0;
    private Timer timer;
    public KeyboardControl(ListenServer client )
    {
        this.client = client;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        if(key == KeyEvent.VK_LEFT)
        {
            if(ACTION==1) return;
            ACTION = 1;
        }
        if(key == KeyEvent.VK_RIGHT)
        {
            if(ACTION==2) return;
            ACTION=2;
        }
        if(key == KeyEvent.VK_UP)
        {
            if(ACTION ==3 ) return;
            ACTION=3;
        }
        if(key == KeyEvent.VK_DOWN)
        {
            if(ACTION ==4 ) return ;
            ACTION=4;
        }
        if(key == KeyEvent.VK_SPACE)
        {
            if(ACTION == 5) return;
            ACTION=5;
        }
        client.getTransferMethod().SEND("ACTION");
        client.getTransferMethod().SEND(ACTION);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(ACTION ==0 ) return;
        ACTION =0;
        client.getTransferMethod().SEND("ACTION");
        client.getTransferMethod().SEND(ACTION);
    }
}
