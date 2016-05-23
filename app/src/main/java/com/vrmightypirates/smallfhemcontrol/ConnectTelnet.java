package com.vrmightypirates.smallfhemcontrol;

class ConnectTelnet {//extends AsyncTask<Void,String,String> {
    /*private static final String TAG = ConnectTelnet.class.getSimpleName() ;

    *//** The system calls this to perform work in a worker thread and
     * delivers it the parameters given to AsyncTask.execute() *//*
    @Override
    protected String doInBackground(Void... params) {
        Log.v(TAG, "fehmresppnse: in ");
        Socket s = null;
        BufferedWriter out = null;
        String temptemperature = null;
        boolean whileRun=true;
        String outStr, outStr1;
        String retStr[] = {"00.0", "00.0", ""};

        Log.i(TAG, "temptemperature? ");

        try {
            s = new Socket("192.168.0.17", 7072);
            out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
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

    *//** The system calls this to perform work in the UI thread and delivers
     * the result from doInBackground() *//*
    protected void onPostExecute(String result) {
        currentTemperatureBathroom.setText(result);
    }*/
}