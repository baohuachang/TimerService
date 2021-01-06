package com.example.timerservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.timerservice.ITimer.Stub
import kotlin.concurrent.thread

class TimerService : Service() {

    companion object {
        private const val TAG = "TimerService"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate (Thread: ${Thread.currentThread()})")
    }

    private val binder = object : Stub() {
        override fun startTimer(delay: Long, callback: ITimeCallback?) {
            Log.d(TAG, "startTimer: $delay (Thread: ${Thread.currentThread()})")
            thread {
                Thread.sleep(delay)
                Log.d(TAG, "startTimer: call onTimer (Thread: ${Thread.currentThread()})")
                callback?.onTimer()
            }//.run()
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}
