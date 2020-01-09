package com.king.httpservertest.logUtils;

import com.king.httpservertest.LogUtils;

import java.net.ServerSocket;
import java.net.Socket;


public class webLog {

    static ServerSocket sc = null;
    static Thread acceptThread = null;

    private static boolean Init() {
        try {
            if (sc == null) {
                sc = new ServerSocket(50131);
                acceptThread = new Thread(new LogAccept());
                LogUtils.logi(String.format("Start Server Success Server Address : %s", sc.getInetAddress().getHostAddress()));
            }
            return true;
        } catch (Exception e) {
            LogUtils.loge("init LogServer Failure errorMessage : " + e.getMessage());
            return false;
        }
    }

    public static void Start() {

        if (Init())
        {
            acceptThread.start();
        }else
        {
            
        }


    }

    public static void Stop() {
        acceptThread.stop();
    }

}

/**
 * Moniter Client Data
 */
class LogAccept implements Runnable {


    @Override
    public void run() {
        try {
            Socket Client = null;
            while (!webLog.sc.isClosed()) {
                Client = webLog.sc.accept();
                parsingClientMessage parsing = parsingClientMessage.getInstance(Client);
                new Thread(parsing).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.loge("LogAccept->run|Exception|Error Message : " + e.getMessage());
        }


    }


}


