package com.king.httpservertest.logUtils.SocketUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSocket {

    private Socket Client = null;

    private ClientSocket()
    {
        Client = new Socket();
    }


    public ClientSocket(Socket client)
    {
        Client = client;
    }

    /**
     * send data to Server
     * @param Message
     * @throws IOException
     */
    public void SendData(byte[] Message) throws IOException {
        OutputStream out =  Client.getOutputStream();
        out.write(Message,0,Message.length);
        out.flush();
    }

    /**
     * Connect to Remote Server
     * @param Host Host Name
     * @param Port Remote Port
     * @throws IOException
     */
    public void connect(String Host,int Port) throws IOException {
        Client.connect(new InetSocketAddress(Host,Port),15000);
    }

    public void disConnect() throws IOException {
        Client.close();
    }



}
