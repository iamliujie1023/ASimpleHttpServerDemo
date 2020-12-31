package com.liuj.demo2020Q4.httpserver2

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.liuj.demo2020Q4.LogUtils
import com.liuj.demo2020Q4.MyApplication
import com.liuj.demo2020Q4.socketservice.Util
import java.io.*


fun startService() {
    MyApplication.getInstance()
        .startService(Intent(MyApplication.getInstance(), SimpleHTTPServerService::class.java))
}

fun stopService() {
    MyApplication.getInstance()
        .stopService(Intent(MyApplication.getInstance(), SimpleHTTPServerService::class.java))
}


class SimpleHTTPServerService : Service() {

    private val PORT_ARRAYS = 55123

    private lateinit var socketThread: SHttpServer

    private fun startSocketServer(): Boolean {
        socketThread = SHttpServer(
            MyApplication.getInstance().filesDir.absolutePath + File.separator,
            PORT_ARRAYS
        )
        socketThread.start()
        return true
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.i("SimpleHTTPServerDemo-onCreate， IP=${Util.getIpAddressString()}")
        startSocketServer()
    }

    override fun onDestroy() {
        super.onDestroy()
        socketThread.stop()
        stopForeground(true)
    }

}