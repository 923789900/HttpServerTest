package com.king.httpservertest.logUtils;

import com.king.httpservertest.LogUtils;
import com.king.httpservertest.logUtils.Http.Request;
import com.king.httpservertest.logUtils.Http.Response;

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





    @Override
    public void run() {
        String Address = getClientAddress();
        LogUtils.logi("Recevied Client Address : " + Address);
        ByteArrayOutputStream Mess = getReciveData();
        Response response = new Response();
        if (Mess == null)
        {
            response.setReturnContent("Hellow Word".getBytes());
            SendMessage(response);
            return;
        }
        Request req = Request.Parsing(Mess,Socket Client);
        response.setReturnContent(req.toString().getBytes());
        SendMessage(response);

    }








}
