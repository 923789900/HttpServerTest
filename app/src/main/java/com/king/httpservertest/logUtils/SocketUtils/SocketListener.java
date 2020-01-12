package com.king.httpservertest.logUtils.SocketUtils;

import java.net.Socket;

public interface SocketListener {

    public abstract void event_Connection(ClientSocket client);

    public abstract void event_Recevied(ClientSocket client,byte[] Data,int Length);

    public abstract void event_disConnection(ClientSocket client);

    
}
