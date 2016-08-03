package com.example.svangara.client;

/**
 * Created by svangara on 7/28/16.
 */
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ClientActivity extends Activity {


    private Button connectPhones;
    private String serverIpAddress = "10.0.0.16";
    private boolean connected = false;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        connectPhones = (Button) findViewById(R.id.btnConnect);
        connectPhones.setOnClickListener(connectListener);
    }

    private OnClickListener connectListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!connected) {
                Thread cThread = new Thread(new ClientThread());
                cThread.start();
            }
        }
    };

    public class ClientThread implements Runnable {

        public void run() {
            try {
                Log.d("ClientActivity", "C: Connecting...");
                Socket socket = new Socket("10.0.0.15", 8873);
                connected = true;
                while (connected) {
                    try {
                        Log.d("ClientActivity", "C: Sending command.");
                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                                .getOutputStream())), true);
                        out.println("Hey Server!");
                        Log.d("ClientActivity", "C: Sent.");
                    } catch (Exception e) {
                        Log.e("ClientActivity", "S: Error", e);
                    }
                }
                socket.close();
                Log.d("ClientActivity", "C: Closed.");
            } catch (Exception e) {
                Log.e("ClientActivity", "C: Error", e);
                connected = false;
            }
        }
    }
}