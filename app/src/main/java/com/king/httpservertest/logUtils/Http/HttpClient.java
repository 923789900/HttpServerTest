package com.king.httpservertest.logUtils.Http;

import com.king.httpservertest.LogUtils;
import com.king.httpservertest.logUtils.parsingClientMessage;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpClient {

    private Socket Client;

    private HttpClient(){}
    private HttpClient(Socket client) {
        Client = client;
    }

    /**
     * get new Parsing Client Message Instance
     *
     * @param client 客户对象
     * @return
     */
    public static HttpClient getInstance(Socket client) {
        HttpClient Message = new HttpClient(client);
        return Message;
    }

    /**
     * get Client Address
     *
     * @return
     */
    private String getClientAddress() {
        return Client.getInetAddress().getHostAddress();
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
                if (len < 1024) {
                    out.write(buf, 0, len);
                    break;
                } else if (len == 1024) {
                    out.write(buf, 0, len);
                } else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.loge("parsingClientMessage->readInputStream|Exception|ErrorMessage :  " + e.getMessage());
            }

        } while (true);

        return out;

    }

    /**
     * get received byte buffer
     *
     * @return
     */
    private ByteArrayOutputStream getReciveData() {
        ByteArrayOutputStream Result = null;
        try {
            //获取用户的输入对象
            InputStream input = Client.getInputStream();
            //读取用户传来的数据
            Result = readInputStream(input);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.loge("getReciveData Exception Error Message : " + e.getMessage());
            Result = null;
        }

        return Result;
    }

    /**
     * return message get
     * @param req
     * @return
     */
    private boolean SendMessage(Response req) {
        try {
            ByteArrayOutputStream outputStream =  req.Build();
            OutputStream ClientOutputStream = Client.getOutputStream();
            ClientOutputStream.write(outputStream.toByteArray());
            ClientOutputStream.flush();
            ClientOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.loge("parsingClientMessage->SendMessage|Exception|Error Message : " + e.getMessage());
            return false;
        }


    }





}
