package PreGameManage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class IconManage {
    static public int black = 1;
    static public int white = 0;
    static public String[] Icon={
        "assets/plane_user_1.png",
        "assets/plane_user_2.png",
        "assets/Bullet.png",
        "assets/plane_enemy.png",
        "assets/explosion.png"};
    
    public static void setIcon(JLabel label, String fileName)
    {
        try {
            BufferedImage image = ImageIO.read(IconManage.class.getClassLoader().getResource(fileName));
            int x =label.getSize().width;
            int y =label.getSize().height;
            int ix =image.getWidth();
            int iy =image.getHeight();
            int dx=0;
            int dy=0;
            if(x /y > ix /iy){
                dy=y;
                dx=dy*ix /iy;
            }else{
                dx=x;
                dy=dx*iy/ix;
            }
            ImageIcon icon = new ImageIcon(image.getScaledInstance(dx, dy, BufferedImage.SCALE_SMOOTH));
            label.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(IconManage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void setIcon(JLabel label, int i)
    {
        try {
            BufferedImage image = ImageIO.read(IconManage.class.getClassLoader().getResource(IconManage.Icon[i]));
            int x =label.getSize().width;
            int y =label.getSize().height;
            int ix =image.getWidth();
            int iy =image.getHeight();
            int dx=0;
            int dy=0;
            if(x /y > ix /iy){
                dy=y;
                dx=dy*ix /iy;
            }else{
                dx=x;
                dy=dx*iy/ix;
            }
            ImageIcon icon = new ImageIcon(image.getScaledInstance(dx, dy, BufferedImage.SCALE_SMOOTH));
            label.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(IconManage.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(IconManage.Icon[i]);
        }
    }
    
}
