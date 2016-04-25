package com.vrmightypirates.smalfhemcontrol;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;


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


    public String getTemperatureBathroom() {

        public void onClick(View v) {
            new DownloadImageTask().execute("http://example.com/image.png");
        }












        Log.v(TAG, "fehmresppnse: before ");
        new Thread() {

            public void run() {

            }
        }.start();

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



    private class DownloadImageTask extends AsyncTask<String> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
        protected String doInBackground(String... urls) {




            return loadImageFromNetwork(urls[0]);
        }

        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(Bitmap result) {
            mImageView.setImageBitmap(result);
        }
    }



}
