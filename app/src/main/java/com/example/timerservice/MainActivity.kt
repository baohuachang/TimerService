package com.example.timerservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import com.example.timerservice.TimerService

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    var mService: ITimer.Stub? = null

    private val connection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as ITimer.Stub
            mService = binder
            Log.i(TAG, "onServiceConnected: Call Start Timer (Thread: ${Thread.currentThread()})")
            binder.startTimer(2, object : ITimeCallback {
                override fun asBinder(): IBinder {
                    TODO("Not yet implemented")
                }

                override fun onTimer() {
                    Log.i(TAG, "onTimer: Called (Thread: ${Thread.currentThread()})")
                }
            })
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            Log.i(TAG, "on Button Click: ")
            Intent(this, TimerService::class.java).also {
                bindService(it, connection, Context.BIND_AUTO_CREATE)
            }
        }

        findViewById<Button>(R.id.timerButton).setOnClickListener {
            mService?.startTimer(2, object: ITimeCallback {
                override fun asBinder(): IBinder {
                    TODO("Not yet implemented")
                }

                override fun onTimer() {
                    Log.i(TAG, "onTimer: Called by start timer  (Thread: ${Thread.currentThread()})")
                }

            })
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        Log.i(TAG, "onStop: Unbind service")
    }
}