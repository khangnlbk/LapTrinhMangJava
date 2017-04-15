package GameClient;

import Room.RoomManage;
import PreGameManage.PreGameProcess;
import InGameManage.ArrayObjectBullet;
import InGameManage.ArrayObjectPlaneBoss;
import InGameManage.ArrayObjectExplode;
import com.google.gson.Gson;
import InGameManage.GameWindowDesign;
import InGameManage.ObjectLocation;
import InGameManage.InGameControl;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ListenServer implements Runnable{
    private SocketOfClient socketOfClient;
    private String choosedRoom = null;
    public boolean isIngame = false;
    public ListenServer(Socket SOCK)
    {
        this.SOCK = SOCK;
        socketOfClient = new SocketOfClient(SOCK);
    }
    public void remote_roomUI(PreGameProcess roomUI)
    {
        this.roomUI = roomUI;
    }
    public void remote_lobby(RoomManage Lobby)
    {
        this.Lobby = Lobby;
    }
    public void remote_Login_frame(Login_frame login_frame)
    {
        this.login_frame = login_frame;
    }
    public void remote_Game(GameWindowDesign game)
    {
        this.game = game;
    }
    public void remote_inGame(InGameControl ingame)
    {
        this.ingame = ingame;
    }
    public String getchoosedRoom()
    {
        return choosedRoom;
    }
    public void setChoosedRoom(String s)
    {
        choosedRoom = s;
    }
    public SocketOfClient getTransferMethod()
    {
        return socketOfClient;
    }

    public boolean isWaittingServer()
    {
        return waitting;
    }

    public void setNotWaitting()
    {
        waitting = false;
    }

    public void setWaitting()
    {
        waitting = true;
        while(isWaittingServer())System.out.print("");
    }
    public void setClientName(String s)
    {
        client_name =s;
    }
    public String getName()
    {
        return client_name;
    }
    public void update_room() throws IOException
    {
        int ii = socketOfClient.RECEIVE_i();
        listRoom.clear();
        for(int i=0;i<ii;i++)
            listRoom.add(socketOfClient.RECEIVE_s());
        setNotWaitting();
    }

    public void setEnableJoinButton(boolean active)
    {
        Lobby.setEnableJoinButton(active);
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                String REQUIRE = socketOfClient.RECEIVE_s();
                if(REQUIRE== null) return;
                System.out.println(REQUIRE);
                switch(REQUIRE)
                {
                    case "LOGIN_SUCCESS":
                        login_frame.LoginSuccess(true);
                    case "UPDATE_ROOM":
                        update_room();
                        while(Lobby == null) System.out.print("");
                        Lobby.update_list_room();
                        break;
                    case "CREATE_SUCCESS":
                        setNotWaitting();
                        Lobby.openRoomFrame(0,true);
                        break;
                    case "JOIN_SUCCESS":
                        int temp = socketOfClient.RECEIVE_i();
                        setNotWaitting();
                        Lobby.openRoomFrame(temp,false);
                        break;      
                    case "LOGIN_FALSE":
                        login_frame.LoginSuccess(false);
                    case "CREATE_FALSE":
                    case "JOIN_FALSE":
                        setNotWaitting();
                        break;
                    case "JOIN_ROOM":
                        roomUI.setEnemyInfo(socketOfClient.RECEIVE_s());
                        break;
                    case "OUT_ROOM":
                        OUT_ROOM();
                        break;
                    case "READY":
                        roomUI.setStartButtonEnable(socketOfClient.RECEIVE_i());
                        break;
                    case "START":
                        String start_json = socketOfClient.RECEIVE_s();
                        setNotWaitting();
                        START_GAME(start_json);
                        break;
                    case "PLAYING":
                        String plane=socketOfClient.RECEIVE_s(),bullets=socketOfClient.RECEIVE_s();
                        String enemies=socketOfClient.RECEIVE_s();String explodes = socketOfClient.RECEIVE_s();
                        DO_ANIMATION(plane,bullets,enemies,explodes);
                    default: break;
                }
            } catch (IOException ex) {}
        }
    }
    private void OUT_ROOM()
    {
        if(isIngame== true)
        {
            isIngame = false;
            roomUI.setVisible(true);
            game.dispose();
            game = null;
        }
        roomUI.setEnemyInfo(null);
        roomUI.checkBoss(true);
    }

    private void START_GAME(String s)
    {
        Gson gson = new Gson();
        ObjectLocation ol = gson.fromJson(s, ObjectLocation.class);
        roomUI.PlayGame(ol);
        isIngame = true;
    }
    public void gameover()
    {
        game.dispose();
        roomUI.setVisible(true);
    }
    public void logout()
    {
        if(Lobby != null) Lobby.dispose();
        Lobby = null;
    }

    private void DO_ANIMATION(String plane,String bullets,String enemies,String explodes)
    {
        Gson gson = new Gson();
        ObjectLocation ol = gson.fromJson(plane,ObjectLocation.class);
        ArrayObjectBullet bs = gson.fromJson(bullets,ArrayObjectBullet.class);
        ArrayObjectPlaneBoss e = gson.fromJson(enemies,ArrayObjectPlaneBoss.class);
        ArrayObjectExplode explo = gson.fromJson(explodes, ArrayObjectExplode.class);
        ingame.animation(ol,bs,e,explo);
    }

    public ArrayList listRoom = new ArrayList();
    private RoomManage Lobby;
    private Login_frame login_frame;
    private PreGameProcess roomUI;
    private boolean waitting = true;
    private Socket SOCK;
    private String client_name;
    private GameWindowDesign game;
    private InGameControl ingame;
}
