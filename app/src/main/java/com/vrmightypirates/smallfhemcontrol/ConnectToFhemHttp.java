package com.vrmightypirates.smallfhemcontrol;

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
    private static final String command = "?XHR=1&inform=type=status;filter=room=all&timestamp=' + new Date().getTime()";//"?cmd=list%20BZ.HT.BadHeizung%20desiredTemperature&XHR=1";;

    private static final String TAG = ConnectToFhemHttp.class.getSimpleName();

    public String connect(){
        Log.e(TAG, "found content: **************:::");

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
            Log.d(TAG,"response test");
            int statusCode = connection.getResponseCode();
            Log.d(TAG,"response status code is " + statusCode);
            Log.d(TAG,"response test");
            RequestResult<InputStream> response = new RequestResult((InputStream) new BufferedInputStream(connection.getInputStream()));
            Log.d(TAG,"response test");
            reader = new InputStreamReader(response.content);
            Log.d(TAG,"response test3");
            String content = CharStreams.toString(reader);
            Log.d(TAG,"response test4");
            if (content.contains("<title>") || content.contains("<div id=")) {
                Log.e(TAG, "found strange content: " + content);
            }
            Log.e(TAG, "found content: **************" + content);

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
