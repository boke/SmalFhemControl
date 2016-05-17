package com.vrmightypirates.smalfhemcontrol;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by Boke on 16.05.2016.
 */
class LooperThread extends Thread {

    public Handler mHandler;
    OnNewMessageFromFhemListener onNewMessageFromFhemListener;

    public void run() {
        Looper.prepare();
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if(msg.what == 0) {
                    onNewMessageFromFhemListener.onMessageFromFhemReceived((String)msg.obj);
                }
            }
        };
        Looper.loop();
    }

    public interface OnNewMessageFromFhemListener {
        public void onMessageFromFhemReceived(String messageFromFhem);
    }
}


