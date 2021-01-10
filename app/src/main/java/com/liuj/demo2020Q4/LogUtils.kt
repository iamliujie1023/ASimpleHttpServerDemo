package com.liuj.demo2020Q4

import android.util.Log
import android.widget.Toast
import java.util.logging.Level

/**
 * @author liuj
 * @time 2020/12/29
 */
object LogUtils {

    private val TAG = "liuj_Demo"

    private val DEBUG: Boolean = true

    fun toast(msg: String?) {
        if (DEBUG) {
            showToast(msg)
        }
    }

    fun d(msg: String?) {
        if (DEBUG) {
            Log.d(TAG, msg!!)
        }
    }

    fun e(msg: String?) {
        if (DEBUG) {
            Log.e(TAG, msg!!)
        }
    }

    fun i(msg: String?) {
        if (DEBUG) {
            Log.i(TAG, msg!!)
        }
    }

    fun log(level : Level, msg: String) {
        if (DEBUG) {
            Log.i(TAG, "level=${level.name}, msg=$msg")
        }
    }

    fun v(msg: String?) {
        if (DEBUG) {
            Log.v(TAG, msg!!)
        }
    }

    fun w(msg: String?) {
        if (DEBUG) {
            Log.w(TAG, msg!!)
        }
    }

}


fun showToast(msg: String?) {
    Toast.makeText(MyApplication.getInstance(), msg ?: "", Toast.LENGTH_SHORT)
}

