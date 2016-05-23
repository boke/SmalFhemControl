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
    OnMassageFromFhem onMassageFromFhem;

    CommunicateWithFhemTelnet(OnMassageFromFhem onMassageFromFhem, String message){
        this.onMassageFromFhem = onMassageFromFhem;
        this.execute(message);
    }


    private final String TAG = CommunicateWithFhemTelnet.class.getSimpleName();
    BufferedWriter outputWriter = null;
    Socket socket = null;
    String socketIp = "192.168.0.17";
    int socketPort = 7072;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Void doInBackground(String... params) {


        BufferedWriter outputWriter = null;
        String messageFromFhem = null;
        boolean whileRun = true;
        Log.i(TAG, "doInBackground");

        try {
            socket = new Socket(socketIp, socketPort);

            while (!socket.isConnected()) {
                Log.i(TAG, "doInBackground: Socket is not connected");
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

        while (whileRun) {

            int i = 1;
            Log.i(TAG, "" + i++);
            try {
                messageFromFhem = inputReader.readLine() + System.getProperty("line.separator");
            } catch (IOException e) {
                e.printStackTrace();
            }

            publishProgress(messageFromFhem.toString());

            Log.e(TAG, "Is Connected: " + socket.isConnected());
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Log.i(TAG, "onProgressUpdate after: " + values[0]);

        if (onMassageFromFhem != null) {
            onMassageFromFhem.onMessageFromFhemReceived(values[0]);
        }else{
            Log.e(TAG, "onProgressUpdate: null");
        }
    }


    interface OnMassageFromFhem {
        void onMessageFromFhemReceived(String messageFromFhem);
    }

}
