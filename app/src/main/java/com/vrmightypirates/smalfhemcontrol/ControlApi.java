package com.vrmightypirates.smalfhemcontrol;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * Created by Boke on 23.04.2016.
 */
public class ControlApi  {

    private static final Connection CURENTCONNECTION = Connection.TELNET;
    String content= null;
    ConnectToFhemHttp connectToFhemHttp = new ConnectToFhemHttp();
    private String temperatureBathroom;
    private final String TAG = ControlApi.class.getSimpleName();
    private TextView currentTemperatureBathroom;
    private SeekBar seekBarHeater;

    String[] splitStr;
    String outStr, outStr1, outStrAlias;


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
               /* ConnectTelnet connectTelnet = new ConnectTelnet();
                connectTelnet.execute();*/

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

    public enum Connection {
        TELNET,
        HTTP
    }
//TODO interface info wenn sich werte geändert haben

}
