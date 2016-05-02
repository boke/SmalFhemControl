package com.vrmightypirates.smalfhemcontrol;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


/**
 * Created by Boke on 23.04.2016.
 */
public class ControlApi  {

    private static final Connection CURENTCONNECTION = Connection.TELNET;
    String content= null;
    ConnectToFhemHttp connectToFhemHttp = new ConnectToFhemHttp();
    private String temperatureBathroom;
    private final String TAG = ControlApi.class.getSimpleName();
    String[] splitStr;
    private TextView currentTemperatureBathroom;

    String outStr, outStr1, outStrAlias;
    String retStr[] = {"00.0", "00.0", ""};

    LooperThread mLooperThread;

    ControlApi(){

        mLooperThread = new LooperThread();
        mLooperThread.start();
    }

    private class LooperThread extends Thread {

        public Handler mHandler;

        public void run() {
            Looper.prepare();
            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    if(msg.what == 0) {
                        doLongRunningOperation((String)msg.obj);
                    }
                }
            };
            Looper.loop();
        }
    }


    public String getTemperatureBathroom(TextView currentTemperatureBathroom) {

        this.currentTemperatureBathroom =currentTemperatureBathroom;

        switch(CURENTCONNECTION) {
            case TELNET:

                //String[] telnetTemperature = connectToFhemTelnet.getFHEMString("BZ.HT.BadHeizung");



                new SetTemperatureTelnetText().execute();







                break;

            case HTTP:
                new SetTemperatureText().execute();
                break;
            default:
                break;
        }




        return temperatureBathroom;
    }

    private  void doLongRunningOperation(String result) {
        // Add long running operation here.

        Log.i(TAG, "doLongRunningOperation: ");
        currentTemperatureBathroom.setText(result);
    }




    public void setTemperatureBathroom(String temperatureBathroom) {
        this.temperatureBathroom = temperatureBathroom;
        Log.v(TAG, "setTemperatureBathroom"+ temperatureBathroom);




//        String [] fhemResponse = connectToFhem.getFHEMString("BZ.HT.BadHeizung");
//        for (int i = 0; i < fhemResponse.length; i++){
//
//            Log.e(TAG, "fehmresppnse: "+ fhemResponse[i]);
//        }


    }

    protected class SetTemperatureText extends AsyncTask<Void,String,String> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */



        @Override
        protected String doInBackground(Void... params) {
            Log.v(TAG, "fehmresppnse: in ");
            while (content==null){
                Log.v(TAG, "fehmresppnse: in While ");
                content = connectToFhemHttp.connect();
                splitStr = content.split("\\s+");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            if(splitStr[3]!=null){
                temperatureBathroom = splitStr[3];
                Log.v(TAG, "fehmresppnse: "+ splitStr[3]);

            }



            return splitStr[3];
        }

        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(String result) {

            currentTemperatureBathroom.setText(result);
        }


    }



    protected class SetTemperatureTelnetText extends AsyncTask<Void,String,String> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */



        @Override
        protected String doInBackground(Void... params) {
            Log.v(TAG, "fehmresppnse: in ");
            Socket s = null;

            BufferedWriter out = null;
            String temptemperature = null;
            boolean whileRun=true;

            Log.i(TAG, "temptemperature? ");

            try {
                Log.i(TAG, "temptemperature? ");
                s = new Socket("192.168.0.17", 7072);
                out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                Log.i(TAG, "BufferedWriter? ");
//              outStr = "{ReadingsVal('" + "BZ.HT.BadHeizung" + "','desiredTemperature','')}";
                outStr = "inform on BZ.HT.BadHeizung";
                outStr1 = outStr + System.getProperty("line.separator");
                out.write(outStr1);
                out.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
            int i = 0;

            while(whileRun) {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    Log.i(TAG, "temptemperature? " + i++);
                    temptemperature = in.readLine() + System.getProperty("line.separator");
                    retStr[0] = temptemperature;
                    Log.i(TAG, "temptemperature: " + temptemperature);


                    if (mLooperThread.mHandler != null) {
                        Message msg = mLooperThread.mHandler.obtainMessage(0);
                        msg.obj = temptemperature;

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
                Log.e(TAG, "Is Connected: " + s.isConnected());

            }


            return "";
        }

        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(String result) {

            currentTemperatureBathroom.setText(result);
        }


    }

    public enum Connection {
        TELNET,
        HTTP
    }


}
