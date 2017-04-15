package InGameManage;

import PreGameManage.IconManage;
import InGameManage.InGameControl;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

public class ArrayObjectExplode extends ArrayList<ObjectExplode>{
    public ArrayObjectExplode(){
        //commnent
    }

    public void animations(InGameControl g)
    {
        if(this.size()>0)
            for(ObjectExplode e : this)
                animation(e, g);
    }
    private void animation(ObjectExplode e,InGameControl g)
    {
        Thread x = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JLabel label = new JLabel();
                    g.add(label);
                    for(int i = 0; i < 25 ; i+=5)
                    {
                        label.setBounds(e.x-i, e.y-i, 50+2*i, 50+2*i);
                        IconManage.setIcon(label, 4);
                        g.repaint();
                        if(i==20)
                            Thread.sleep(40);
                        Thread.sleep(20);
                    }
                    g.remove(label);
                    g.repaint();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ArrayObjectExplode.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        x.start();
    }
}
