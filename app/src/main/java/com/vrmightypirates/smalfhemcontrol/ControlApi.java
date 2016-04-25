package com.vrmightypirates.smalfhemcontrol;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;


/**
 * Created by Boke on 23.04.2016.
 */
public class ControlApi  {
    String content= null;
    ConnectToFhemTelnet connectToFhemTelnet = new ConnectToFhemTelnet();
    ConnectToFhemHttp connectToFhemHttp = new ConnectToFhemHttp();
    private String temperatureBathroom;
    private final String TAG = ControlApi.class.getSimpleName();
    String[] splitStr;
    private TextView currentTemperatureBathroom;


    public String getTemperatureBathroom(TextView currentTemperatureBathroom) {
        this.currentTemperatureBathroom =currentTemperatureBathroom;
            new SetTemperatureText().execute();

        return temperatureBathroom;
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



}
