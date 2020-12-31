package com.liuj.demo2020Q4.httpservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.text.TextUtils
import com.liuj.demo2020Q4.LogUtils
import com.liuj.demo2020Q4.MyApplication
import com.liuj.demo2020Q4.StringUtils
import com.liuj.demo2020Q4.showToast
import com.liuj.demo2020Q4.socketservice.Util
import org.cybergarage.http.*
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.util.*


/**
 * @author liuj
 * @time 2020/12/30
 */

/**
 * @author liuj
 * @time 2020/12/29
 */

fun startService() {
    MyApplication.getInstance()
        .startService(Intent(MyApplication.getInstance(), HTTPServerDemo::class.java))
}

fun stopService() {
    MyApplication.getInstance()
        .stopService(Intent(MyApplication.getInstance(), HTTPServerDemo::class.java))
}

const val VBOX_REQUEST_SONG_URL = "/song/url"
const val SOCKET_MAIN_THREAD_NAME = "Socket_Main_Thread"
const val CRLF = "\r\n"
const val HEADER_BODY_SEP: String = CRLF + CRLF


class HTTPServerDemo : Service(), HTTPRequestListener {

    private val PORT_ARRAYS = 55123

    private lateinit var socketThread: HTTPServer

    private fun startSocketServer(): Boolean {
        socketThread = HTTPServer()
        socketThread.addRequestListener(this)
        val result = socketThread.open(Util.getIpAddressString(), PORT_ARRAYS)
        socketThread.start()
        return result
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.i("HTTPServerDemo-onCreate， IP=${Util.getIpAddressString()}")
        startSocketServer()
    }

    override fun onDestroy() {
        super.onDestroy()
        socketThread.stop()
        stopForeground(true)
    }

    override fun httpRequestRecieved(httpReq: HTTPRequest?) {
        if (null == httpReq) {
            return
        }

        val file = File(MyApplication.getInstance().filesDir.absolutePath + File.separator + "/HTTPServer_demo_test.mp4")
        if (!file.exists()) {
            file.createNewFile()
        }
        val fileOut = FileOutputStream(file)
        fileOut.write(httpReq.content)
        LogUtils.i("HTTPServerDemo-httpRequestReceiver=\n${httpReq.toString()}")
    }


}