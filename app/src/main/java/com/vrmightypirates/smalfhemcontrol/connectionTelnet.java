package com.vrmightypirates.smalfhemcontrol;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by Boke on 15.05.2016.
 */
public class ConnectionTelnet extends AsyncTask<Void,String,String> {


    private static final String TAG = ConnectTelnet.class.getSimpleName();
    BufferedWriter outputWriter = null;
    Socket socket = null;
    String socketIp = "192.168.0.17";
    int socketPort = 7072;
    LooperThread mLooperThread;

    public ConnectionTelnet() {
        mLooperThread = new LooperThread();
    }

    public boolean sendMessage(String message) {

        if(socket.isConnected())
        try {
            outputWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            outputWriter.write(message + System.getProperty("line.separator"));
            outputWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } else{
            Log.w(TAG, "Socket is not connected!");
        }
        return true;
    }

    @Override
    protected String doInBackground(Void... params) {

        BufferedWriter outputWriter = null;
        String messageFromFhem = null;
        boolean whileRun=true;
        Log.i(TAG, "temptemperature? ");

        try {
            socket = new Socket(socketIp, socketPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(whileRun) {
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                messageFromFhem = inputReader.readLine() + System.getProperty("line.separator");

                Log.i(TAG, "messageFromFhem: " + messageFromFhem);

                if (mLooperThread.mHandler != null) {

                    Message msg = mLooperThread.mHandler.obtainMessage(0);
                    msg.obj = messageFromFhem;
                    mLooperThread.mHandler.sendMessage(msg);

                }

                Thread.sleep(10);

            } catch (IOException e) {
                Log.e(TAG, "Fuck");
                e.printStackTrace();
            } catch (InterruptedException e) {
                Log.e(TAG, "Fuckinterupt");
                e.printStackTrace();
            }
            Log.e(TAG, "Is Connected: " + socket.isConnected());
        }
        return "";
    }
}
