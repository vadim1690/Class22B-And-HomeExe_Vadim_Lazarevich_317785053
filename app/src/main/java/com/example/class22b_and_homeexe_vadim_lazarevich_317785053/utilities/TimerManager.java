package com.example.class22b_and_homeexe_vadim_lazarevich_317785053.utilities;

import android.os.Handler;

import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.callbacks.CallBack_TimerUpdate;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.logic.TimerStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerManager {
    private final int DELAY = 1000;
    private TimerStatus timerStatus = TimerStatus.OFF;
    private CallBack_TimerUpdate callBack_Timer_update;
    private final Runnable r ;
    private final Handler handler = new Handler();



    public TimerManager(CallBack_TimerUpdate callBack) {
        this.callBack_Timer_update = callBack;
        r =  new Runnable() {
            public void run() {
                handler.postDelayed(r, DELAY);
                callBack_Timer_update.CallBackUpdate();

            }
        };
    }




    public void startTimer() {
        timerStatus = TimerStatus.RUNNING;
        handler.postDelayed(r, DELAY);
    }


    public void stopTimer() {
        timerStatus = TimerStatus.PAUSE;
        handler.removeCallbacks(r);
    }

    public TimerStatus getTimerStatus() {
        return timerStatus;
    }


}
