package com.vrmightypirates.smalfhemcontrol;

import android.util.Base64;
import android.util.Log;

import com.google.common.io.CharStreams;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Boke on 24.04.2016.
 */
public class ConnectToFhemHttp {
    public static final int SOCKET_TIMEOUT = 20000;
    private static final String url = "http://192.168.0.17:8083/fhem";
    private static final String command = "?cmd=list%20BZ.HT.BadHeizung%20desiredTemperature&XHR=1";;

    private static final String TAG = ConnectToFhemHttp.class.getSimpleName();

    public String connect(){

        InputStreamReader reader = null;

        try {
            // url = "http://192.168.0.17:8083/fhem" + urlSuffix;
            URL requestUrl = new URL(url + command);

            Log.e(TAG, "accessing URL {}");
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setConnectTimeout(SOCKET_TIMEOUT);
            connection.setReadTimeout(SOCKET_TIMEOUT);

            String authString = ("" + ":" + "");
            connection.addRequestProperty("Authorization", "Basic " +
                    Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP));
            int statusCode = connection.getResponseCode();
            Log.d(TAG,"response status code is " + statusCode);

            RequestResult<InputStream> response = new RequestResult((InputStream) new BufferedInputStream(connection.getInputStream()));

            reader = new InputStreamReader(response.content);
            String content = CharStreams.toString(reader);
            if (content.contains("<title>") || content.contains("<div id=")) {
                Log.e(TAG, "found strange content: " + content);
            }
            Log.e(TAG, "found content: " + content);

            return content;
//            return new RequestResult<>((InputStream) new BufferedInputStream(connection.getInputStream()));

        } catch (ConnectTimeoutException e) {
            Log.e(TAG,"connection timed out" +e);

        } catch (IOException e) {
           Log.e(TAG,"cannot connect to host" + e);

        }

        return null;
    }

}
