package com.liuj.demo2020Q4.httpserver2

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.liuj.demo2020Q4.LogUtils
import com.liuj.demo2020Q4.MyApplication
import com.liuj.demo2020Q4.socketservice.Util
import java.io.*


//fun startService() {
//    MyApplication.getInstance()
//        .startService(Intent(MyApplication.getInstance(), SimpleHTTPServerService::class.java))
//}
//
//fun stopService() {
//    MyApplication.getInstance()
//        .stopService(Intent(MyApplication.getInstance(), SimpleHTTPServerService::class.java))
//}


object SimpleHTTPServerService {

    private val PORT : Int = 9990

    private val nanoHttpServer: RealNanoHttpServer = RealNanoHttpServer(
        MyApplication.getInstance().filesDir.absolutePath + File.separator,
        PORT
    )


    @SuppressLint("TryCatchExceptionError")
    fun startSocketServer(): Boolean {
        try {
            LogUtils.i("SimpleHTTPServerDemo-onCreate， IP=${Util.getIpAddressString()}")
            if(!nanoHttpServer.isAlive) {
                nanoHttpServer.start()
            }
            return true
        } catch (e: Exception) {
            LogUtils.i("SimpleHTTPServer startSocketServer ERROR= $e")
        }
        LogUtils.i("startSocketServer false")
        return false
    }

    fun stopSocketServer() {
        nanoHttpServer.stop()
    }


}