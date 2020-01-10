package com.king.httpservertest.logUtils;

import com.king.httpservertest.LogUtils;
import com.king.httpservertest.logUtils.Http.HttpClient;
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
        Request httpClient = new Request(client);
        String Message = "<h1> king<h1>";
        Response res = new Response().setReturnContent(httpClient.Content);
        httpClient.SendMessage(res);
    }








}
