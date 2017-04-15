package InGameManage;

import PreGameManage.IconManage;
import javax.swing.JLabel;

public class BulletLabel extends JLabel {
    private int x , y;
    public BulletLabel(int x, int y)
    {
        this.x= x;
        this.y= y;
        init();
    }
    private void init()
    {
        setLocation(x, y);
        IconManage.setIcon(this, 2);
        hidden();
    }
    public void setLocation(int x,int y) //vi tri xuat phat cua vien dan
    {
        setBounds(x,y,20,20);
    }
    public void showing()
    {
        setVisible(true);
    }
    public void hidden()
    {
        setVisible(false);
    }
}
