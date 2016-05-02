package com.vrmightypirates.smalfhemcontrol;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Boke on 24.04.2016.
 */
public class ConnectToFhemTelnet {

    private static final String SERVER_IP = "192.168.0.17";
    private static final int SERVERPORT = 7072;
    private static final String TAG = ConnectToFhemTelnet.class.getSimpleName();

    public String[] getFHEMString(String akt_Device) {
        String outStr, outStr1, outStrAlias;
        String retStr[] = {"00.0", "00.0", ""};
        if (!SERVER_IP.equals("")) {
            try {
                Log.e(TAG, "Is Connected? "+ System.getProperty("line.separator"));
                Socket s = new Socket(SERVER_IP, SERVERPORT);
                Log.e(TAG, "Is Connect");
                Log.e(TAG, "Is Connected: " +s.isConnected());
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

                outStr = "{ReadingsVal('" + akt_Device + "','desiredTemperature','')}";
                outStr1 = outStr + System.getProperty("line.separator");

                out.write(outStr1);
                out.flush();
                String temptemperature = in.readLine() + System.getProperty("line.separator");
                if (temptemperature.length() > 2) {
                    retStr[0] = temptemperature;
                    Log.i(TAG, "temptemperature: " + temptemperature);
                }

                outStr = "{ReadingsVal('" + akt_Device + "','humidity','')}";
                outStr1 = outStr + System.getProperty("line.separator");
                out.write(outStr1);
                out.flush();
                String temphumidity = in.readLine() + System.getProperty("line.separator");
                if (temphumidity.length() > 2) {
                    retStr[1] = temphumidity;
                    Log.i(TAG, "temphumidity: " + temphumidity);
                }

                outStrAlias = "{AttrVal('" + akt_Device + "','alias','')}";
                outStrAlias = outStrAlias + System.getProperty("line.separator");
                out.write(outStrAlias);
                out.flush();
                String tempalias = in.readLine();
                if (tempalias.length() > 2) {
                    retStr[2] = tempalias;
                    Log.i(TAG, "tempalias: " + tempalias);
                } else {
                    retStr[2] = akt_Device;
                }

                out.close();
            } catch (UnknownHostException e) {
                Log.e(TAG, "Uups!\nUnknownHostException\nServer-Settings überprüfen.");
//                        showMsg("Uups!\nUnknownHostException\nServer-Settings überprüfen.");
                                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG, "Uups!\nIOException\nServer-Settings überprüfen.\nIst das Device auch vorhanden?");
//                        showMsg("Uups!\nIOException\nServer-Settings überprüfen.\nIst das Device auch vorhanden?");
                                e.printStackTrace();
            } catch (Exception e) {
                Log.e(TAG, "Uups!\nException\nServer-Settings überprüfen.");
                        e.printStackTrace();
            }
        }
        return retStr;
    }


}