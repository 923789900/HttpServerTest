package com.king.httpservertest;

import com.king.httpservertest.logUtils.SocketUtils.SocketListener;

import java.net.Socket;

public class ListenerRequest implements SocketListener {
    @Override
    public void event_Connection(Socket client) {
            LogUtils.logi("Client Connection : "+client.getInetAddress().getHostAddress());
    }

    @Override
    public void event_Recevied(Socket client, byte[] Data, int Length) {
        LogUtils.logi("Client Recevied Data Length : "+Length +", Data : "+Data.toString());
    }

    @Override
    public void event_disConnection(Socket client) {

    }

}
