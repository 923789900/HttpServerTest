package com.king.httpservertest.logUtils.Http.Interface;

import com.king.httpservertest.logUtils.Http.Request;

public interface HttpListener {

    public abstract void Event_ClientConnection(String IPAddress,int Prot);

    public abstract void Event_ClientRequest(Request request);

    public abstract void Event_ClientDisConnected(String IPAddress,int Prot);

}
