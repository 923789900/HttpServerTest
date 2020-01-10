package com.king.httpservertest.logUtils.Http;

import com.king.httpservertest.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpClient  {

    protected Socket Client;

    protected HttpClient(){}
    protected HttpClient(Socket client) {
        Client = client;
    }


    /**
     * read input stream to byte array Stream
     *
     * @param input
     * @return
     */
    protected static ByteArrayOutputStream readInputStream(InputStream input) {
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
    protected ByteArrayOutputStream getReciveData() {
        ByteArrayOutputStream Result = null;
        try {
            //获取用户的输入对象
            InputStream input = Client.getInputStream();
            //读取用户传来的数据
            Result = readInputStream(input);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.loge("HttpClient->getReciveData|Exception|ErrorMessage : " + e.getMessage());
            Result = null;
        }

        return Result;
    }


    /**
     * get Client Address
     *
     * @return
     */
    public String getClientAddress() {
        return Client.getInetAddress().getHostAddress();
    }

    /**
     * return message get
     * @param req
     * @return
     */
    public boolean SendMessage(Response req) {
        OutputStream ClientOutputStream = null;
        try {
            ByteArrayOutputStream outputStream =  req.Build();
            ClientOutputStream = Client.getOutputStream();
            ClientOutputStream.write(outputStream.toByteArray());
            ClientOutputStream.flush();
            ClientOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            String Message =  "HttpClient->SendMessage|Exception|Error Message : " + e.getMessage();
            LogUtils.loge(Message);
            Response res = new Response();
            res.setCode(ResponseCode.Bad_Request);
            res.setReturnContent(Message.getBytes());
            SendMessage(res);
            return false;
        }


    }





}
