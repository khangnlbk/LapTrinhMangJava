package GameServer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Room_Client_list {
    private HashMap<String, SocketOfServer> Client_list;
    private HashMap<String, SocketOfServer> Playing_client;
    private HashMap<SocketOfServer,RoomManager> Room_list;
    public Room_Client_list() {
        Client_list = new HashMap<String, SocketOfServer>();
        Room_list = new HashMap<SocketOfServer,RoomManager>();
        Playing_client = new HashMap<String,SocketOfServer>();
    }

    public void addClient_list(String s,SocketOfServer SOCK)
    {
        Client_list.put(s, SOCK);
    }
    private void addRoom_list(SocketOfServer SOCK, RoomManager Room)
    {
        Room_list.put(SOCK, Room);
    }
    public void ClientToRoom(String s,RoomManager room)
    {
        SocketOfServer SOCK = removeClient(s);
        Playing_client.put(s, SOCK);
        addRoom_list(SOCK, room);
    }
    public void RoomToClient(SocketOfServer SOCK,boolean boo)
    {
        if(boo) Client_list.put(SOCK.getName(), SOCK);
        else SOCK.closePort();
        Playing_client.remove(SOCK.getName());
        Room_list.remove(SOCK);
    }


    public String[] getAllRoomName()
    {
        HashMap<RoomManager,String> temp= new HashMap<RoomManager, String>();
        int tempi=0;
        Set keys = Room_list.keySet();
        for(Iterator i = keys.iterator(); i.hasNext();)
        {
            SocketOfServer socktemp = (SocketOfServer) i.next();
            RoomManager roomtemp = Room_list.get(socktemp);
            temp.put(roomtemp, "A");
        }
        String[] array = new String[temp.size()];
        keys = temp.keySet();
        for(Iterator i = keys.iterator();i.hasNext();)
        {
            RoomManager roomtemp = (RoomManager) i.next();
            array[tempi]=roomtemp.getBoss().getName(); tempi++;
        }
        return array;
    }

    public HashMap getClient_list()
    {
        return Client_list;
    }

    public HashMap getRoom_list()
    {
        return Room_list;
    }

    public SocketOfServer getClient(String key)
    {
        return Client_list.get(key);
    }

    public RoomManager getRoom(SocketOfServer SOCK)
    {
        return Room_list.get(SOCK);
    }
    public RoomManager getRoom(String s)
    {
        SocketOfServer SOCK = Playing_client.get(s);
        return Room_list.get(SOCK);
    }

    private SocketOfServer removeClient(String s)
    {
        return Client_list.remove(s);
    }
}
