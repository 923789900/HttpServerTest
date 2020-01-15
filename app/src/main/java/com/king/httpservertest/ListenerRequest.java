package com.king.httpservertest;

import com.king.httpservertest.logUtils.Http.Interface.HttpListener;
import com.king.httpservertest.logUtils.Http.Request;
import com.king.httpservertest.logUtils.Http.Response;
import com.king.httpservertest.logUtils.SocketUtils.ClientSocket;
import com.king.httpservertest.logUtils.SocketUtils.SocketListener;

import java.net.Socket;

public class ListenerRequest implements HttpListener {


    @Override
    public void Event_ClientConnection(String IPAddress, int Prot) {
        LogUtils.logi("Connection  : IP " + IPAddress + "Prot : " + Prot);
    }

    @Override
    public void Event_ClientRequest(Request request) {
        try {
            Response res = new Response().setReturnContent(request.Content);
            request.SendMessage(res);
        } catch (Exception e) {
            LogUtils.logi("ListenerRequest->event_Recevied|Exception|ErrorMessage : " + e.getMessage());
        }
    }

    @Override
    public void Event_ClientDisConnected(String IPAddress, int Prot) {

    }
}
