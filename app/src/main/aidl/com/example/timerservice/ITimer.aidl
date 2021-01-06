// ITimer.aidl
package com.example.timerservice;
import com.example.timerservice.ITimeCallback;
// Declare any non-default types here with import statements

interface ITimer {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void startTimer(long delay, ITimeCallback callback);
}
