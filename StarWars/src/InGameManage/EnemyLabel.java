package InGameManage;

import PreGameManage.IconManage;
import javax.swing.JLabel;

public class EnemyLabel extends JLabel{
    private int x, y;
    public EnemyLabel(int x, int y)
    {
        this.x = x;
        this.y = y;
        init();
    }
    private void init()
    {
        setLocation(x, y);
        IconManage.setIcon(this, 3);
        setVisible(false);
    }

    public void setLocation(int x, int y)
    {
        setBounds(x, y, 70, 70);
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
