package com.king.httpservertest;

import com.king.httpservertest.logUtils.Http.Request;
import com.king.httpservertest.logUtils.Http.Response;
import com.king.httpservertest.logUtils.SocketUtils.ClientSocket;
import com.king.httpservertest.logUtils.SocketUtils.SocketListener;

import java.net.Socket;

public class ListenerRequest implements SocketListener {
    @Override
    public void event_Connection(ClientSocket client) {
            LogUtils.logi("Client Connection : "+client.getAddress());
    }

    @Override
    public void event_Recevied(ClientSocket client, byte[] Data, int Length) {
        try
        {
            Request req = new Request(client,Data,Length);
            Response res = new Response().setReturnContent(req.Content);
            req.SendMessage(res);
        }catch (Exception e)
        {
            LogUtils.logi("ListenerRequest->event_Recevied|Exception|ErrorMessage : "+e.getMessage());
        }

    }

    @Override
    public void event_disConnection(ClientSocket client) {

    }






}
