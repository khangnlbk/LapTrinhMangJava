
package InGameManage;

import java.util.ArrayList;

public class ArrayPlaneBossLabel extends ArrayList<EnemyLabel>{
    public ArrayPlaneBossLabel(InGameControl g)
        {
            for(int i =0 ; i < 10;i++)
            {
                EnemyLabel e = new EnemyLabel(0, 0);
                add(e);g.add(e);
            }
        }
}
