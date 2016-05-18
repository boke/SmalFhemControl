package com.vrmightypirates.smalfhemcontrol;

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



public class ConnectionTelnet{

    private static final String TAG = ConnectTelnet.class.getSimpleName();

    public void startConnection(String message) {

        CommunicateWithFhemTelnet communicate = new CommunicateWithFhemTelnet();
        communicate.execute(message);

    }

    class CommunicateWithFhemTelnet extends AsyncTask<String,String,Void> {

        private final String TAG = CommunicateWithFhemTelnet.class.getSimpleName();
        BufferedWriter outputWriter = null;
        Socket socket = null;
        String socketIp = "192.168.0.17";
        int socketPort = 7072;
        LooperThread mLooperThread;

   /* public ConnectionTelnet() {
        mLooperThread = new LooperThread();
    }*/

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

                while(!socket.isConnected()){
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

            while (whileRun) {
                try {
                    BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    messageFromFhem = inputReader.readLine() + System.getProperty("line.separator");
                    publishProgress(messageFromFhem.toString());

                    Log.i(TAG, "messageFromFhem: " + messageFromFhem);

                  /*  if (mLooperThread.mHandler != null) {

                        Message msg = mLooperThread.mHandler.obtainMessage(0);
                        msg.obj = messageFromFhem;
                        mLooperThread.mHandler.sendMessage(msg);

                    }*/

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

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.i(TAG, "messageFromFhem after: " + values[0]);
        }
    }
}








