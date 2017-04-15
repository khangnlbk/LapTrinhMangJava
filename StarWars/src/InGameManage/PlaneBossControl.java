package InGameManage;

import InGameManage.ObjectLocation;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaneBossControl implements Runnable{
    private ArrayObjectPlaneBoss enemies;
    private int quantity;
    private ObjectLocation ol;
    private ArrayObjectExplode explo;
    public PlaneBossControl(ArrayObjectPlaneBoss enemies, ObjectLocation ol, ArrayObjectExplode explo)
    {
        this.enemies = enemies;
        this.ol = ol;
        this.explo = explo;
    }

    @Override
    public void run() {
        randomQuantity();
        createEnemy();
    }

    private int randomInt(int min,int max)
    {
        Random ran = new Random();
        return ran.nextInt(max-min+1)+min;
    }

    private void randomQuantity()
    {
        quantity = randomInt(1, 1);
    }
    private void createEnemy()
    {
        for(int i = 0 ;i < quantity; i++)
        {
            int x = randomInt(5,355);
            int speed = randomInt(10, 20);
            autoTranslate(x, 0, speed);
        }
    }

    public void autoTranslate(int x, int y, int speed) //tu dong chay Boss
    {
        ObjectPlaneBoss e = new ObjectPlaneBoss(x, 0, speed);
        enemies.add(e);
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while(e.getY()<610) //chu y cho nay //y=610, may bay den het man hinh thi chet
                {
                    try {
                        e.translate();
                        check(e);
                        Thread.sleep(speed);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PlaneBossControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(!e.isDead())
                    ol.lost();
                enemies.remove(e);
            }
        });
        th.start();
    }

    private void check(ObjectPlaneBoss e) //kiem tra 2 may bay va cham nhau 
    {
        int x=e.getX(),y=e.getY();
        boolean b = e.isDead();
        for(int plane = 0 ; plane < 2 ; plane++)
            if(!ol.isDead(plane)&&!b) //neu 2 may bay cham nhau
                if((y<=ol.getY(plane)&&ol.getY(plane)<=y+70)||(y<=ol.getY(plane)+50&&ol.getY(plane)<=y+20))
                    if((x<=ol.getX(plane)&&ol.getX(plane)<=x+70)||(x<=ol.getX(plane)+50&&ol.getX(plane)<=x+20))
                    {
                        System.out.println(x+" "+ol.getX(plane));
                        ol.die(plane);
                        explo.add(new ObjectExplode(ol.getX(plane), ol.getY(plane)));
                    }
        if(ol.isDead(0)&&ol.isDead(1)&&!e.isDead()) ol.lost();
    }
}
