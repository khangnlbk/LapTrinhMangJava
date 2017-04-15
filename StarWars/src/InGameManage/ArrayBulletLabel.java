package InGameManage;

import java.util.ArrayList;

public class ArrayBulletLabel extends ArrayList<BulletLabel>{
    public ArrayBulletLabel(InGameControl g){
            for(int i =0 ; i< 20;i++)
            {
                BulletLabel label = new BulletLabel(0,0);
                add(label);g.add(label);
            }
        }
}
