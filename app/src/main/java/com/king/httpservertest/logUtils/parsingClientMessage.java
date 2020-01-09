package com.king.httpservertest.logUtils;

import com.king.httpservertest.LogUtils;
import com.king.httpservertest.logUtils.Http.Request;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class parsingClientMessage implements Runnable {

    private Socket client;

    /**
     * get new Parsing Client Message Instance
     *
     * @param client 客户对象
     * @return
     */
    public static parsingClientMessage getInstance(Socket client) {
        parsingClientMessage Message = new parsingClientMessage();
        Message.client = client;
        return Message;
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
            InputStream input = client.getInputStream();
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
     * get Client Address
     *
     * @return
     */
    private String getClientAddress() {
        return client.getInetAddress().getHostAddress();
    }

    /**
     * return message get
     * @param Message
     * @return
     */
    private boolean SendMessage(byte[] Message) {
        try {
            byte[] Header = "HTTP/1.1 200 OK\nServer: king/2.0.14\n\n".getBytes();
            ByteArrayOutputStream Localout = new ByteArrayOutputStream();
            Localout.write(Header,0,Header.length);
            Localout.write(Message,0,Message.length);
            OutputStream out = client.getOutputStream();
            out.write(Localout.toByteArray(),0,Localout.size());
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.loge("parsingClientMessage->SendMessage|Exception|Error Message : " + e.getMessage());
            return false;
        }


    }





    @Override
    public void run() {
        String Address = getClientAddress();
        LogUtils.logi("Recevied Client Address : " + Address);
        ByteArrayOutputStream Mess = getReciveData();
        if (Mess == null)
        {
            SendMessage("Null".getBytes());
            return;
        }
        Request req = Request.Parsing(Mess);
        SendMessage(req.toString().getBytes());
        LogUtils.logi(req.toString());

    }








}
