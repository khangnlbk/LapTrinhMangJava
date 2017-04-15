
package InGameManage;

import GameClient.ListenServer;
import PreGameManage.IconManage;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class InGameControl extends JPanel{
    private static final int sizeBullet=1000000;
    private ListenServer client;
    private ObjectLocation ol;
    private int width,height;
    private JLabel plane[] = new JLabel[2];
    private ArrayBulletLabel bul_label;
    private ArrayPlaneBossLabel bos_label;
    public InGameControl(int width,int height,ObjectLocation ol, ListenServer client)
    {
        this.width=width;
        this.height=height;
        this.ol = ol;
        this.client = client;
        init();
        bul_label = new ArrayBulletLabel(this);
        bos_label = new ArrayPlaneBossLabel(this);
        client.remote_inGame(this);
        ready();
    }
    @Override
    protected void paintComponent(Graphics grphcs) {
        ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("assets/Background.png"));
        grphcs.drawImage(icon.getImage(), 0, 0, width, height, null);
        setOpaque(false);
        super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.
    }
    private void init()
    {
        setSize(width, height);setLayout(null);this.setFocusable(true);
        plane[0] = new JLabel();plane[1] = new JLabel();setPlanesLocation();
        IconManage.setIcon(plane[0], 0);IconManage.setIcon(plane[1], 1);
        add(plane[0]);add(plane[1]);
    }
    private void ready()
    {
        
        JLabel lb = new JLabel();
        lb.setBounds(230, 250, 50, 50); // set up vi tri Clock
        lb.setBackground(new Color(0, 191, 255));
        lb.setForeground(new Color(238, 238, 238));
        lb.setFont(new java.awt.Font("Sylfaen", 1, 40));
        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);add(lb);
        Thread x = new Thread(new Runnable() {
            @Override
            public void run() {
                int t =3;
                for(int i = 0 ; i <3; i++)
                {
                    try {
                       
                        lb.setText(""+t);
                        repaint();
                        Thread.sleep(1000);
                        t--;
                        if(t==2){
                            
                            client.getTransferMethod().SEND("LETGO");
                        }
                       
       
                    } catch (InterruptedException ex) {
                        Logger.getLogger(InGameControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                remove(lb); repaint();
                addKeyListener(new KeyboardControl(client));
            }
        });
        x.start();
    }

    private void setPlanesLocation()
    {
        for(int i = 0;i < 2; i++){
            if(ol.isDead(i)) plane[i].setVisible(false);
            else plane[i].setBounds(ol.getX(i),ol.getY(i),50,50);
        }
            
    }

    public void animation(ObjectLocation ol,ArrayObjectBullet bul, ArrayObjectPlaneBoss e,ArrayObjectExplode explo)
    {
        Thread x1 = shoot(bul),x2 = createEnemy(e);
        this.ol=ol;
        setPlanesLocation();
        repaint();
        try {
            x1.join();
            x2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(InGameControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        x1.start();
        x2.start();
        explo.animations(this);
        if(ol.isLost()){
            JOptionPane.showMessageDialog(null, "Game Over");
            client.gameover();
        }
    }
    private Thread shoot(ArrayObjectBullet bullets)
    {
        Thread x1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int t = bul_label.size();
                
                if(bullets.size() < bul_label.size())
                {
                    for(int i =  0 ; i< bullets.size(); i++)
                    {
                        bul_label.get(i).setLocation(bullets.get(i).getX(), bullets.get(i).getY());
                        if(!bul_label.get(i).isShowing()) bul_label.get(i).showing();
                    }
                    if(bul_label.size()>sizeBullet&&sizeBullet>=bullets.size())
                        while(bul_label.size()>sizeBullet)
                        {
                            remove(bul_label.get(sizeBullet));
                            bul_label.remove(sizeBullet);
                        }
                    for(int i = bullets.size();i<bul_label.size();i++)
                        bul_label.get(i).hidden(); //khi Bullet di qua man hinh thi hidden()
                }
                else
                {
                    for(int i = 0 ; i < t;i++)
                    {
                        BulletLabel ltemp = bul_label.get(i);
                        ltemp.setLocation(bullets.get(i).getX(), bullets.get(i).getY());
                        if(!ltemp.isShowing()) ltemp.showing();
                    }
                    for(int i=0;i<bullets.size()-t;i++)
                    {
                        BulletLabel bl = new BulletLabel(bullets.get(i+t).getX(), bullets.get(i+t).getY());
                        bl.showing();bul_label.add(bl);add(bl);
                    }
                }
                repaint();
            }
        });
        return x1;
    }

    private Thread createEnemy(ArrayObjectPlaneBoss e)
    {
        Thread x1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int t = bos_label.size();
                if(e.size()>bos_label.size())
                {
                    for(int i = 0 ; i < t;i++)
                    {
                        EnemyLabel ltemp = bos_label.get(i);
                        ltemp.setLocation(e.get(i).getX(), e.get(i).getY());
                        if(!ltemp.isShowing()) ltemp.showing();
                    }
                    for(int i=0;i<e.size()-t;i++)
                    {
                        EnemyLabel el = new EnemyLabel(e.get(i+t).getX(), e.get(i+t).getY());
                        el.showing();bos_label.add(el);add(el);
                    }
                }
                else
                {
                    for(int i =  0 ; i< e.size(); i++)
                    {
                        bos_label.get(i).setLocation(e.get(i).getX(), e.get(i).getY());
                        if(!bos_label.get(i).isShowing()) bos_label.get(i).showing();
                    }
                    if(bos_label.size()>sizeBullet&&sizeBullet>=e.size())
                        while(bos_label.size()>25)
                        {
                            remove(bos_label.get(25));
                            bos_label.remove(25);
                        }
                    for(int i = e.size();i<bos_label.size();i++)
                        bos_label.get(i).hidden();
                }
                repaint();
            }
        });
        return x1;
    }

    
}
