package com.king.httpservertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.king.httpservertest.logUtils.Http.Core.HttpServer;
import com.king.httpservertest.logUtils.SocketUtils.SocketServer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onClick();
    }


    public void onClick()
    {

        findViewById(R.id.start_server).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListenerRequest lis = new ListenerRequest();

                HttpServer server = null;
                try {
                    server = new HttpServer(lis);
                    server.Start(50131);
                    LogUtils.logi("Start Server Success");
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtils.logi("Start Server Failure");
                }

            }
        });


    }




}
