package com.king.httpservertest.logUtils.SocketUtils;

import java.net.Socket;

public interface SocketListener {

    public abstract void event_Connection(Socket client);

    public abstract void event_Recevied(Socket client,byte[] Data,int Length);

    public abstract void event_disConnection(Socket client);

    
}
