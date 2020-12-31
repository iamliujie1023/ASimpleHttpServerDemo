package com.liuj.demo2020Q4.socketservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.text.TextUtils
import com.liuj.demo2020Q4.LogUtils
import com.liuj.demo2020Q4.MyApplication
import com.liuj.demo2020Q4.StringUtils
import com.liuj.demo2020Q4.showToast
import org.cybergarage.http.HTTPHeader
import org.cybergarage.http.HTTPStatus
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile
import java.net.ServerSocket
import java.net.Socket
import java.util.*

/**
 * @author liuj
 * @time 2020/12/29
 */

fun startService() {
    MyApplication.getInstance()
        .startService(Intent(MyApplication.getInstance(), DemoSocketService::class.java))
}

fun stopService() {
    MyApplication.getInstance()
        .stopService(Intent(MyApplication.getInstance(), DemoSocketService::class.java))
}

const val VBOX_REQUEST_SONG_URL = "/song/url"
const val SOCKET_MAIN_THREAD_NAME = "Socket_Main_Thread"
const val CRLF = "\r\n"
const val HEADER_BODY_SEP: String = CRLF + CRLF


class DemoSocketService : Service() {

    private val PORT_ARRAYS = listOf(55123)

    private var socketThread: SocketThread? = null

    private fun startSocketServer(): Boolean {
        for (port in PORT_ARRAYS) {
            try {
                val localServer = ServerSocket(port)
                val localPort = localServer?.localPort
                if (localPort <= 0) {
                    continue
                }
                LogUtils.i("listen on localPort:$localPort")
                socketThread = SocketThread(localServer)
                socketThread?.start()
                break
            } catch (e: IOException) {
                e.printStackTrace()
                showToast(e.toString())
            }
        }
        return true
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.i("DemoSocketService-onCreate， IP=${Util.getIpAddressString()}")
        startSocketServer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        socketThread?.interrupt()
        stopForeground(true)
    }

    inner class SocketThread(private val _localServer: ServerSocket) : Thread() {
        private var ifStop = false

        override fun run() {
            currentThread().name = "MainProxyThread"
            while (!ifStop) {
                try {
                    val acceptSocket = _localServer.accept()
                    LogUtils.i("_localServer.accept=${_localServer.hashCode()}")
                    Thread(ProxyRunnable(acceptSocket)).start()
                } catch (e: IOException) {
                    e.printStackTrace()
                    LogUtils.e("interrupt-error=${e?.toString()}")
                }
            }
        }

        override fun interrupt() {
            super.interrupt()
            try {
                _localServer?.close()
            } catch (e: IOException) {
                LogUtils.e("interrupt-error=${e?.toString()}")
            }
            ifStop = true
        }

    }

    inner class ProxyRunnable(private val socket: Socket) : Runnable {
        private val bytes = ByteArray(8 * 1024)

        /**
         * 服务器请求
         */
        private var serverInputStream: InputStream? = null
        private var firstLine: String? = ""

        ////////////////////////////////////////////////
        //	Header
        ////////////////////////////////////////////////
        private var httpHeaderList = Vector<HTTPHeader>()

        override fun run() {
            Thread.currentThread().name = "ProxyThread#$this"
            LogUtils.i("proxyThread:" + Thread.currentThread().name)
            var bytesRead: Int
            try {
                val sb = StringBuffer()
                val input = BufferedInputStream(socket.getInputStream())
                firstLine = Util.readLine(input)
                if (firstLine?.isEmpty() == true) {
                    return
                }

                // Thanks for Giordano Sassaroli <sassarol@cefriel.it> (09/03/03)
                val httpStatus = HTTPStatus(firstLine)
                val statCode = httpStatus.statusCode
                if (statCode == HTTPStatus.CONTINUE) {
                    var headerLine: String? = Util.readLine(input)
                    while (headerLine != null && 0 < headerLine.length) {
                        val header = HTTPHeader(headerLine)
                        if (header.hasName()) {
                            setHeader(header)
                        }
                        headerLine = Util.readLine(input)
                    }
                    //look forward another first line
                    val actualFirstLine: String = Util.readLine(input)
                    if (actualFirstLine.isNotEmpty()) {
                        //this is the actual first line
                        firstLine = actualFirstLine
                    } else {
//                        return true
                        return
                    }
                }


////                val inputStream = BufferedReader(InputStreamReader(socket.getInputStream()))
//                val inputStream  = socket.getInputStream()
//                val file = File(MyApplication.getInstance().filesDir.absolutePath + File.separator + "/demo_test.video")
//                if (!file.exists()) {
//                    file.createNewFile()
//                }
//                val fileOut = FileOutputStream(file)
//                var len = 0
//                // 开始读取
//                while (inputStream.read(bytes).also { len = it } != -1) {
//                    fileOut.write(bytes, 0, len)
//                    LogUtils.i("read...len=${len}")
//                }

                val requestStr = sb.toString()
                if (StringUtils.isBlank(requestStr)) {
                    LogUtils.i("requestStr is Empty")
                    return
                }
                val raf: RandomAccessFile? = null

//                path = requestStr.split(" ".toRegex()).toTypedArray()[1]
//                val routerWrapper: RouterWrapper = RouterManager.getRouterWrapper(path)
//                if (null == routerWrapper) {
//                    LogUtils.i(
//                        "routerWrapper is Empty"
//                    )
//                    return
//                }
//                val clazz: Class<*> = routerWrapper.getClazz()
//                if (null == clazz) {
//                    LogUtils.i(
//                        "routerWrapper clazz is Empty"
//                    )
//                    return
//                }

                val splitArray =
                    requestStr.split("\r\n\r\n".toRegex()).toTypedArray()
                var requestHeader: String? = null
                var requestBody: String? = null
                if (splitArray.isNotEmpty()) {
                    requestHeader = splitArray[0]
                }
                if (splitArray.size > 1) {
                    requestBody = splitArray[1]
                }
//                val httpParse: IHttpProcess = clazz.newInstance() as IHttpProcess
//                val responseStr: String = httpParse.parseRequest(requestHeader, requestBody)
                sendResponse("liuj_demo_200", socket)
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                clean()
            }
        }

        private fun sendResponse(
            responseStr: String,
            socket: Socket?
        ) {
            try {
                val sb = StringBuilder()
                if (TextUtils.isEmpty(responseStr)) {
                    sb.append("HTTP/1.1 404 Not Found")
                    sb.append(CRLF).append(CRLF)
                    val jsonObject = JSONObject()
                    jsonObject.put("code", 404)
                    sb.append(jsonObject.toString())
                } else {
                    sb.append("HTTP/1.1 200 OK")
                    sb.append(CRLF.toString() + "Content-Type: application/json")
                    sb.append(CRLF).append(CRLF)
                    sb.append(responseStr)
                }
                LogUtils.i(
                    "sendResponse str =$sb"
                )
                socket!!.getOutputStream().write(sb.toString().toByteArray())
            } catch (e: IOException) {
                LogUtils.i(
                    "throwable =$e"
                )
            } catch (e: JSONException) {
                LogUtils.i(
                    "throwable =$e"
                )
            }
        }

        private fun clean() {
            LogUtils.i("clean#$this")
            try {
                if (serverInputStream != null) {
                    serverInputStream!!.close() //对于Connection:keep-alive无效，stream关掉后不会影响服务器继续给客户端发数据
                    serverInputStream = null
                }
                socket?.close()
            } catch (e: IOException) {
                e.printStackTrace()
                LogUtils.i("e#${e?.toString()}")
            }
        }

        fun getNHeaders(): Int {
            return httpHeaderList.size
        }

        fun addHeader(header: HTTPHeader?) {
            httpHeaderList!!.add(header)
        }

        fun addHeader(name: String?, value: String?) {
            val header = HTTPHeader(name, value)
            httpHeaderList!!.add(header)
        }

        fun getHeader(n: Int): HTTPHeader {
            return httpHeaderList!![n] as HTTPHeader
        }

        fun getHeader(name: String?): HTTPHeader? {
            val nHeaders = getNHeaders()
            for (n in 0 until nHeaders) {
                val header = getHeader(n)
                val headerName = header.name
                if (headerName.equals(name, ignoreCase = true) == true) return header
            }
            return null
        }

        fun clearHeaders() {
            httpHeaderList.clear()
            httpHeaderList = Vector<HTTPHeader>()
        }

        fun hasHeader(name: String?): Boolean {
            return if (getHeader(name) != null) true else false
        }

        fun setHeader(name: String?, value: String?) {
            val header = getHeader(name)
            if (header != null) {
                header.value = value
                return
            }
            addHeader(name, value)
        }

        fun setHeader(name: String?, value: Int) {
            setHeader(name, Integer.toString(value))
        }

        fun setHeader(name: String?, value: Long) {
            setHeader(name, java.lang.Long.toString(value))
        }

        fun setHeader(header: HTTPHeader) {
            setHeader(header.name, header.value)
        }

        fun getHeaderValue(name: String?): String? {
            val header = getHeader(name) ?: return ""
            return header.value
        }


    }


}