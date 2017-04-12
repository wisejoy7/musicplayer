package com.musiqueplayer.playlistequalizerandroidwear;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeService extends Service {
    // constant
    public static long NOTIFY_INTERVAL = 10 * 1000; // 10 seconds
    int j;
    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    public Timer mTimer = null;
    int x;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // cancel if already existed
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            //if(new MusicPlayer().isPlaying()) {
                mTimer = new Timer();

       }
        // schedule task
        x=0;j=0;

       // final SharedPreferences.Editor editor = this.getSharedPreferences("sleepmode", Context.MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("sleepmode", MODE_PRIVATE);

            NOTIFY_INTERVAL = prefs.getInt("timerend", -2); //0 is the default value.

            if(NOTIFY_INTERVAL>0) {
                /*mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL*1000);*/
                x=1;

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences prefs = getSharedPreferences("sleepmode", MODE_PRIVATE);

                        int NOTIFY_INTERVALX = prefs.getInt("timerend", -2);
                        if(NOTIFY_INTERVALX>0 & new MusicPlayer().isPlaying())
                            new MusicPlayer().xPause();
                    }
                }, NOTIFY_INTERVAL*1000);

                Toast.makeText(getApplicationContext(), "Sleep mode set for "+(NOTIFY_INTERVAL/60)+" minutes", Toast.LENGTH_SHORT).show();
            }
            else if(NOTIFY_INTERVAL==-1) {
                //mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL*-1);

            }



    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    if(x==1) {
                        new MusicPlayer().xPause();

                    /*stopService(new Intent(getApplicationContext(), TimeService.class));
                    mTimer.cancel();
                        j++;*/


                    }
                }

            });
        }

        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }

    }

}