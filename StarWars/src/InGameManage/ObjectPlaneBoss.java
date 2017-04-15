package InGameManage;

public class ObjectPlaneBoss {
    private int loc[];
    private int speed;
    private boolean dead;

    public ObjectPlaneBoss(int x,int y,int speed)
    {
        loc = new int[2];
        loc[0]=x;
        loc[1]=y;
        dead = false;
        this.speed =speed;
    }

    public void setY(int y)
    {
        loc[1]=y;
    }

    public int getX()
    {
        return loc[0];
    }

    public int getY()
    {
        return loc[1];
    }

    public int speed()
    {
        return speed;
    }

    public void translate() 
    { 
        if(getY()<700)  //chu y cho nay:set up may bay di het man hinh thi game over
            loc[1]+=1;
    }
    public void dead()
    {
        dead = true;
    }

    public boolean isDead()
    {
        return dead;
    }
}
