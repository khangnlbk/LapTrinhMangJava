package InGameManage;

import GameClient.ListenServer;
import javax.swing.JFrame;

public class GameWindowDesign extends JFrame{
    public int wigthGameWindow=550;
    public int hightGameWindow=700;
    
    private ObjectLocation ol;
    private ListenServer client;
    private int color;
    private String name;
    
    public GameWindowDesign(){
        //comment
    }
    
    public GameWindowDesign(ListenServer client,int color, String name, ObjectLocation ol)
    {
        this.client=client;
        this.color= color;
        this.name= name;
        this.ol = ol;
        init();
        client.remote_Game(this);
    }

    private void init()
    {
        setSize(wigthGameWindow , hightGameWindow); //kich thuoc man hinh game
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        add(new InGameControl(this.getSize().width,this.getSize().height,ol, client));
    }
}
