package com.king.httpservertest.logUtils.SocketUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocket extends Socket {


    /**
     * send data to Server
     * @param Message
     * @throws IOException
     */
    public void SendData(byte[] Message) throws IOException {
        OutputStream out =  super.getOutputStream();
        out.write(Message,0,Message.length);
        out.flush();
    }


}
