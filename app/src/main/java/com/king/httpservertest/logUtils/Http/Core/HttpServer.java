package com.king.httpservertest.logUtils.Http.Core;


import com.king.httpservertest.LogUtils;
import com.king.httpservertest.logUtils.Http.Interface.HttpListener;
import com.king.httpservertest.logUtils.Http.Request;
import com.king.httpservertest.logUtils.SocketUtils.ClientSocket;
import com.king.httpservertest.logUtils.SocketUtils.SocketListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private ServerSocket Server;

    //server port
    private int Port;

    private HttpListener listener;

    private HttpServer() {
    }

    /**
     * construct
     *
     * @param listener listener object
     * @throws IOException
     */
    public HttpServer(HttpListener listener) throws IOException {
        this.listener = listener;
    }

    /**
     * Server Listener event
     */
    private Thread ServerListener() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                accept();
            }
        });

        return th;
    }

    /**
     * read input stream to byte array Stream
     *
     * @param input
     * @return
     */
    private static ByteArrayOutputStream readInputStream(InputStream input) {
        byte[] buf = new byte[1024];
        int len = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        do {

            try {
                len = input.read(buf, 0, 1024);
                if (len == -1)
                {
                    break;
                }
                else if (len <= 1024) {
                    out.write(buf, 0, len);
                    if (len<1024)
                    {
                        break;
                    }
                } else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.loge("HttpClient->readInputStream|Exception|ErrorMessage :  " + e.getMessage());
            }

        } while (true);

        return out;

    }

    /**
     * get received byte buffer
     *
     * @return
     */
    private ByteArrayOutputStream getReceiveData(Socket client) {
        ByteArrayOutputStream Result = null;
        try {
            //获取用户的输入对象
            InputStream input = client.getInputStream();
            //读取用户传来的数据
            Result = readInputStream(input);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.loge("SocketServer->getReciveData|Exception|ErrorMessage : " + e.getMessage());
            Result = null;
        }

        return Result;
    }

    /**
     * launch client event
     * @param Client
     */
    private void launch(final Socket Client)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                ClientSocket client = new ClientSocket(Client);
                //用户连接事件
                listener.Event_ClientConnection(client.getAddress(),client.getPort());

                //用户收到数据
                Request req = new Request(Client);
                listener.Event_ClientRequest(req);


            }
        }).start();




    }

    /**
     * Forward Data
     */
    private void accept() {
        try {
            Socket Client = Server.accept();
            //发射用户
            launch(Client);
            //重载方法进行重复接收
            if (!Server.isClosed())
            {
                accept();
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.loge("SocketServer->accept|Exception|errorMessage : " + e.getMessage());
        }

    }

    /**
     * start server listener
     */
    public boolean Start(String hostName, int Port) {
        try {
            Server = new ServerSocket();
            Server.bind(new InetSocketAddress(hostName, Port));
            ServerListener().start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.loge("SocketServer->Start|Exception|errorMessage : " + e.getMessage());
            return false;
        }

    }

    /**
     * start server listener
     */
    public boolean Start(int Port) {
        try {
            Server = new ServerSocket(Port);
            ServerListener().start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.loge("SocketServer->Start|Exception|errorMessage : " + e.getMessage());
            return false;
        }

    }

    /**
     * stop server
     * @return
     */
    public boolean Stop()
    {
        try {
            Server.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.loge("SocketServer->Stop|Exception|errorMessage : " + e.getMessage());
            return false;
        }
    }



}
