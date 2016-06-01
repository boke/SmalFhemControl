package com.vrmightypirates.smallfhemcontrol;

import android.os.AsyncTask;
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

public class CommunicateWithFhemTelnet extends AsyncTask<String,String,Void> {
    private final boolean singleResponse;
    OnMassageFromFhem onMassageFromFhem;
    private final String TAG = CommunicateWithFhemTelnet.class.getSimpleName();
    BufferedWriter outputWriter = null;
    Socket socket = null;
    String socketIp = "192.168.0.17";
    int socketPort = 7072;

    CommunicateWithFhemTelnet(OnMassageFromFhem onMassageFromFhem, String message, boolean singleResponse){
        this.onMassageFromFhem = onMassageFromFhem;
        this.singleResponse = singleResponse;
        Log.i(TAG, "CommunicateWithFhemTelnet: " + message);
        this.execute(message,Boolean.toString(singleResponse));
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Void doInBackground(String... params) {

        BufferedWriter outputWriter = null;
        String messageFromFhem = null;
        String singleResponseString = params[1];

        try {
            socket = new Socket(socketIp, socketPort);

            while (!socket.isConnected()) {
                Thread.sleep(100);
            }

            outputWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            outputWriter.write(params[0] + System.getProperty("line.separator"));
            outputWriter.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BufferedReader inputReader = null;
        try {
            inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!isCancelled()) {

            int i = 1;
            try {
                messageFromFhem = inputReader.readLine() + System.getProperty("line.separator");
                Log.i(TAG, "doInBackground: "+ messageFromFhem);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(singleResponseString.equals("true")){
                this.cancel(true);
            }

            publishProgress(messageFromFhem.toString());
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (onMassageFromFhem != null) {
            onMassageFromFhem.onMessageFromFhemReceived(values[0],singleResponse);
        }else{
            Log.e(TAG, "onProgressUpdate: null");
        }
    }



    interface OnMassageFromFhem {
        void onMessageFromFhemReceived(String messageFromFhem, boolean singleResponse);
    }

}
